package com.uplift.common.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户上下文信息
 */
@Data
public class UserContext implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 岗位ID列表
     */
    private List<Long> postIds;

    /**
     * 数据权限范围
     */
    private Integer dataScope;

    /**
     * 是否是超级管理员
     */
    public boolean isSuperAdmin() {
        return roles != null && roles.contains("super_admin");
    }

    /**
     * 是否拥有指定角色
     */
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    /**
     * 是否拥有指定权限
     */
    public boolean hasPermission(String permission) {
        return isSuperAdmin() || (permissions != null && permissions.contains(permission));
    }
}
