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
 * @date 2024/12/22 00:40
 */
@TableName("tbl_posts")
@Data
@Accessors(chain = true)
public class Post {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("image_url")
    private String imageUrl;
    @TableField("random_order")
    private String randomOrder;
    @TableField("create_time")
    private Instant createTime;
    @TableField("update_time")
    private Instant updateTime;
}
