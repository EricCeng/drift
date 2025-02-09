package org.drift.post.controller;

import org.drift.common.api.CommonResult;
import org.drift.common.pojo.post.PostDetailResponse;
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

    @GetMapping("/all")
    public CommonResult<List<PostResponse>> getAllPosts(@RequestParam(value = "following", required = false) Boolean following,
                                                        @RequestParam("page") Integer page) {
        return CommonResult.success(postService.getAllPosts(following, page));
    }

    @GetMapping("/personal")
    public CommonResult<List<PostResponse>> getPersonalPosts(@RequestParam(value = "author_id", required = false) Long authorId,
                                                             @RequestParam(value = "page") Integer page) {
        return CommonResult.success(postService.getPersonalPosts(authorId, page));
    }

    @GetMapping("/like")
    public CommonResult<List<PostResponse>> getLikePosts(@RequestParam(value = "page") Integer page) {
        return CommonResult.success(postService.getLikePosts(page));
    }

    @GetMapping("/collection")
    public CommonResult<List<PostResponse>> getCollectionPosts(@RequestParam(value = "author_id", required = false) Long authorId,
                                                               @RequestParam(value = "page") Integer page) {
        return CommonResult.success(postService.getCollectionPosts(authorId, page));
    }

    @GetMapping("/detail")
    public CommonResult<PostDetailResponse> getPostDetail(@RequestParam(value = "post_id") Long postId) {
        return CommonResult.success(postService.getPostDetail(postId));
    }
}
