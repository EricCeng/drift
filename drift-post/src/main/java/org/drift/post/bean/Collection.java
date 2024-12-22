package org.drift.post.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:43
 */
@TableName("tbl_collections")
@Data
@Accessors(chain = true)
public class Collection {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("post_id")
    private Long postId;
    @TableField("author_id")
    private Long authorId;
    @TableField("user_id")
    private Long userId;
    @TableField("create_time")
    private Instant createTime;
}
