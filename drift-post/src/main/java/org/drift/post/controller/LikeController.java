package org.drift.post.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.post.PostRequest;
import org.drift.post.service.LikeService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:40
 */
@RestController
@RequestMapping("/drift/like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public CommonResult<?> like(@RequestBody PostRequest request) {
        likeService.like(request);
        return CommonResult.success();
    }

    @PostMapping("/cancel")
    public CommonResult<?> cancel(@RequestBody PostRequest request) {
        likeService.cancel(request);
        return CommonResult.success();
    }

    @PostMapping("/liked_count")
    public CommonResult<Long> getUserLikedCount(@RequestParam("authorId") Long authorId) {
        return CommonResult.success(likeService.getUserLikedCount(authorId));
    }
}
