package org.drift.post.service;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:36
 */
public interface CollectionService {
    void collect(Long postId, Long authorId);

    void cancel(Long postId, Long authorId);

    Long getUserCollectedCount(Long authorId);
}
