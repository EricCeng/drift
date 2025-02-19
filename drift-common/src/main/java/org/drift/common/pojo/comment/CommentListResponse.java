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
    @JsonProperty("comment")
    private CommentResponse comment;
    @JsonProperty("earliest_reply")
    private CommentResponse earliestReply;
    @JsonProperty("reply_count")
    private Long replyCount;
    private Boolean first;
}
