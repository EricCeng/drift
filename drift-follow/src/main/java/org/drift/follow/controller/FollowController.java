package org.drift.follow.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.follow.FollowRequest;
import org.drift.common.pojo.follow.FollowResponse;
import org.drift.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
@RestController
@RequestMapping("/drift/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public CommonResult<?> follow(@RequestBody FollowRequest request) {
        followService.follow(request);
        return CommonResult.success();
    }

    @PostMapping("/unfollow")
    public CommonResult<?> unfollow(@RequestBody FollowRequest request) {
        followService.unfollow(request);
        return CommonResult.success();
    }

    @GetMapping("/info")
    public CommonResult<FollowResponse> getFollowInfo(@RequestParam("user_id") Long userId) {
        return CommonResult.success(followService.getFollowInfo(userId));
    }

    @GetMapping("following_users")
    public CommonResult<List<Long>> getFollowingUsers(@RequestParam("user_id") Long userId) {
        return CommonResult.success(followService.getFollowingUsers(userId));
    }
}
