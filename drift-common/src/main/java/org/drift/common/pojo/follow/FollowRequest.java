package org.drift.common.pojo.follow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:13
 */
@Data
public class FollowRequest {
    @JsonProperty("follower_id")
    private Long followerId;
    @JsonProperty("followed_id")
    private Long followedId;
}
