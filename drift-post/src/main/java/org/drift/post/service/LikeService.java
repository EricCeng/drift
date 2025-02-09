package org.drift.post.service;

import org.drift.common.pojo.like.PostLikedCountDto;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:35
 */
public interface LikeService {
    void like(Long postId, Long authorId);

    void cancel(Long postId, Long authorId);

    Long getUserLikedCount(Long userId);

    List<PostLikedCountDto> getPostLikedCountList(List<Long> postIds);

    Boolean isLiked(Long postId);

    List<Long> isLikedForPosts(List<Long> postIds);

    List<Long> getLikePostIds(Integer page);

    Long getPostLikedCount(Long postId);
}
