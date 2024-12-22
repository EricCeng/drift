package org.drift.common.pojo.follow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:24
 */
@Data
@Accessors(chain = true)
public class FollowResponse {
    @JsonProperty("following_count")
    private Long followingCount;
    @JsonProperty("follower_count")
    private Long followerCount;
}
