package org.drift.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.api.CommonResult;
import org.drift.common.context.UserContextHolder;
import org.drift.common.feign.CommentServiceClient;
import org.drift.common.feign.FollowServiceClient;
import org.drift.common.feign.UserServiceClient;
import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.common.pojo.post.PostDetailResponse;
import org.drift.common.pojo.post.PostResponse;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.drift.common.util.DateUtil;
import org.drift.post.bean.Post;
import org.drift.post.mapper.PostMapper;
import org.drift.post.service.CollectionService;
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
    private final CollectionService collectionService;
    private final UserServiceClient userServiceClient;
    private final FollowServiceClient followServiceClient;
    private final CommentServiceClient commentServiceClient;

    public PostServiceImpl(PostMapper postMapper,
                           LikeService likeService,
                           CollectionService collectionService,
                           UserServiceClient userServiceClient,
                           FollowServiceClient followServiceClient,
                           CommentServiceClient commentServiceClient) {
        this.postMapper = postMapper;
        this.likeService = likeService;
        this.collectionService = collectionService;
        this.userServiceClient = userServiceClient;
        this.followServiceClient = followServiceClient;
        this.commentServiceClient = commentServiceClient;
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
        // 根据各动态的点赞数
        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        Map<Long, Long> postLikedCountMap = getPostLikedCountMap(postIds);
        // 获取登录用户关于各动态的点赞状态
        List<Long> likedPostIds = likeService.isLikedForPosts(postIds);
        // 根据动态作者ID获取对应的作者信息
        Map<Long, UserInfoResponse> authorBasicInfoMap = getAuthorBasicInfoMap(posts);
        // 整合数据
        return merge(posts, authorBasicInfoMap, likedPostIds, postLikedCountMap);
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
        // 根据各动态的点赞数
        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        Map<Long, Long> postLikedCountMap = getPostLikedCountMap(postIds);
        // 获取登录用户关于各动态的点赞状态
        List<Long> likedPostIds = likeService.isLikedForPosts(postIds);
        // 获取作者信息
        UserInfoResponse userInfo = userServiceClient.getUserBasicInfo(authorId).getData();
        // 整合数据
        return posts.stream().map(post -> {
            Long postId = post.getId();
            return new PostResponse()
                    .setPostId(postId)
                    .setTitle(post.getTitle())
                    .setFirstImageUrl("")
                    .setAuthorId(post.getUserId())
                    .setAuthor(userInfo.getUsername())
                    .setAuthorAvatarUrl(userInfo.getAvatarUrl())
                    .setLiked(likedPostIds.contains(postId))
                    .setLikedCount(Optional.ofNullable(postLikedCountMap.get(postId)).orElse(0L));
        }).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getLikePosts(Integer page) {
        List<Long> postIds = likeService.getLikePostIds(page);
        if (ObjectUtils.isEmpty(postIds)) {
            return new ArrayList<>();
        }
        // 获取动态列表
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>()
                .select(Post::getId, Post::getUserId, Post::getTitle, Post::getImageUrl)
                .in(Post::getId, postIds));
        // 根据各动态的点赞数
        Map<Long, Long> postLikedCountMap = getPostLikedCountMap(postIds);
        // 根据动态作者ID获取对应的作者信息
        Map<Long, UserInfoResponse> authorBasicInfoMap = getAuthorBasicInfoMap(posts);
        // 整合数据
        return posts.stream().map(post -> {
            Long postId = post.getId();
            UserInfoResponse authorBasicInfo = authorBasicInfoMap.get(post.getUserId());
            return new PostResponse()
                    .setPostId(postId)
                    .setTitle(post.getTitle())
                    .setFirstImageUrl("")
                    .setAuthorId(post.getUserId())
                    .setAuthor(authorBasicInfo.getUsername())
                    .setAuthorAvatarUrl(authorBasicInfo.getAvatarUrl())
                    .setLiked(true)
                    .setLikedCount(Optional.ofNullable(postLikedCountMap.get(postId)).orElse(0L));
        }).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getCollectionPosts(Long authorId, Integer page) {
        if (authorId == null) {
            authorId = UserContextHolder.getUserContext();
        }
        List<Long> postIds = collectionService.getUserCollectionPostIds(authorId, page);
        if (ObjectUtils.isEmpty(postIds)) {
            return new ArrayList<>();
        }
        // 获取动态列表
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>()
                .select(Post::getId, Post::getUserId, Post::getTitle, Post::getImageUrl)
                .in(Post::getId, postIds));
        // 根据各动态的点赞数
        Map<Long, Long> postLikedCountMap = getPostLikedCountMap(postIds);
        // 获取登录用户关于各动态的点赞状态
        List<Long> likedPostIds = likeService.isLikedForPosts(postIds);
        // 根据动态作者ID获取对应的作者信息
        Map<Long, UserInfoResponse> authorBasicInfoMap = getAuthorBasicInfoMap(posts);
        return merge(posts, authorBasicInfoMap, likedPostIds, postLikedCountMap);
    }

    @Override
    public PostDetailResponse getPostDetail(Long postId) {
        // 获取动态信息
        Post post = postMapper.selectById(postId);
        // 获取作者信息
        Long authorId = post.getUserId();
        UserInfoResponse authorBasicInfo = userServiceClient.getUserBasicInfo(authorId).getData();
        // 获取登录用户关于该动态的点赞状态
        Boolean liked = likeService.isLiked(postId);
        // 获取登录用户关于该动态的收藏状态
        Boolean collected = collectionService.isCollected(postId);
        // 获取动态点赞数
        Long likedCount = likeService.getPostLikedCount(postId);
        // 获取动态收藏数
        Long collectedCount = collectionService.getPostCollectedCount(postId);
        // 获取动态评论数
        Long commentCount = commentServiceClient.getPostCommentCount(postId).getData();
        return new PostDetailResponse()
                .setAuthorId(authorId)
                .setAuthor(authorBasicInfo.getUsername())
                .setAuthorAvatarUrl(authorBasicInfo.getAvatarUrl())
                .setPostId(postId)
                .setTitle(post.getTitle())
                .setContent(post.getContent())
                .setImageUrlList(new ArrayList<>())
                .setReleaseTime(DateUtil.format(post.getUpdateTime()))
                .setEdited(post.getCreateTime().equals(post.getUpdateTime()))
                .setLiked(liked)
                .setCollected(collected)
                .setLikedCount(likedCount)
                .setCollectedCount(collectedCount)
                .setCommentCount(commentCount);
    }

    private Map<Long, Long> getPostLikedCountMap(List<Long> postIds) {
        List<PostLikedCountDto> postLikedCountList = likeService.getPostLikedCountList(postIds);
        return postLikedCountList.stream()
                .collect(Collectors.toMap(PostLikedCountDto::getPostId, PostLikedCountDto::getLikedCount));
    }

    private Map<Long, UserInfoResponse> getAuthorBasicInfoMap(List<Post> posts) {
        Set<Long> authorIds = posts.stream().map(Post::getUserId).collect(Collectors.toSet());
        CommonResult<List<UserInfoResponse>> authorBasicInfoList =
                userServiceClient.getUserBasicInfoList(new UserRequest().setUserIds(authorIds));
        return authorBasicInfoList.getData().stream()
                .collect(Collectors.toMap(UserInfoResponse::getUserId, user -> user));
    }

    private List<PostResponse> merge(List<Post> posts,
                                     Map<Long, UserInfoResponse> authorBasicInfoMap,
                                     List<Long> likedPostIds,
                                     Map<Long, Long> postLikedCountMap) {
        return posts.stream().map(post -> {
            Long postId = post.getId();
            UserInfoResponse authorBasicInfo = authorBasicInfoMap.get(post.getUserId());
            return new PostResponse()
                    .setPostId(postId)
                    .setTitle(post.getTitle())
                    .setFirstImageUrl("")
                    .setAuthorId(post.getUserId())
                    .setAuthor(authorBasicInfo.getUsername())
                    .setAuthorAvatarUrl(authorBasicInfo.getAvatarUrl())
                    .setLiked(likedPostIds.contains(postId))
                    .setLikedCount(Optional.ofNullable(postLikedCountMap.get(postId)).orElse(0L));
        }).collect(Collectors.toList());
    }
}
