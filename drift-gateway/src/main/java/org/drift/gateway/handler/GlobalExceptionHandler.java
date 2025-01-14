package org.drift.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.drift.common.api.CommonResult;
import org.drift.common.api.IErrorCode;
import org.drift.common.api.ResultCode;
import org.drift.common.exception.ApiException;
import org.drift.gateway.util.WebFrameworkUtil;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 17:03
 */
@Component
@Slf4j
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 已经 commit，则直接返回异常
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 转换成 CommonResult
        CommonResult<?> result;
        if (ex instanceof ResponseStatusException) {
            result = responseStatusExceptionHandler(exchange, (ResponseStatusException) ex);
        } else {
            result = defaultExceptionHandler(exchange, ex);
        }
        return WebFrameworkUtil.writeJson(exchange, result);
    }

    /**
     * 处理 Spring Cloud Gateway 默认抛出的 ResponseStatusException 异常
     */
    private CommonResult<?> responseStatusExceptionHandler(ServerWebExchange exchange,
                                                           ResponseStatusException ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[responseStatusExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return CommonResult.error(ex.getStatusCode().value(), ex.getReason());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(ServerWebExchange exchange,
                                                   Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[defaultExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
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
        return commonResult;
    }
}
