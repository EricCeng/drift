package org.drift.follow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.follow.bean.Follow;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
public interface FollowMapper extends BaseMapper<Follow> {
    long selectFollowingCount(Long userId);

    long selectFollowerCount(Long userId);
}
