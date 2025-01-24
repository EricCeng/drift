package org.drift.common.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 23:13
 */
@Data
public class AuthRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String password;
    @JsonProperty("re_password")
    private String rePassword;
}
