package org.drift.comment.service;

import org.drift.common.pojo.comment.CommentRequest;
import org.drift.common.pojo.comment.CommentListResponse;
import org.drift.common.pojo.comment.CommentResponse;

import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:02
 */
public interface CommentService {
    void publish(CommentRequest request);

    Long getPostCommentCount(Long postId);

    List<CommentListResponse> getPostCommentList(Long postId, Long authorId, Integer page);

    List<CommentResponse> getChildCommentList(Long parentCommentId, Long topChildCommentId, Integer page);
}
