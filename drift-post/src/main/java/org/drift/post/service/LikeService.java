package org.drift.post.service;

import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.common.pojo.post.PostRequest;

import java.util.List;
import java.util.Set;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:35
 */
public interface LikeService {
    void like(PostRequest request);

    void cancel(PostRequest request);

    Long getUserLikedCount(Long authorId);

    List<PostLikedCountDto> getPostLikedCountList(Set<Long> postIds);

    Boolean isLiked(Long userId, Long postId);

    List<Long> isLikedForPosts(Long userId, Set<Long> postIds);
}
