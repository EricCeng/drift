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
import org.drift.common.pojo.comment.CommentListResponse;
import org.drift.common.pojo.comment.CommentRequest;
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
        List<CommentDto> commentList = commentMapper.selectCommentList(postId, page * 10);
        if (ObjectUtils.isEmpty(commentList)) {
            return List.of();
        }
        // 获取一级评论 comment id
        Set<Long> commentIdSet = commentList.stream().map(CommentDto::getCommentId).collect(Collectors.toSet());
        // 获取一级评论 user id
        Set<Long> userIdSet = commentList.stream().map(CommentDto::getUserId).collect(Collectors.toSet());
        // 根据一级评论 comment id 列表获取评论最早的一条二级评论（动态作者回复的优先）及子评论数
        List<CommentDto> earliestReplyList = commentMapper.selectEarliestReplyList(commentIdSet, authorId);
        Map<Long, CommentDto> earliestReplyMap = new HashMap<>(10);
        if (!ObjectUtils.isEmpty(earliestReplyList)) {
            earliestReplyMap = earliestReplyList.stream().collect(Collectors.toMap(CommentDto::getParentCommentId, comment -> comment));
            // 加入二级评论 comment id
            commentIdSet.addAll(earliestReplyList.stream().map(CommentDto::getCommentId).collect(Collectors.toSet()));
            // 加入二级评论 user id
            userIdSet.addAll(earliestReplyList.stream().map(CommentDto::getUserId).collect(Collectors.toSet()));
        }
        // 根据 comment id 列表获取当前登录用户是否点赞对应评论
        List<Long> likeCommentIdList = commentLikeMapper.selectLikeCommentIdList(UserContextHolder.getUserContext(), commentIdSet);
        // 根据 comment id 列表获取每条评论的点赞数
        Map<Long, Long> commentLikedCountMap = commentLikeMapper.selectCommentLikedCount(commentIdSet);
        // 根据 user id 列表，获取对应用户信息
        Map<Long, AuthorInfoDto> authorBasicInfoMap = getAuthorBasicInfoMap(userIdSet);
        // 整合数据
        return merge(commentList, earliestReplyMap, authorBasicInfoMap, commentLikedCountMap, likeCommentIdList);
    }

    @Override
    public List<CommentResponse> getCommentReplyList(Long commentId, Long earliestReplyId, Integer page) {
        List<CommentDto> replyList = commentMapper.selectCommentReplyList(commentId, earliestReplyId, page * 5);
        if (ObjectUtils.isEmpty(replyList)) {
            return List.of();
        }
        // 获取回复的 comment id
        Set<Long> commentIdSet = replyList.stream().map(CommentDto::getCommentId).collect(Collectors.toSet());
        // 获取回复的 user id
        Set<Long> userIdSet = replyList.stream().map(CommentDto::getUserId).collect(Collectors.toSet());
        // 获取回复的 reply_user_id
        Set<Long> replyUserIdSet = replyList.stream().map(CommentDto::getReplyToUserId)
                .filter(replyToUserId -> !ObjectUtils.isEmpty(replyToUserId)).collect(Collectors.toSet());
        userIdSet.addAll(replyUserIdSet);
        // 根据 comment id 列表获取当前登录用户是否点赞对应评论
        List<Long> likeCommentIdList = commentLikeMapper.selectLikeCommentIdList(UserContextHolder.getUserContext(), commentIdSet);
        // 根据 comment id 列表获取每条评论的点赞数
        Map<Long, Long> commentLikedCountMap = commentLikeMapper.selectCommentLikedCount(commentIdSet);
        // 根据 user id 列表，获取对应用户信息
        Map<Long, AuthorInfoDto> authorBasicInfoMap = getAuthorBasicInfoMap(userIdSet);
        return replyList.stream().map(reply -> {
                    CommentResponse commentResponse = new CommentResponse()
                            .setCommentId(reply.getCommentId())
                            .setContent(reply.getContent())
                            .setReleaseTime(DateUtil.format(reply.getCreateTime()))
                            .setLikedCount(commentLikedCountMap.getOrDefault(reply.getCommentId(), 0L))
                            .setAuthorInfo(authorBasicInfoMap.get(reply.getUserId())
                                    .setLiked(likeCommentIdList.contains(reply.getCommentId())));
                    if (!ObjectUtils.isEmpty(reply.getReplyToUserId())) {
                        commentResponse.setReplyToUserId(reply.getReplyToUserId())
                                .setReplyToUserName(authorBasicInfoMap.get(reply.getReplyToUserId()).getAuthor());
                    }
                    return commentResponse;
                }
        ).toList();
    }

    private static List<CommentListResponse> merge(List<CommentDto> commentList,
                                                   Map<Long, CommentDto> earliestReplyMap,
                                                   Map<Long, AuthorInfoDto> authorBasicInfoMap,
                                                   Map<Long, Long> commentLikedCountMap,
                                                   List<Long> likeCommentIdList) {
        return commentList.stream().map(comment -> {
            CommentListResponse commentListResponse = new CommentListResponse()
                    .setComment(new CommentResponse()
                            .setCommentId(comment.getCommentId())
                            .setContent(comment.getContent())
                            .setReleaseTime(DateUtil.format(comment.getCreateTime()))
                            .setLikedCount(commentLikedCountMap.get(comment.getCommentId()))
                            .setAuthorInfo(authorBasicInfoMap.get(comment.getUserId())
                                    .setLiked(likeCommentIdList.contains(comment.getCommentId()))))
                    .setReplyCount(0L);
            CommentDto earliestReply = earliestReplyMap.get(comment.getCommentId());
            if (!ObjectUtils.isEmpty(earliestReply)) {
                commentListResponse.setReplyCount(earliestReply.getChildCommentCount() - 1)
                        .setEarliestReply(new CommentResponse()
                                .setCommentId(earliestReply.getCommentId())
                                .setContent(earliestReply.getContent())
                                .setReleaseTime(DateUtil.format(earliestReply.getCreateTime()))
                                .setLikedCount(commentLikedCountMap.get(earliestReply.getCommentId()))
                                .setAuthorInfo(authorBasicInfoMap.get(earliestReply.getUserId())
                                        .setLiked(likeCommentIdList.contains(earliestReply.getCommentId()))));
            }
            return commentListResponse;
        }).toList();
    }

    private Map<Long, AuthorInfoDto> getAuthorBasicInfoMap(Set<Long> userIdSet) {
        CommonResult<List<UserInfoResponse>> authorBasicInfoList =
                userServiceClient.getUserBasicInfoList(new UserRequest().setUserIds(userIdSet));
        return authorBasicInfoList.getData().stream()
                .collect(Collectors.toMap(
                        UserInfoResponse::getUserId,
                        user -> new AuthorInfoDto()
                                .setAuthorId(user.getUserId())
                                .setAuthor(user.getUsername())
                                .setAuthorAvatarUrl(user.getAvatarUrl())
                ));
    }
}
