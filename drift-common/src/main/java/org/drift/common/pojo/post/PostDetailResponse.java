package org.drift.common.pojo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.drift.common.pojo.user.AuthorInfoDto;

import java.util.List;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 14:30
 */
@Data
@Accessors(chain = true)
public class PostDetailResponse {
    @JsonProperty("author_info")
    private AuthorInfoDto authorInfo;
    @JsonProperty("post_id")
    private Long postId;
    private String title;
    private String content;
    @JsonProperty("image_url_list")
    private List<String> imageUrlList;
    @JsonProperty("release_time")
    private String releaseTime;
    private Boolean edited;
    private Boolean collected;
    @JsonProperty("liked_count")
    private Long likedCount;
    @JsonProperty("collected_count")
    private Long collectedCount;
    @JsonProperty("comment_count")
    private Long commentCount;
}
