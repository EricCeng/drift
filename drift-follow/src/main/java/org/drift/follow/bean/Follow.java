package org.drift.follow.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:08
 */
@TableName("tbl_follows")
@Data
@Accessors(chain = true)
public class Follow {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("follower_id")
    private Long followerId;
    @TableField("followed_id")
    private Long followedId;
    @TableField("create_time")
    private Instant createTime;
}
