package org.drift.comment.service;

import org.drift.common.pojo.comment.CommentResponse;

import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:02
 */
public interface CommentService {
    Long getPostCommentCount(Long postId);

    List<CommentResponse> getPostCommentList(Long postId);
}
