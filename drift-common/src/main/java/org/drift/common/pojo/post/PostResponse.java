package org.drift.common.pojo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.drift.common.pojo.user.AuthorDto;

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
    @JsonProperty("release_time")
    private String releaseTime;
    @JsonProperty("author_info")
    private AuthorDto authorInfo;
    @JsonProperty("liked_count")
    private Long likedCount;
}
