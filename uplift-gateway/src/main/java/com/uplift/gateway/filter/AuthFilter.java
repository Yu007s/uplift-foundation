package com.uplift.gateway.filter;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * Sa-Token 认证过滤器配置
 */
@Slf4j
@Configuration
public class AuthFilter {

    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaReactorFilter saReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico", "/actuator/**", "/health")
                // 前置函数：在每次认证前执行，OPTIONS 预检请求直接放行
                .setBeforeAuth(obj -> {
                    ServerHttpRequest request = (ServerHttpRequest) obj;
                    if (HttpMethod.OPTIONS.equals(request.getMethod())) {
                        // OPTIONS 预检请求直接放行，不做认证
                        SaRouter.stop();
                    }
                })
                // 认证函数
                .setAuth(obj -> {
                    SaRouter.match("/**")
                            // 登录接口放行
                            .notMatch("/api/user/auth/login", "/api/user/auth/register", "/api/user/auth/captcha", "/api/user/auth/check")
                            // 健康检查放行
                            .notMatch("/health", "/actuator/**")
                            // GoStep 健康检查放行
                            .notMatch("/api/gostep/health")
                            // 检查登录
                            .check(() -> StpUtil.checkLogin());
                })
                // 异常处理函数
                .setError(e -> {
                    log.error("认证失败: {}", e.getMessage());
                    return SaResult.error(e.getMessage());
                });
    }
}
