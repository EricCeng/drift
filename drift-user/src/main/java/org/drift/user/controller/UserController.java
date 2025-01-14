package org.drift.user.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.user.UserInfoResponse;
import org.drift.common.pojo.user.UserRequest;
import org.drift.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:12
 */
@RestController
@RequestMapping("/drift/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    private CommonResult<UserInfoResponse> getUserInfo(@RequestParam(defaultValue = "user_id", required = false) Long userId) {
        return CommonResult.success(userService.getUserInfo(userId));
    }

    @GetMapping("/basic_info_list")
    private CommonResult<List<UserInfoResponse>> getUserBasicInfo(@RequestBody UserRequest request) {
        return CommonResult.success(userService.getBasicUserInfoList(request));
    }

}
