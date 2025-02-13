package org.drift.common.pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author jiakui_zeng
 * @date 2025/2/13 17:23
 */
@Data
public class CommentRequest {
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("parent_comment_id")
    private Long parentCommentId;
    private String content;
    @JsonProperty("reply_to_user_id")
    private Long replyToUserId;
}
