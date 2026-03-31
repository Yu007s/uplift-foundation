package com.uplift.common.constant;

/**
 * 用户相关常量
 */
public interface UserConstant {

    // ==================== 用户状态 ====================
    int USER_STATUS_ENABLED = 1;
    int USER_STATUS_DISABLED = 0;

    // ==================== 用户类型 ====================
    int USER_TYPE_C = 1;        // C端用户
    int USER_TYPE_ADMIN = 2;    // 后台管理员

    // ==================== 应用类型 ====================
    int APP_TYPE_C = 1;         // C端应用
    int APP_TYPE_ADMIN = 2;     // 后台管理系统

    // ==================== 登录类型 ====================
    String LOGIN_TYPE_PASSWORD = "password";
    String LOGIN_TYPE_SMS = "sms";
    String LOGIN_TYPE_EMAIL = "email";
    String LOGIN_TYPE_WECHAT = "wechat";

    // ==================== Token 类型 ====================
    String TOKEN_TYPE_BEARER = "Bearer";

    // ==================== 默认角色 ====================
    String ROLE_SUPER_ADMIN = "super_admin";
    String ROLE_ADMIN = "admin";
    String ROLE_USER = "user";

    // ==================== 密码相关 ====================
    int PASSWORD_MIN_LENGTH = 6;
    int PASSWORD_MAX_LENGTH = 32;
}
