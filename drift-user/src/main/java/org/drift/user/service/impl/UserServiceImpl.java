package org.drift.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.api.CommonResult;
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
        String username = request.getUsername();
        String password = request.getPassword();
        String rePassword = request.getRePassword();
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password) || !StringUtils.hasLength(rePassword)) {
            throw new ApiException("用户名或密码不能为空");
        }
        if (!password.equals(rePassword)) {
            throw new ApiException("两次密码不一致");
        }
        userMapper.insert(new User().setUsername(username).setPassword(password));
    }

    @Override
    public String login(AuthRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .eq(User::getUsername, request.getUsername())
                .eq(User::getPassword, request.getPassword()));
        return JwtUtil.createToken(user.getId(), null);
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
