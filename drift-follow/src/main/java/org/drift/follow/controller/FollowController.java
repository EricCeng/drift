package org.drift.follow.controller;

import org.drift.common.api.CommonResult;
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
    public CommonResult<?> follow(@RequestParam("followed_id") Long followedId) {
        followService.follow(followedId);
        return CommonResult.success();
    }

    @PostMapping("/unfollow")
    public CommonResult<?> unfollow(@RequestParam("followed_id") Long followedId) {
        followService.unfollow(followedId);
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

    @GetMapping("is_following")
    public CommonResult<Boolean> isFollowing(@RequestParam("author_id") Long authorId) {
        return CommonResult.success(followService.isFollowing(authorId));
    }
}
