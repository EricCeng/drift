package org.drift.common.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jiakui_zeng
 * @date 2025/2/12 18:03
 */
public class AuthorDto {
    @JsonProperty("author_id")
    private Long authorId;
    private String author;
    @JsonProperty("author_avatar_url")
    private String authorAvatarUrl;
    private Boolean liked;
    private Boolean collected;
}
