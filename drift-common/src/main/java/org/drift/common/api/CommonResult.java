package org.drift.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 16:56
 */
@Data
public class CommonResult<T> {
    /**
     * 状态码
     */
    @JsonProperty("error_code")
    private Integer code;
    /**
     * 返回信息
     */
    @JsonProperty("error_msg")
    private String message;

    @JsonProperty("data")
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> error(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResult<T> error(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null);
    }

    public static <T> CommonResult<T> error(int errorCode, String message) {
        return new CommonResult<>(errorCode, message, null);
    }

    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(ResultCode.ERROR.getCode(), message, null);
    }

    public static <T> CommonResult<T> error() {
        return error(ResultCode.ERROR);
    }
}
