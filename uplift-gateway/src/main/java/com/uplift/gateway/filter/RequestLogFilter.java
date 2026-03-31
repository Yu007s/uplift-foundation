package com.uplift.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 请求日志过滤器
 * 记录请求信息并生成追踪ID
 */
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    private static final String TRACE_ID = "traceId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String traceId = UUID.randomUUID().toString().replace("-", "");
        
        // 将 traceId 添加到请求头
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(TRACE_ID, traceId)
                .build();
        
        long startTime = System.currentTimeMillis();
        
        log.info("[{}] {} {} - 开始处理", 
                traceId,
                request.getMethod(),
                request.getURI().getPath());
        
        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .doFinally(signalType -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("[{}] {} {} - 处理完成，耗时: {}ms，状态码: {}",
                            traceId,
                            request.getMethod(),
                            request.getURI().getPath(),
                            duration,
                            exchange.getResponse().getStatusCode());
                });
    }

    @Override
    public int getOrder() {
        // 最高优先级
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
