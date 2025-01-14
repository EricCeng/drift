package org.drift.post.controller;

import org.drift.common.api.CommonResult;
import org.drift.post.service.LikeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult<?> like(@RequestParam("post_id") Long postId,
                                @RequestParam("author_id") Long authorId) {
        likeService.like(postId, authorId);
        return CommonResult.success();
    }

    @PostMapping("/cancel")
    public CommonResult<?> cancel(@RequestParam("post_id") Long postId,
                                  @RequestParam("author_id") Long authorId) {
        likeService.cancel(postId, authorId);
        return CommonResult.success();
    }

    @PostMapping("/liked_count")
    public CommonResult<Long> getUserLikedCount(@RequestParam("authorId") Long authorId) {
        return CommonResult.success(likeService.getUserLikedCount(authorId));
    }
}
