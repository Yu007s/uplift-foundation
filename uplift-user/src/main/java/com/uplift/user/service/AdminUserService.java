package com.uplift.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.user.entity.AdminUser;

import java.util.List;

/**
 * 后台管理员服务
 */
public interface AdminUserService extends IService<AdminUser> {

    /** 管理员登录 */
    TokenDTO login(LoginDTO loginDTO);

    /** 登出 */
    void logout(Long adminId, String appCode);

    /** 根据 appCode + username 查管理员 */
    AdminUser getByAppCodeAndUsername(String appCode, String username);

    /** 获取管理员角色列表 */
    List<String> getAdminRoles(Long adminId);

    /** 获取管理员权限列表 */
    List<String> getAdminPermissions(Long adminId, String appCode);
}
