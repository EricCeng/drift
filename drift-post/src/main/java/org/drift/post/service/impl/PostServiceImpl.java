package org.drift.post.service.impl;

import org.drift.common.api.CommonResult;
import org.drift.common.context.UserContextHolder;
import org.drift.common.feign.FollowServiceClient;
import org.drift.common.feign.UserServiceClient;
import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.common.pojo.post.PostResponse;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.drift.post.bean.Post;
import org.drift.post.mapper.PostMapper;
import org.drift.post.service.LikeService;
import org.drift.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:37
 */
@Service
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final LikeService likeService;
    private final UserServiceClient userServiceClient;
    private final FollowServiceClient followServiceClient;

    public PostServiceImpl(PostMapper postMapper,
                           LikeService likeService,
                           UserServiceClient userServiceClient,
                           FollowServiceClient followServiceClient) {
        this.postMapper = postMapper;
        this.likeService = likeService;
        this.userServiceClient = userServiceClient;
        this.followServiceClient = followServiceClient;
    }

    @Override
    public List<PostResponse> getAllPosts(Boolean following, Integer page) {
        List<Long> followingUserIds = new ArrayList<>();
        if (following != null && following) {
            // 只有登录用户才有获取关注列表动态的场景
            followingUserIds = followServiceClient.getFollowingUsers(UserContextHolder.getUserContext()).getData();
            if (ObjectUtils.isEmpty(followingUserIds)) {
                return new ArrayList<>();
            }
        }
        // 获取动态列表
        List<Post> posts = postMapper.selectPostList(null, followingUserIds, page);
        if (ObjectUtils.isEmpty(posts)) {
            return new ArrayList<>();
        }
        // 根据动态ID获取对应的点赞数及登录用户的点赞状态
        Set<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toSet());
        List<PostLikedCountDto> postLikedCountList = likeService.getPostLikedCountList(postIds);
        Map<Long, Long> postLikedCountMap = postLikedCountList.stream()
                .collect(Collectors.toMap(PostLikedCountDto::getPostId, PostLikedCountDto::getLikedCount));
        // 获取登录用户的点赞状态
        List<Long> likedPostIds = likeService.isLikedForPosts(postIds);
        // 根据动态作者ID获取对应的作者信息
        Set<Long> authorIds = posts.stream().map(Post::getUserId).collect(Collectors.toSet());
        CommonResult<List<UserInfoResponse>> authorBasicInfoList =
                userServiceClient.getUserBasicInfoList(new UserRequest().setUserIds(authorIds));
        Map<Long, UserInfoResponse> authorBasicInfoMap = authorBasicInfoList.getData().stream()
                .collect(Collectors.toMap(UserInfoResponse::getUserId, user -> user));
        return posts.stream().map(post -> {
            Long postId = post.getId();
            UserInfoResponse authorBasicInfo = authorBasicInfoMap.get(post.getUserId());
            return new PostResponse()
                    .setPostId(postId)
                    .setTitle(post.getTitle())
                    .setFirstImageUrl("")
                    .setAuthor(authorBasicInfo.getUsername())
                    .setAuthorAvatarUrl(authorBasicInfo.getAvatarUrl())
                    .setLiked(likedPostIds.contains(postId))
                    .setLikedCount(Optional.ofNullable(postLikedCountMap.get(postId)).orElse(0L));
        }).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPersonalPosts(Long authorId, Integer page) {
        if (authorId == null) {
            authorId = UserContextHolder.getUserContext();
        }
        // 获取动态列表
        List<Post> posts = postMapper.selectPostList(authorId, null, page);
        if (ObjectUtils.isEmpty(posts)) {
            return new ArrayList<>();
        }
        // 根据动态ID获取对应的点赞数及登录用户的点赞状态
        Set<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toSet());
        List<PostLikedCountDto> postLikedCountList = likeService.getPostLikedCountList(postIds);
        Map<Long, Long> postLikedCountMap = postLikedCountList.stream()
                .collect(Collectors.toMap(PostLikedCountDto::getPostId, PostLikedCountDto::getLikedCount));
        List<Long> likedPostIds = likeService.isLikedForPosts(postIds);
        return posts.stream().map(post -> {
            Long postId = post.getId();
            return new PostResponse()
                    .setPostId(postId)
                    .setTitle(post.getTitle())
                    .setFirstImageUrl("")
                    .setLiked(likedPostIds.contains(postId))
                    .setLikedCount(Optional.ofNullable(postLikedCountMap.get(postId)).orElse(0L));
        }).collect(Collectors.toList());
    }
}
