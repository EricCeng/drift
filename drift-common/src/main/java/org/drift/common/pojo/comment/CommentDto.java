package org.drift.common.pojo.comment;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @author jiakui_zeng
 * @date 2025/2/11 17:32
 */
@Data
@Accessors(chain = true)
public class CommentDto {
    private Long commentId;
    private Long userId;
    private String content;
    private Instant createTime;
    private Boolean firstComment;
    private Boolean topped;
    private Long parentCommentId;
    private Long childCommentCount;
}
