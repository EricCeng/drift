package org.drift.common.exception;

import lombok.Getter;
import org.drift.common.api.IErrorCode;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 17:04
 */
@Getter
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
