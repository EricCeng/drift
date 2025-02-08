package org.drift.common.pojo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 12:44
 */
@Data
@Accessors(chain = true)
public class PostResponse {
    @JsonProperty("post_id")
    private Long postId;
    private String title;
    @JsonProperty("first_image_url")
    private String firstImageUrl;
    @JsonProperty("author_id")
    private Long authorId;
    private String author;
    @JsonProperty("author_avatar_url")
    private String authorAvatarUrl;
    private Boolean liked;
    @JsonProperty("liked_count")
    private Long likedCount;
}
