package org.drift.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.drift.post.bean.Collection;

import java.util.List;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:39
 */
public interface CollectionMapper extends BaseMapper<Collection> {
    List<Long> selectUserCollectionPostIds(Long userId, Integer page);
}
