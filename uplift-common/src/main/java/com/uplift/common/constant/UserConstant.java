package com.uplift.common.constant;

/**
 * 用户相关常量
 */
public interface UserConstant {

    // ==================== 用户状态 ====================
    int USER_STATUS_ENABLED = 1;
    int USER_STATUS_DISABLED = 0;
    int USER_STATUS_LOCKED = 2;

    // ==================== 性别 ====================
    int GENDER_UNKNOWN = 0;
    int GENDER_MALE = 1;
    int GENDER_FEMALE = 2;

    // ==================== 登录类型 ====================
    String LOGIN_TYPE_PASSWORD = "password";
    String LOGIN_TYPE_SMS = "sms";
    String LOGIN_TYPE_EMAIL = "email";
    String LOGIN_TYPE_WECHAT = "wechat";
    String LOGIN_TYPE_QQ = "qq";

    // ==================== Token 类型 ====================
    String TOKEN_TYPE_BEARER = "Bearer";
    String TOKEN_TYPE_BASIC = "Basic";

    // ==================== 默认角色 ====================
    String ROLE_SUPER_ADMIN = "super_admin";
    String ROLE_ADMIN = "admin";
    String ROLE_USER = "user";

    // ==================== 默认密码 ====================
    String DEFAULT_PASSWORD = "123456";

    // ==================== 密码相关 ====================
    int PASSWORD_MIN_LENGTH = 6;
    int PASSWORD_MAX_LENGTH = 32;
    int PASSWORD_ERROR_MAX_TIMES = 5;
    int PASSWORD_LOCK_MINUTES = 30;
}
