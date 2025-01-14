package org.drift.user.service;

import org.drift.common.pojo.user.AuthRequest;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:11
 */
public interface UserService {
    void register(AuthRequest request);

    String login(AuthRequest request);

    UserInfoResponse getUserInfo(Long userId);

    List<UserInfoResponse> getBasicUserInfoList(UserRequest request);
}
