package org.drift.follow.service;

import org.drift.common.pojo.follow.FollowRequest;
import org.drift.common.pojo.follow.FollowResponse;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
public interface FollowService {
    void follow(FollowRequest request);

    void unfollow(FollowRequest request);

    FollowResponse getFollowInfo(Long userId);

    List<Long> getFollowingUsers(Long userId);
}
