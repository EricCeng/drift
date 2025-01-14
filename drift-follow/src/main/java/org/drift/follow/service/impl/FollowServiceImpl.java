package org.drift.follow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.context.UserContextHolder;
import org.drift.common.pojo.follow.FollowResponse;
import org.drift.follow.bean.Follow;
import org.drift.follow.mapper.FollowMapper;
import org.drift.follow.service.FollowService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowMapper followMapper;

    public FollowServiceImpl(FollowMapper followMapper) {
        this.followMapper = followMapper;
    }

    @Override
    public void follow(Long followedId) {
        followMapper.insert(new Follow().setFollowerId(UserContextHolder.getUserContext()).setFollowedId(followedId));
    }

    @Override
    public void unfollow(Long followedId) {
        Follow follow = followMapper.selectOne(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, UserContextHolder.getUserContext())
                .eq(Follow::getFollowedId, followedId));
        followMapper.deleteById(follow);
    }

    @Override
    public FollowResponse getFollowInfo(Long userId) {
        return new FollowResponse()
                .setFollowingCount(followMapper.selectFollowingCount(userId))
                .setFollowerCount(followMapper.selectFollowerCount(userId));
    }

    @Override
    public List<Long> getFollowingUsers(Long userId) {
        List<Follow> follows = followMapper.selectList(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, userId));
        if (ObjectUtils.isEmpty(follows)) {
            return new ArrayList<>();
        }
        return follows.stream().map(Follow::getFollowedId).collect(Collectors.toList());
    }
}
