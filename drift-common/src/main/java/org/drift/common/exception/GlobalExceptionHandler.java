package org.drift.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.drift.common.api.CommonResult;
import org.drift.common.api.IErrorCode;
import org.drift.common.api.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 17:03
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> handleException(Exception ex) {
        // 返回的最终对象
        CommonResult<?> commonResult;
        if (ex instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) ex).getErrorCode();
            if (errorCode != null) {
                commonResult = CommonResult.failed(errorCode);
            } else {
                commonResult = CommonResult.failed(ex.getMessage());
            }
        } else {
            commonResult = CommonResult.failed(ResultCode.FAILED);
        }
        log.error("[Global Capture Exception]:", ex);
        return commonResult;
    }
}
