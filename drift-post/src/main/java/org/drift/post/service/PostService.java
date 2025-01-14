package org.drift.post.service;

import org.drift.common.pojo.post.PostResponse;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:35
 */
public interface PostService {
    List<PostResponse> getAllPosts(Boolean following, Integer page);

    List<PostResponse> getPersonalPosts(Long authorId, Integer page);
}
