package com.uplift.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 用户上下文过滤器
 * 将登录用户信息传递给下游服务
 */
@Slf4j
@Component
public class UserContextFilter implements GlobalFilter, Ordered {

    private static final String HEADER_USER_ID = "X-User-Id";
    private static final String HEADER_USERNAME = "X-Username";
    private static final String HEADER_TENANT_ID = "X-Tenant-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder requestBuilder = request.mutate();

        try {
            // 如果用户已登录，将用户信息添加到请求头
            if (StpUtil.isLogin()) {
                Long userId = StpUtil.getLoginIdAsLong();
                Object loginExtra = StpUtil.getExtra("username");
                String username = loginExtra != null ? loginExtra.toString() : "";
                
                requestBuilder.header(HEADER_USER_ID, String.valueOf(userId));
                requestBuilder.header(HEADER_USERNAME, username);
                
                // 获取租户信息
                Object tenantId = StpUtil.getExtra("tenantId");
                if (tenantId != null) {
                    requestBuilder.header(HEADER_TENANT_ID, tenantId.toString());
                }
                
                log.debug("传递用户上下文 - userId: {}, username: {}", userId, username);
            }
        } catch (Exception e) {
            // 未登录或获取信息失败，不添加用户头
            log.debug("用户未登录或获取上下文失败");
        }

        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
    }

    @Override
    public int getOrder() {
        // 在认证过滤器之后执行
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}
