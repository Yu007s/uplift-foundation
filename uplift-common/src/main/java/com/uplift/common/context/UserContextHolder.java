package com.uplift.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户上下文持有者
 * 使用 TransmittableThreadLocal 解决线程池上下文传递问题
 */
public class UserContextHolder {

    private static final TransmittableThreadLocal<UserContext> CONTEXT = new TransmittableThreadLocal<>();

    /**
     * 设置用户上下文
     */
    public static void setContext(UserContext context) {
        CONTEXT.set(context);
    }

    /**
     * 获取用户上下文
     */
    public static UserContext getContext() {
        return CONTEXT.get();
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        UserContext context = getContext();
        return context != null ? context.getUserId() : null;
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        UserContext context = getContext();
        return context != null ? context.getUsername() : null;
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        UserContext context = getContext();
        return context != null ? context.getTenantId() : null;
    }

    /**
     * 获取应用ID
     */
    public static Long getAppId() {
        UserContext context = getContext();
        return context != null ? context.getAppId() : null;
    }

    /**
     * 是否是超级管理员
     */
    public static boolean isSuperAdmin() {
        UserContext context = getContext();
        return context != null && context.isSuperAdmin();
    }

    /**
     * 清除上下文
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
