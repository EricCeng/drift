package org.drift.common.api;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:59
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, "成功"),
    FAILED(500, "内部服务器错误"),
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
