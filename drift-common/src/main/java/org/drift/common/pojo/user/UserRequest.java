package org.drift.common.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 13:10
 */
@Data
@Accessors(chain = true)
public class UserRequest {
    @JsonProperty("user_ids")
    private Set<Long> userIds;
}
