package com.uplift.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.RegisterDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.common.result.Result;
import com.uplift.user.service.AdminUserService;
import com.uplift.user.service.AppService;
import com.uplift.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统一认证控制器
 * 通过 appCode 自动识别 C端还是后台登录
 */
@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AdminUserService adminUserService;
    private final AppService appService;

    /**
     * 统一登录入口
     * 客户端传入 appCode 区分系统：
     *   C端：  jb-c / gostep-c
     *   后台：  jb-admin / gostep-admin
     */
    @PostMapping("/login")
    public Result<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        // 校验 app 是否合法
        appService.validateApp(loginDTO.getAppCode());

        // 根据 appCode 判断路由到哪个用户体系
        int appType = appService.getAppType(loginDTO.getAppCode());
        TokenDTO tokenDTO;
        if (appType == 1) {
            // C端用户登录
            tokenDTO = userService.login(loginDTO);
        } else {
            // 后台管理员登录
            tokenDTO = adminUserService.login(loginDTO);
        }
        return Result.success(tokenDTO);
    }

    /**
     * C端用户注册
     */
    @PostMapping("/register")
    public Result<TokenDTO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        TokenDTO tokenDTO = userService.register(registerDTO);
        return Result.success(tokenDTO);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.success();
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<Object> getLoginInfo() {
        StpUtil.checkLogin();
        return Result.success(StpUtil.getTokenSession().get("userId"));
    }

    /**
     * 检查是否登录
     */
    @GetMapping("/check")
    public Result<Boolean> checkLogin() {
        return Result.success(StpUtil.isLogin());
    }
}
