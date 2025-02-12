package org.drift.common.pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.drift.common.pojo.user.AuthorDto;

/**
 * @author jiakui_zeng
 * @date 2025/2/11 17:13
 */
@Data
@Accessors(chain = true)
public class CommentResponse {
    @JsonProperty("parent_comment")
    private CommentDto parentComment;
    @JsonProperty("child_comment")
    private CommentDto childComment;
    @JsonProperty("comment_id")
    private Long commentId;
    private String content;
    @JsonProperty("release_time")
    private String releaseTime;
    @JsonProperty("author_info")
    private AuthorDto authorInfo;
    @JsonProperty("liked_count")
    private Long likedCount;
}
