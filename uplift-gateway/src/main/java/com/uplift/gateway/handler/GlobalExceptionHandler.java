package com.uplift.gateway.handler;

import com.uplift.common.result.Result;
import com.uplift.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

/**
 * 全局异常处理器 (WebFlux)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, ServerWebExchange exchange) {
        String uri = exchange.getRequest().getURI().getPath();
        log.error("系统异常 - URI: {}", uri, e);
        return Result.error(ResultCode.ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e, ServerWebExchange exchange) {
        String uri = exchange.getRequest().getURI().getPath();
        log.warn("非法参数 - URI: {}, Message: {}", uri, e.getMessage());
        return Result.error(ResultCode.PARAM_IS_INVALID.getCode(), e.getMessage());
    }
}
