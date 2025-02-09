package org.drift.common.util;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 17:16
 */
public class BatchMapperUtils {
    private static final int BATCH_SIZE = 2000;

    /**
     * @param entityList      要插入数据的List集合
     * @param baseMapperClass 必须是BaseMapper的子类
     */

    public static void insertBatch(Collection<?> entityList, Class<? extends BaseMapper> baseMapperClass) {
        insertBatch(entityList, baseMapperClass, BATCH_SIZE);
    }

    /**
     * @param entityList      要插入数据的List集合
     * @param baseMapperClass 必须是BaseMapper的子类
     * @param batchSize       数据量
     */
    public static void insertBatch(Collection<?> entityList, Class<? extends BaseMapper> baseMapperClass, int batchSize) {
        if (ObjectUtils.isEmpty(entityList)) {
            return;
        }
        String sqlStatement = baseMapperClass.getName() + StringPool.DOT + SqlMethod.INSERT_ONE.getMethod();
        Class<?> entityClass = ReflectionKit.getSuperClassGenericType(baseMapperClass, BaseMapper.class, 0);
        SqlHelper.executeBatch(entityClass,
                new NoLoggingImpl(null),
                entityList,
                batchSize,
                (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    /**
     * 根据 主键 ID 批量更新记录
     *
     * @param entityList      要更新数据的List集合
     * @param baseMapperClass 必须是BaseMapper的子类
     */
    public static <T> void updateBatch(List<T> entityList, Class<? extends BaseMapper<T>> baseMapperClass) {
        if (ObjectUtils.isEmpty(entityList)) {
            return;
        }
        String sqlStatement = baseMapperClass.getName() + StringPool.DOT + SqlMethod.UPDATE_BY_ID.getMethod();
        Class<?> entityClass = ReflectionKit.getSuperClassGenericType(baseMapperClass, BaseMapper.class, 0);
        SqlHelper.executeBatch(entityClass,
                new NoLoggingImpl(null),
                entityList,
                BATCH_SIZE,
                (sqlSession, entity) -> {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, entity);
                    sqlSession.update(sqlStatement, param);
                });
    }
}
