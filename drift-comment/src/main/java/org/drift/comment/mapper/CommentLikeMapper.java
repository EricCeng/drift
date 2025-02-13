package org.drift.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.drift.comment.bean.CommentLike;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jiakui_zeng
 * @date 2025/2/13 16:39
 */
public interface CommentLikeMapper extends BaseMapper<CommentLike> {
    List<Long> selectLikeCommentIdList(Long userId, Set<Long> commentIds);

    @MapKey("comment_id")
    Map<Long, Long> selectCommentLikedCount(Set<Long> commentIdSet);
}
