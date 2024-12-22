package org.drift.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.post.bean.Post;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:38
 */
public interface PostMapper extends BaseMapper<Post> {
    List<Post> selectPostList(Long authorId, List<Long> followingUserIds, Integer page);
}
