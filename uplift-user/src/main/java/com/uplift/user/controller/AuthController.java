package com.uplift.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.common.result.Result;
import com.uplift.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = userService.login(loginDTO);
        return Result.success(tokenDTO);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        Long userId = StpUtil.getLoginIdAsLong();
        userService.logout(userId);
        return Result.success();
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getLoginInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("userId", StpUtil.getLoginIdAsLong());
        info.put("token", StpUtil.getTokenValue());
        info.put("tokenTimeout", StpUtil.getTokenTimeout());
        info.put("sessionTimeout", StpUtil.getSessionTimeout());
        return Result.success(info);
    }

    /**
     * 检查是否登录
     */
    @GetMapping("/check")
    public Result<Boolean> checkLogin() {
        return Result.success(StpUtil.isLogin());
    }
}
