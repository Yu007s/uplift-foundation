package com.uplift.common.constant;

/**
 * 通用常量
 */
public interface CommonConstant {

    // ==================== 系统常量 ====================
    String UTF8 = "UTF-8";
    String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    String TRACE_ID = "traceId";
    String REQUEST_ID = "X-Request-Id";

    // ==================== 请求头常量 ====================
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_TENANT_ID = "X-Tenant-Id";
    String HEADER_APP_ID = "X-App-Id";
    String HEADER_APP_KEY = "X-App-Key";
    String HEADER_USER_ID = "X-User-Id";
    String HEADER_USERNAME = "X-Username";

    // ==================== 缓存前缀 ====================
    String CACHE_PREFIX = "uplift:";
    String CACHE_USER_PREFIX = CACHE_PREFIX + "user:";
    String CACHE_TOKEN_PREFIX = CACHE_PREFIX + "token:";
    String CACHE_CAPTCHA_PREFIX = CACHE_PREFIX + "captcha:";
    String CACHE_RATE_LIMIT_PREFIX = CACHE_PREFIX + "rate_limit:";
    String CACHE_LOCK_PREFIX = CACHE_PREFIX + "lock:";

    // ==================== 限流相关 ====================
    int DEFAULT_RATE_LIMIT = 100;
    int DEFAULT_RATE_LIMIT_WINDOW = 60;

    // ==================== 分页默认值 ====================
    int DEFAULT_PAGE_NUM = 1;
    int DEFAULT_PAGE_SIZE = 10;
    int MAX_PAGE_SIZE = 1000;

    // ==================== 时间格式 ====================
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String TIME_FORMAT = "HH:mm:ss";
}
