package org.drift.comment.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 15:03
 */
@TableName("tbl_comments")
@Data
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("post_id")
    private Long postId;
    @TableField("user_id")
    private Long userId;
    @TableField("parent_comment_id")
    private Long parentCommentId;
    @TableField("reply_to_user_id")
    private Long replyToUserId;
    @TableField("content")
    private String content;
    @TableField("random_order")
    private String randomOrder;
    @TableField("first_comment")
    private Boolean firstComment;
    @TableField("topped")
    private Boolean topped;
    @TableField("create_time")
    private Instant createTime;
}
