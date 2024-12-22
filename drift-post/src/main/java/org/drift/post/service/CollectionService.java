package org.drift.post.service;

import org.drift.common.pojo.post.PostRequest;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:36
 */
public interface CollectionService {
    void collect(PostRequest request);

    void cancel(PostRequest request);

    Long getUserCollectedCount(Long authorId);
}
