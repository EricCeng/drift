package org.drift.post.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.post.PostResponse;
import org.drift.post.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:39
 */
@RestController
@RequestMapping("/drift/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all_posts")
    public CommonResult<List<PostResponse>> getAllPosts(@RequestParam("user_id") Long userId,
                                                        @RequestParam("page") Integer page) {
        return CommonResult.success(postService.getAllPosts(userId, page));
    }

    @GetMapping("/personal_posts")
    public CommonResult<List<PostResponse>> getPersonalPosts(@RequestParam("user_id") Long userId,
                                                             @RequestParam("author_id") Long authorId,
                                                             @RequestParam("page") Integer page) {
        return CommonResult.success(postService.getPersonalPosts(userId, authorId, page));
    }
}
