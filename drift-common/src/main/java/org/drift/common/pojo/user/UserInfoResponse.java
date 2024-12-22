package org.drift.common.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 23:43
 */
@Data
@Accessors(chain = true)
public class UserInfoResponse {
    @JsonProperty("user_id")
    private Long userId;
    private String username;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String bio;
    private Integer age;
    private String gender;
    private String region;
    private String occupation;
    private String school;
    @JsonProperty("following_count")
    private Long followingCount;
    @JsonProperty("follower_count")
    private Long followerCount;
    @JsonProperty("liked_count")
    private Long likedCount;
    @JsonProperty("collected_count")
    private Long collectedCount;
}
