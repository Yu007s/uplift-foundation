package com.uplift.user.controller;

import com.uplift.api.user.dto.UserDTO;
import com.uplift.common.result.Result;
import com.uplift.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器
 * 实现 UserApi Feign 接口
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
        UserDTO userDTO = userService.getById(userId) != null 
                ? convertToDTO(userService.getById(userId)) : null;
        return Result.success(userDTO);
    }

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/info/username/{username}")
    public Result<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return Result.success(userDTO);
    }

    /**
     * 根据手机号获取用户信息
     */
    @GetMapping("/info/phone/{phone}")
    public Result<UserDTO> getUserByPhone(@PathVariable String phone) {
        UserDTO userDTO = userService.getUserByPhone(phone);
        return Result.success(userDTO);
    }

    /**
     * 批量获取用户信息
     */
    @PostMapping("/info/batch")
    public Result<List<UserDTO>> getUserBatch(@RequestBody List<Long> userIds) {
        // TODO: 实现批量查询
        return Result.success();
    }

    /**
     * 验证用户密码
     */
    @PostMapping("/validate/password")
    public Result<Boolean> validatePassword(@RequestParam Long userId, 
                                             @RequestParam String password) {
        boolean valid = userService.validatePassword(userId, password);
        return Result.success(valid);
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
     * 获取用户权限列表
     */
    @GetMapping("/permissions/{userId}")
    public Result<List<String>> getUserPermissions(@PathVariable Long userId) {
        List<String> permissions = userService.getUserPermissions(userId);
        return Result.success(permissions);
    }

    /**
     * 获取用户角色列表
     */
    @GetMapping("/roles/{userId}")
    public Result<List<String>> getUserRoles(@PathVariable Long userId) {
        List<String> roles = userService.getUserRoles(userId);
        return Result.success(roles);
    }

    private UserDTO convertToDTO(com.uplift.user.entity.User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setTenantId(user.getTenantId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setRealName(user.getRealName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setGender(user.getGender());
        dto.setStatus(user.getStatus());
        dto.setDeptId(user.getDeptId());
        dto.setLastLoginIp(user.getLastLoginIp());
        dto.setLastLoginTime(user.getLastLoginTime());
        dto.setCreateTime(user.getCreateTime());
        return dto;
    }
}
