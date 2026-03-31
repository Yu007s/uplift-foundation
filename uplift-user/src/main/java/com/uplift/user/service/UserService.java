package com.uplift.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.api.user.dto.UserDTO;
import com.uplift.user.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    TokenDTO login(LoginDTO loginDTO);

    /**
     * 用户登出
     */
    void logout(Long userId);

    /**
     * 根据用户名获取用户
     */
    UserDTO getUserByUsername(String username);

    /**
     * 根据手机号获取用户
     */
    UserDTO getUserByPhone(String phone);

    /**
     * 获取用户角色列表
     */
    List<String> getUserRoles(Long userId);

    /**
     * 获取用户权限列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 验证密码
     */
    boolean validatePassword(Long userId, String password);

    /**
     * 刷新 Token
     */
    TokenDTO refreshToken(String refreshToken);
}
