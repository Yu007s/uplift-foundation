package com.uplift.api.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Token 信息 DTO
 */
@Data
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 访问令牌 */
    private String accessToken;

    /** 令牌类型 */
    private String tokenType;

    /** 过期时间（秒） */
    private Long expiresIn;

    /** 用户ID */
    private Long userId;

    /** 应用编码 */
    private String appCode;

    /** 用户类型 1-C端用户 2-管理员 */
    private Integer userType;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 角色列表 */
    private Set<String> roles;

    /** 权限列表 */
    private Set<String> permissions;
}
