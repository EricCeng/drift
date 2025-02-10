package org.drift.common.api;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:59
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, "成功"),
    UNAUTHORIZED(401, "账号未登录"),
    INVALID_TOKEN(402, "访问令牌已失效，请重新登录"),
    ERROR(500, "内部服务器错误"),
    ;

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
