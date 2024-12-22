package org.drift.user.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:19
 */
@TableName("tbl_users")
@Data
@Accessors(chain = true)
public class User {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("avatar_url")
    private String avatarUrl;
    @TableField("bio")
    private String bio;
    @TableField("birthday")
    private String birthday;
    @TableField("gender")
    private String gender;
    @TableField("region")
    private String region;
    @TableField("occupation")
    private String occupation;
    @TableField("school")
    private String school;
    @TableField("enrollment_date")
    private String enrollmentDate;
    @TableField("create_time")
    private Instant createTime;
    @TableField("update_time")
    private Instant updateTime;
}
