package org.drift.user.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.user.RegisterationRequest;
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

    @PostMapping("/register")
    private CommonResult<?> register(@RequestBody RegisterationRequest request) {
        userService.register(request);
        return CommonResult.success();
    }

    @GetMapping("/info")
    private CommonResult<UserInfoResponse> getUserInfo(@RequestParam("user_id") Long userId) {
        return CommonResult.success(userService.getUserInfo(userId));
    }

    @GetMapping("/basic_info_list")
    private CommonResult<List<UserInfoResponse>> getUserBasicInfo(@RequestBody UserRequest request) {
        return CommonResult.success(userService.getBasicUserInfoList(request));
    }

}
