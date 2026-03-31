package com.uplift.common.result;

import lombok.Getter;

/**
 * 响应状态码枚举
 */
@Getter
public enum ResultCode {

    // ==================== 成功状态 ====================
    SUCCESS(200, "操作成功"),

    // ==================== 客户端错误 (4xx) ====================
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "拒绝访问，权限不足"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后再试"),

    // ==================== 服务端错误 (5xx) ====================
    ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),

    // ==================== 业务错误 (1000-1999) ====================
    // 用户相关 (1000-1099)
    USER_NOT_EXIST(1000, "用户不存在"),
    USER_ALREADY_EXIST(1001, "用户已存在"),
    USER_PASSWORD_ERROR(1002, "密码错误"),
    USER_ACCOUNT_DISABLED(1003, "账号已被禁用"),
    USER_ACCOUNT_LOCKED(1004, "账号已被锁定"),
    USER_TOKEN_EXPIRED(1005, "登录已过期，请重新登录"),
    USER_TOKEN_INVALID(1006, "登录凭证无效"),
    USER_NO_PERMISSION(1007, "没有操作权限"),
    USER_OLD_PASSWORD_ERROR(1008, "原密码错误"),
    USER_EMAIL_EXIST(1009, "邮箱已被注册"),
    USER_PHONE_EXIST(1010, "手机号已被注册"),

    // 租户相关 (1100-1199)
    TENANT_NOT_EXIST(1100, "租户不存在"),
    TENANT_ALREADY_EXIST(1101, "租户已存在"),
    TENANT_DISABLED(1102, "租户已被禁用"),
    TENANT_EXPIRED(1103, "租户已过期"),

    // 应用相关 (1200-1299)
    APP_NOT_EXIST(1200, "应用不存在"),
    APP_ALREADY_EXIST(1201, "应用已存在"),
    APP_DISABLED(1202, "应用已被禁用"),
    APP_SECRET_ERROR(1203, "应用密钥错误"),

    // 参数校验 (1300-1399)
    PARAM_IS_INVALID(1300, "参数无效"),
    PARAM_IS_BLANK(1301, "参数为空"),
    PARAM_TYPE_ERROR(1302, "参数类型错误"),
    PARAM_NOT_COMPLETE(1303, "参数缺失"),

    // 文件相关 (1400-1499)
    FILE_UPLOAD_ERROR(1400, "文件上传失败"),
    FILE_DOWNLOAD_ERROR(1401, "文件下载失败"),
    FILE_NOT_EXIST(1402, "文件不存在"),
    FILE_TYPE_NOT_SUPPORT(1403, "不支持的文件类型"),
    FILE_SIZE_EXCEED(1404, "文件大小超出限制"),

    // 限流降级 (1500-1599)
    RATE_LIMIT(1500, "请求过于频繁，请稍后再试"),
    CIRCUIT_BREAKER(1501, "服务熔断，请稍后再试"),
    DEGRADE(1502, "服务降级，请稍后再试"),

    // ==================== 第三方服务错误 (2000-2999) ====================
    THIRD_PARTY_SERVICE_ERROR(2000, "第三方服务异常"),
    SMS_SEND_ERROR(2001, "短信发送失败"),
    EMAIL_SEND_ERROR(2002, "邮件发送失败"),
    OSS_UPLOAD_ERROR(2003, "对象存储服务异常");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
