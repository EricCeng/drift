package org.drift.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.pojo.post.PostRequest;
import org.drift.post.bean.Collection;
import org.drift.post.mapper.CollectionMapper;
import org.drift.post.service.CollectionService;
import org.springframework.stereotype.Service;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:38
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionMapper collectionMapper;

    public CollectionServiceImpl(CollectionMapper collectionMapper) {
        this.collectionMapper = collectionMapper;
    }

    @Override
    public void collect(PostRequest request) {
        collectionMapper.insert(new Collection()
                .setPostId(request.getPostId())
                .setAuthorId(request.getAuthorId())
                .setUserId(request.getUserId()));
    }

    @Override
    public void cancel(PostRequest request) {
        Collection collection = collectionMapper.selectOne(new LambdaQueryWrapper<Collection>()
                .eq(Collection::getPostId, request.getPostId())
                .eq(Collection::getAuthorId, request.getAuthorId())
                .eq(Collection::getUserId, request.getUserId()));
        collectionMapper.deleteById(collection);
    }

    @Override
    public Long getUserCollectedCount(Long authorId) {
        return collectionMapper.selectUserCollectedCount(authorId);
    }
}
