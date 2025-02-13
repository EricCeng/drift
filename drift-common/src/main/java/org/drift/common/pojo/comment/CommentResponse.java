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
    @JsonProperty("parent_comment_id")
    private Long parentCommentId;
    @JsonProperty("parent_comment_content")
    private String parentCommentContent;
    @JsonProperty("parent_comment_release_time")
    private String parentCommentReleaseTime;
    @JsonProperty("parent_comment_liked_count")
    private Long parentCommentLikedCount;
    @JsonProperty("parent_author_info")
    private AuthorDto parentAuthorInfo;
    @JsonProperty("child_comment_count")
    private Long childCommentCount;
    @JsonProperty("child_comment_id")
    private Long childCommentId;
    @JsonProperty("child_comment_content")
    private String childCommentContent;
    @JsonProperty("child_comment_release_time")
    private String childCommentReleaseTime;
    @JsonProperty("child_author_info")
    private AuthorDto childAuthorInfo;
    @JsonProperty("child_comment_liked_count")
    private Long childCommentLikedCount;
}
