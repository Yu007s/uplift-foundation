package com.uplift.api.user;

import com.uplift.api.user.dto.UserDTO;
import com.uplift.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户服务 Feign 接口
 */
@FeignClient(name = "uplift-user", path = "/api/user")
public interface UserApi {

    /**
     * 根据ID获取用户信息
     */
    @GetMapping("/info/{userId}")
    Result<UserDTO> getUserById(@PathVariable("userId") Long userId);

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/info/username/{username}")
    Result<UserDTO> getUserByUsername(@PathVariable("username") String username);

    /**
     * 根据手机号获取用户信息
     */
    @GetMapping("/info/phone/{phone}")
    Result<UserDTO> getUserByPhone(@PathVariable("phone") String phone);

    /**
     * 批量获取用户信息
     */
    @PostMapping("/info/batch")
    Result<List<UserDTO>> getUserBatch(@RequestBody List<Long> userIds);

    /**
     * 验证用户密码
     */
    @PostMapping("/validate/password")
    Result<Boolean> validatePassword(@RequestParam("userId") Long userId, 
                                      @RequestParam("password") String password);

    /**
     * 检查用户是否存在
     */
    @GetMapping("/exists/{userId}")
    Result<Boolean> checkUserExists(@PathVariable("userId") Long userId);

    /**
     * 获取用户权限列表
     */
    @GetMapping("/permissions/{userId}")
    Result<List<String>> getUserPermissions(@PathVariable("userId") Long userId);

    /**
     * 获取用户角色列表
     */
    @GetMapping("/roles/{userId}")
    Result<List<String>> getUserRoles(@PathVariable("userId") Long userId);
}
