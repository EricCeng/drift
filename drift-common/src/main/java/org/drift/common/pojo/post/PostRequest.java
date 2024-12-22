package org.drift.common.pojo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 00:54
 */
@Data
public class PostRequest {
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("author_id")
    private Long authorId;
    @JsonProperty("user_id")
    private Long userId;
}
