package org.drift.follow.service;

import org.drift.common.pojo.follow.FollowResponse;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
public interface FollowService {
    void follow(Long followedId);

    void unfollow(Long followedId);

    FollowResponse getFollowInfo(Long userId);

    List<Long> getFollowingUsers(Long userId);

    Boolean isFollowing(Long authorId);
}
