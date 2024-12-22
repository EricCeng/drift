package org.drift.common.pojo.user;

import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 23:13
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
