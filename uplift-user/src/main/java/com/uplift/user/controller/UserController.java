package com.uplift.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.uplift.api.user.dto.UserDTO;
import com.uplift.common.result.Result;
import com.uplift.user.entity.User;
import com.uplift.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端用户控制器
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据ID获取用户信息
     */
    @GetMapping("/info/{userId}")
    public Result<UserDTO> getUserById(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return Result.success(convertToDTO(user));
    }

    /**
     * 根据用户名获取用户信息（需传入appCode）
     */
    @GetMapping("/info/username/{username}")
    public Result<UserDTO> getUserByUsername(@PathVariable String username,
                                              @RequestParam String appCode) {
        User user = userService.getByAppCodeAndUsername(appCode, username);
        return Result.success(convertToDTO(user));
    }

    /**
     * 根据手机号获取用户信息（需传入appCode）
     */
    @GetMapping("/info/phone/{phone}")
    public Result<UserDTO> getUserByPhone(@PathVariable String phone,
                                           @RequestParam String appCode) {
        User user = userService.getByAppCodeAndPhone(appCode, phone);
        return Result.success(convertToDTO(user));
    }

    /**
     * 检查用户是否存在
     */
    @GetMapping("/exists/{userId}")
    public Result<Boolean> checkUserExists(@PathVariable Long userId) {
        boolean exists = userService.getById(userId) != null;
        return Result.success(exists);
    }

    /**
     * 获取当前登录用户权限列表
     */
    @GetMapping("/permissions")
    public Result<List<String>> getUserPermissions() {
        String appCode = (String) StpUtil.getTokenSession().get("appCode");
        Long userId = (Long) StpUtil.getTokenSession().get("userId");
        List<String> permissions = userService.getUserPermissions(userId, appCode);
        return Result.success(permissions);
    }

    /**
     * 获取当前登录用户角色列表
     */
    @GetMapping("/roles")
    public Result<List<String>> getUserRoles() {
        Long userId = (Long) StpUtil.getTokenSession().get("userId");
        List<String> roles = userService.getUserRoles(userId);
        return Result.success(roles);
    }

    private UserDTO convertToDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setStatus(user.getStatus());
        dto.setLastLoginIp(user.getLastLoginIp());
        dto.setLastLoginTime(user.getLastLoginTime());
        dto.setCreateTime(user.getCreateTime());
        return dto;
    }
}
