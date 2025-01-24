package org.drift.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.drift.common.api.CommonResult;
import org.drift.common.api.IErrorCode;
import org.drift.common.api.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jiakui_zeng
 * @date 2025/1/21 15:08
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> handleException(Exception ex) {
        // 返回的最终对象
        CommonResult<?> commonResult;
        if (ex instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) ex).getErrorCode();
            if (errorCode != null) {
                commonResult = CommonResult.error(errorCode);
            } else {
                commonResult = CommonResult.error(ex.getMessage());
            }
        } else {
            commonResult = CommonResult.error(ResultCode.ERROR);
        }
        log.error("[Global Capture Exception]:", ex);
        return commonResult;
    }
}
