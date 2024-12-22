package org.drift.common.pojo.like;

import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/22 13:20
 */
@Data
public class PostLikedCountDto {
    private Long postId;
    private Long likedCount;
}
