package org.drift.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.drift.common.api.CommonResult;
import org.drift.common.api.ResultCode;
import org.drift.common.context.UserContextHolder;
import org.drift.common.exception.ApiException;
import org.drift.common.feign.FollowServiceClient;
import org.drift.common.feign.PostServiceClient;
import org.drift.common.pojo.follow.FollowResponse;
import org.drift.common.pojo.user.AuthRequest;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.drift.common.util.JwtUtil;
import org.drift.user.bean.User;
import org.drift.user.mapper.UserMapper;
import org.drift.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:12
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PostServiceClient postServiceClient;
    private final FollowServiceClient followServiceClient;

    public UserServiceImpl(UserMapper userMapper,
                           PostServiceClient postServiceClient,
                           FollowServiceClient followServiceClient) {
        this.userMapper = userMapper;
        this.postServiceClient = postServiceClient;
        this.followServiceClient = followServiceClient;
    }

    @Override
    public void register(AuthRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getId).eq(User::getPhoneNumber, phoneNumber));
        if (!ObjectUtils.isEmpty(user)) {
            throw new ApiException("该手机号已被注册");
        }
        userMapper.insert(new User().setPhoneNumber(phoneNumber).setPassword(password));
    }

    @Override
    public String login(AuthRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getPassword)
                .eq(User::getPhoneNumber, request.getPhoneNumber()));
        if (ObjectUtils.isEmpty(user)) {
            throw new ApiException("该手机号尚未注册，请先注册");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ApiException("密码错误，请重试");
        }
        return JwtUtil.createToken(user.getId());
    }

    @Override
    public void check() {
        boolean exists = userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getId, UserContextHolder.getUserContext()));
        if (!exists) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
    }

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            userId = UserContextHolder.getUserContext();
        }
        // 获取用户信息
        User user = userMapper.selectById(userId);
        String birthday = user.getBirthday();
        Integer age = StringUtils.hasLength(birthday)
                ? (int) ChronoUnit.YEARS.between(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.now())
                : null;
        // 获取关注数、粉丝数
        CommonResult<FollowResponse> followInfo = followServiceClient.getFollowInfo(userId);
        // 获取获赞数、获收藏数
        CommonResult<Long> likedCount = postServiceClient.getUserLikedCount(userId);
        CommonResult<Long> collectedCount = postServiceClient.getUserCollectedCount(userId);
        return new UserInfoResponse()
                .setUserId(userId)
                .setUsername(user.getUsername())
                .setAvatarUrl(user.getAvatarUrl())
                .setBio(user.getBio())
                .setAge(age)
                .setGender(user.getGender())
                .setRegion(user.getRegion())
                .setOccupation(user.getOccupation())
                .setSchool(user.getSchool())
                .setFollowingCount(followInfo.getData().getFollowingCount())
                .setFollowerCount(followInfo.getData().getFollowerCount())
                .setLikedCount(likedCount.getData())
                .setCollectedCount(collectedCount.getData());
    }

    @Override
    public UserInfoResponse getUserBasicInfo(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            userId = UserContextHolder.getUserContext();
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getAvatarUrl)
                .eq(User::getId, userId));
        return new UserInfoResponse()
                .setUserId(userId)
                .setUsername(user.getUsername())
                .setAvatarUrl(user.getAvatarUrl());
    }

    @Override
    public List<UserInfoResponse> getBasicUserInfoList(UserRequest request) {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getAvatarUrl)
                .in(User::getId, request.getUserIds()));
        return users.stream().map(user -> new UserInfoResponse()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setAvatarUrl(user.getAvatarUrl())
        ).collect(Collectors.toList());
    }
}
