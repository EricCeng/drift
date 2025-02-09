package org.drift.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.context.UserContextHolder;
import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.post.bean.Like;
import org.drift.post.mapper.LikeMapper;
import org.drift.post.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
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
    public void like(Long postId, Long authorId) {
        likeMapper.insert(new Like()
                .setPostId(postId)
                .setAuthorId(authorId)
                .setUserId(UserContextHolder.getUserContext()));
    }

    @Override
    public void cancel(Long postId, Long authorId) {
        Like like = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getPostId, postId)
                .eq(Like::getAuthorId, authorId)
                .eq(Like::getUserId, UserContextHolder.getUserContext()));
        likeMapper.deleteById(like);
    }

    @Override
    public Long getUserLikedCount(Long userId) {
        return likeMapper.selectCount(new LambdaQueryWrapper<Like>().eq(Like::getAuthorId, userId));
    }

    @Override
    public List<PostLikedCountDto> getPostLikedCountList(List<Long> postIds) {
        return likeMapper.selectPostLikedCount(postIds);
    }

    @Override
    public Boolean isLiked(Long postId) {
        return likeMapper.exists(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, UserContextHolder.getUserContext())
                .eq(Like::getPostId, postId));
    }

    @Override
    public List<Long> isLikedForPosts(List<Long> postIds) {
        List<Like> likes = likeMapper.selectList(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, UserContextHolder.getUserContext())
                .eq(Like::getPostId, postIds));
        if (ObjectUtils.isEmpty(likes)) {
            return new ArrayList<>();
        }
        return likes.stream().map(Like::getPostId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getLikePostIds(Integer page) {
        return likeMapper.selectLikePostIds(UserContextHolder.getUserContext(), page);
    }

    @Override
    public Long getPostLikedCount(Long postId) {
        return likeMapper.selectCount(new LambdaQueryWrapper<Like>().eq(Like::getPostId, postId));
    }
}
