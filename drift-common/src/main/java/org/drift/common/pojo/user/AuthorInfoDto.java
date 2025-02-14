package org.drift.common.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jiakui_zeng
 * @date 2025/2/12 18:03
 */
@Data
@Accessors(chain = true)
public class AuthorInfoDto {
    @JsonProperty("author_id")
    private Long authorId;
    private String author;
    @JsonProperty("author_avatar_url")
    private String authorAvatarUrl;
    private Boolean liked;
    private Boolean collected;
}
