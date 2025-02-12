package org.drift.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.comment.bean.Comment;
import org.drift.comment.mapper.CommentMapper;
import org.drift.comment.service.CommentService;
import org.drift.common.pojo.comment.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:02
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public Long getPostCommentCount(Long postId) {
        return commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId));
    }

    @Override
    public List<CommentResponse> getPostCommentList(Long postId) {

        return List.of();
    }
}
