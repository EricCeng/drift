package org.drift.common.pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.drift.common.pojo.user.AuthorInfoDto;

/**
 * @author jiakui_zeng
 * @date 2025/2/14 16:17
 */
@Data
@Accessors(chain = true)
public class CommentResponse {
    @JsonProperty("comment_id")
    private Long commentId;
    private String content;
    @JsonProperty("release_time")
    private String releaseTime;
    @JsonProperty("comment_liked_count")
    private Long commentLikedCount;
    @JsonProperty("author_info")
    private AuthorInfoDto authorInfo;

    @JsonProperty("reply_to_user_id")
    private Long replyToUserId;
    @JsonProperty("reply_to_user_name")
    private Long replyToUserName;
}
