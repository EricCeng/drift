package org.drift.post.service;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:36
 */
public interface CollectionService {
    void collect(Long postId, Long authorId);

    void cancel(Long postId, Long authorId);

    Long getUserCollectedCount(Long userId);

    List<Long> getUserCollectionPostIds(Long userId, Integer page);

    Boolean isCollected(Long postId);

    Long getPostCollectedCount(Long postId);
}
