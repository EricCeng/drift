package org.drift.common.pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jiakui_zeng
 * @date 2025/2/11 17:13
 */
@Data
@Accessors(chain = true)
public class CommentListResponse {
    @JsonProperty("parent_comment")
    private CommentResponse parentComment;
    @JsonProperty("child_comment")
    private CommentResponse childComment;
    @JsonProperty("child_comment_count")
    private Long childCommentCount;
}
