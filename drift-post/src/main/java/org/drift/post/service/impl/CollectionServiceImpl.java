package org.drift.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.drift.common.context.UserContextHolder;
import org.drift.post.bean.Collection;
import org.drift.post.mapper.CollectionMapper;
import org.drift.post.service.CollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void collect(Long postId, Long authorId) {
        collectionMapper.insert(new Collection()
                .setPostId(postId)
                .setAuthorId(authorId)
                .setUserId(UserContextHolder.getUserContext()));
    }

    @Override
    public void cancel(Long postId, Long authorId) {
        Collection collection = collectionMapper.selectOne(new LambdaQueryWrapper<Collection>()
                .eq(Collection::getPostId, postId)
                .eq(Collection::getAuthorId, authorId)
                .eq(Collection::getUserId, UserContextHolder.getUserContext()));
        collectionMapper.deleteById(collection);
    }

    @Override
    public Long getUserCollectedCount(Long userId) {
        return collectionMapper.selectCount(new LambdaQueryWrapper<Collection>().eq(Collection::getAuthorId, userId));
    }

    @Override
    public List<Long> getUserCollectionPostIds(Long userId, Integer page) {
        return collectionMapper.selectUserCollectionPostIds(userId, page * 20);
    }

    @Override
    public Boolean isCollected(Long postId) {
        return collectionMapper.exists(new LambdaQueryWrapper<Collection>()
                .eq(Collection::getUserId, UserContextHolder.getUserContext())
                .eq(Collection::getPostId, postId));
    }

    @Override
    public Long getPostCollectedCount(Long postId) {
        return collectionMapper.selectCount(new LambdaQueryWrapper<Collection>().eq(Collection::getPostId, postId));
    }
}
