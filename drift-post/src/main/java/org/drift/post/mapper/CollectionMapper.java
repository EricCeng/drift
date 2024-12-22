package org.drift.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.post.bean.Collection;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:39
 */
public interface CollectionMapper extends BaseMapper<Collection> {
    long selectUserCollectedCount(Long authorId);
}
