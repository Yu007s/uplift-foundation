package com.uplift.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.RegisterDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.user.entity.User;

import java.util.List;

/**
 * C端用户服务
 */
public interface UserService extends IService<User> {

    /** C端用户登录 */
    TokenDTO login(LoginDTO loginDTO);

    /** C端用户注册 */
    TokenDTO register(RegisterDTO registerDTO);

    /** 登出 */
    void logout(Long userId, String appCode);

    /** 根据 appCode + username 查用户 */
    User getByAppCodeAndUsername(String appCode, String username);

    /** 根据 appCode + phone 查用户 */
    User getByAppCodeAndPhone(String appCode, String phone);

    /** 获取用户角色列表 */
    List<String> getUserRoles(Long userId);

    /** 获取用户权限列表 */
    List<String> getUserPermissions(Long userId, String appCode);
}
