package org.drift.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.common.pojo.post.PostRequest;
import org.drift.post.bean.Like;
import org.drift.post.mapper.LikeMapper;
import org.drift.post.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:38
 */
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeMapper likeMapper;

    public LikeServiceImpl(LikeMapper likeMapper) {
        this.likeMapper = likeMapper;
    }

    @Override
    public void like(PostRequest request) {
        likeMapper.insert(new Like()
                .setPostId(request.getPostId())
                .setAuthorId(request.getAuthorId())
                .setUserId(request.getUserId()));
    }

    @Override
    public void cancel(PostRequest request) {
        Like like = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getPostId, request.getPostId())
                .eq(Like::getAuthorId, request.getAuthorId())
                .eq(Like::getUserId, request.getUserId()));
        likeMapper.deleteById(like);
    }

    @Override
    public Long getUserLikedCount(Long authorId) {
        return likeMapper.selectUserLikedCount(authorId);
    }

    @Override
    public List<PostLikedCountDto> getPostLikedCountList(Set<Long> postIds) {
        return likeMapper.selectPostLikedCount(postIds);
    }

    @Override
    public Boolean isLiked(Long userId, Long postId) {
        return likeMapper.exists(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, userId)
                .eq(Like::getPostId, postId));
    }

    @Override
    public List<Long> isLikedForPosts(Long userId, Set<Long> postIds) {
        List<Like> likes = likeMapper.selectList(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, userId)
                .eq(Like::getPostId, postIds));
        if (ObjectUtils.isEmpty(likes)) {
            return new ArrayList<>();
        }
        return likes.stream().map(Like::getPostId).collect(Collectors.toList());
    }
}
