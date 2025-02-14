package org.drift.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.comment.bean.Comment;
import org.drift.comment.mapper.CommentLikeMapper;
import org.drift.comment.mapper.CommentMapper;
import org.drift.comment.service.CommentService;
import org.drift.common.api.CommonResult;
import org.drift.common.context.UserContextHolder;
import org.drift.common.feign.UserServiceClient;
import org.drift.common.pojo.comment.CommentDto;
import org.drift.common.pojo.comment.CommentRequest;
import org.drift.common.pojo.comment.CommentListResponse;
import org.drift.common.pojo.comment.CommentResponse;
import org.drift.common.pojo.user.AuthorInfoDto;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.drift.common.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:02
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentLikeMapper commentLikeMapper;
    private final UserServiceClient userServiceClient;

    public CommentServiceImpl(CommentMapper commentMapper,
                              CommentLikeMapper commentLikeMapper,
                              UserServiceClient userServiceClient) {
        this.commentMapper = commentMapper;
        this.commentLikeMapper = commentLikeMapper;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void publish(CommentRequest request) {
        // 一级评论：parent_comment_id 为空，reply_to_user_id 为空
        // 二级评论：parent_comment_id 为一级评论ID，reply_to_user_id 为空
        // 三级评论：parent_comment_id 为一级评论ID，reply_to_user_id 为二级评论用户ID
    }

    @Override
    public Long getPostCommentCount(Long postId) {
        return commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId));
    }

    @Override
    public List<CommentListResponse> getPostCommentList(Long postId, Long authorId, Integer page) {
        if (authorId == null) {
            authorId = UserContextHolder.getUserContext();
        }
        // 获取一级评论列表
        List<CommentDto> parentCommentList = commentMapper.selectParentCommentList(postId, page * 10);
        if (ObjectUtils.isEmpty(parentCommentList)) {
            return List.of();
        }
        // 获取一级评论 comment id
        Set<Long> parentCommentIdSet = parentCommentList.stream().map(CommentDto::getCommentId).collect(Collectors.toSet());
        // 获取一级评论 user id
        Set<Long> userIdSet = parentCommentList.stream().map(CommentDto::getUserId).collect(Collectors.toSet());
        // 根据一级评论 comment id 列表获取评论最早的一条二级评论（动态作者回复的优先）及子评论数
        List<CommentDto> childCommentList = commentMapper.selectChildCommentList(parentCommentIdSet, authorId);
        Map<Long, CommentDto> childCommentMap = new HashMap<>(10);
        if (!ObjectUtils.isEmpty(childCommentList)) {
            childCommentMap = childCommentList.stream().collect(Collectors.toMap(CommentDto::getParentCommentId, comment -> comment));
            // 加入二级评论 comment id
            parentCommentIdSet.addAll(childCommentList.stream().map(CommentDto::getCommentId).collect(Collectors.toSet()));
            // 加入二级评论 user id
            userIdSet.addAll(childCommentList.stream().map(CommentDto::getUserId).collect(Collectors.toSet()));
        }
        // 根据 comment id 列表获取当前登录用户是否点赞对应评论
        List<Long> likeCommentIdList = commentLikeMapper.selectLikeCommentIdList(UserContextHolder.getUserContext(), parentCommentIdSet);
        // 根据 comment id 列表获取每条评论的点赞数
        Map<Long, Long> commentLikedCountMap = commentLikeMapper.selectCommentLikedCount(parentCommentIdSet);
        // 根据 user id 列表，获取对应用户信息
        Map<Long, UserInfoResponse> authorBasicInfoMap = getAuthorBasicInfoMap(userIdSet);
        // 整合数据
        return merge(parentCommentList, childCommentMap, authorBasicInfoMap, commentLikedCountMap, likeCommentIdList);
    }

    @Override
    public List<CommentResponse> getChildCommentList(Long parentCommentId, Long topChildCommentId, Integer page) {



        return List.of();
    }

    private static List<CommentListResponse> merge(List<CommentDto> parentCommentList,
                                                   Map<Long, CommentDto> childCommentMap,
                                                   Map<Long, UserInfoResponse> authorBasicInfoMap,
                                                   Map<Long, Long> commentLikedCountMap,
                                                   List<Long> likeCommentIdList) {
        return parentCommentList.stream().map(parentComment -> {
            UserInfoResponse parentAuthorInfo = authorBasicInfoMap.get(parentComment.getUserId());
            CommentListResponse commentListResponse = new CommentListResponse()
                    .setParentComment(new CommentResponse()
                            .setCommentId(parentComment.getCommentId())
                            .setContent(parentComment.getContent())
                            .setReleaseTime(DateUtil.format(parentComment.getCreateTime()))
                            .setCommentLikedCount(commentLikedCountMap.get(parentComment.getCommentId()))
                            .setAuthorInfo(new AuthorInfoDto()
                                    .setAuthorId(parentAuthorInfo.getUserId())
                                    .setAuthor(parentAuthorInfo.getUsername())
                                    .setAuthorAvatarUrl(parentAuthorInfo.getAvatarUrl())
                                    .setLiked(likeCommentIdList.contains(parentComment.getCommentId()))))
                    .setChildCommentCount(0L);
            CommentDto childComment = childCommentMap.get(parentComment.getCommentId());
            if (!ObjectUtils.isEmpty(childComment)) {
                UserInfoResponse childAuthorInfo = authorBasicInfoMap.get(childComment.getUserId());
                commentListResponse.setChildCommentCount(childComment.getChildCommentCount() - 1)
                        .setChildComment(new CommentResponse()
                                .setCommentId(childComment.getCommentId())
                                .setContent(childComment.getContent())
                                .setReleaseTime(DateUtil.format(childComment.getCreateTime()))
                                .setCommentLikedCount(commentLikedCountMap.get(childComment.getCommentId()))
                                .setAuthorInfo(new AuthorInfoDto()
                                        .setAuthorId(childAuthorInfo.getUserId())
                                        .setAuthor(childAuthorInfo.getUsername())
                                        .setAuthorAvatarUrl(childAuthorInfo.getAvatarUrl())
                                        .setLiked(likeCommentIdList.contains(childComment.getCommentId()))));
            }
            return commentListResponse;
        }).toList();
    }

    private Map<Long, UserInfoResponse> getAuthorBasicInfoMap(Set<Long> userIdSet) {
        CommonResult<List<UserInfoResponse>> authorBasicInfoList =
                userServiceClient.getUserBasicInfoList(new UserRequest().setUserIds(userIdSet));
        return authorBasicInfoList.getData().stream()
                .collect(Collectors.toMap(UserInfoResponse::getUserId, user -> user));
    }
}
