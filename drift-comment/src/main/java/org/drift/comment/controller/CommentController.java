package org.drift.comment.controller;

import org.drift.comment.service.CommentService;
import org.drift.common.api.CommonResult;
import org.drift.common.pojo.comment.CommentRequest;
import org.drift.common.pojo.comment.CommentListResponse;
import org.drift.common.pojo.comment.CommentResponse;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<List<CommentListResponse>> getPostCommentList(@RequestParam("post_id") Long postId,
                                                                      @RequestParam(value = "author_id", required = false) Long authorId,
                                                                      @RequestParam("page") Integer page) {
        return CommonResult.success(commentService.getPostCommentList(postId, authorId, page));
    }

    @GetMapping("/child_comment_list")
    public CommonResult<List<CommentResponse>> getChildCommentList(@RequestParam("parent_comment_id") Long parentCommentId,
                                                                   @RequestParam("top_child_comment_id") Long topChildCommentId,
                                                                   @RequestParam("page") Integer page) {
        return CommonResult.success(commentService.getChildCommentList(parentCommentId, topChildCommentId, page));
    }

    @PostMapping("/publish")
    public CommonResult<?> publish(@RequestBody CommentRequest request) {
        commentService.publish(request);
        return CommonResult.success();
    }
}
