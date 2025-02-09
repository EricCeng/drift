package org.drift.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.common.pojo.like.PostLikedCountDto;
import org.drift.post.bean.Like;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:38
 */
public interface LikeMapper extends BaseMapper<Like> {
    List<PostLikedCountDto> selectPostLikedCount(List<Long> postIds);

    List<Long> selectLikePostIds(Long userId, Integer page);
}
