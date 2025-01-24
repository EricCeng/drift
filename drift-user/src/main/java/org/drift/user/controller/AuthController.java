package org.drift.user.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.user.AuthRequest;
import org.drift.user.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiakui_zeng
 * @date 2024/12/27 17:22
 */
@RestController
@RequestMapping("/drift/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private CommonResult<?> register(@RequestBody AuthRequest request) {
        userService.register(request);
        return CommonResult.success();
    }

    @PostMapping("/login")
    private CommonResult<String> login(@RequestBody AuthRequest request) {
        return CommonResult.success(userService.login(request));
    }

    @GetMapping("/check")
    private CommonResult<?> check() {
        return CommonResult.success();
    }
}
