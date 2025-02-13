package org.drift.comment.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

/**
 * @author jiakui_zeng
 * @date 2025/2/13 16:37
 */
@TableName("tbl_comment_likes")
@Data
public class CommentLike {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("comment_id")
    private Long commentId;
    @TableField("user_id")
    private Long userId;
    @TableField("create_time")
    private Instant createTime;
}
