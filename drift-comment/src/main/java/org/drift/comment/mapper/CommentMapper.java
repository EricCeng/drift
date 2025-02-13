package org.drift.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.comment.bean.Comment;
import org.drift.common.pojo.comment.CommentDto;

import java.util.List;
import java.util.Set;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:02
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> selectParentCommentList(Long postId, Integer page);

    List<CommentDto> selectChildCommentList(Set<Long> parentCommentIds);
}
