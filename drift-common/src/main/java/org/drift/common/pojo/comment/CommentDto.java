package org.drift.common.pojo.comment;

import lombok.Data;
import lombok.experimental.Accessors;

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
    private String createTime;
    private Long childCommentCount;
    private Long childCommentId;
    private Long childCommentUserId;
    private String childCommentContent;
    private Long childCommentCreateTime;
}
