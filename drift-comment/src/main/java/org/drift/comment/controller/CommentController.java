package org.drift.comment.controller;

import org.drift.comment.service.CommentService;
import org.drift.common.api.CommonResult;
import org.drift.common.pojo.comment.CommentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:06
 */
@RestController
@RequestMapping("/drift/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment_count")
    public CommonResult<Long> getPostCommentCount(@RequestParam("post_id") Long postId) {
        return CommonResult.success(commentService.getPostCommentCount(postId));
    }

    @GetMapping("/list")
    public CommonResult<List<CommentResponse>> getPostCommentList(@RequestParam("post_id") Long postId) {
        return CommonResult.success(commentService.getPostCommentList(postId));
    }
}
