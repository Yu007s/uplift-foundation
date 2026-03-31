package com.uplift.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.common.constant.UserConstant;
import com.uplift.common.exception.BusinessException;
import com.uplift.common.result.ResultCode;
import com.uplift.user.entity.AdminUser;
import com.uplift.user.mapper.AdminUserMapper;
import com.uplift.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 * 后台管理员服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        String appCode = loginDTO.getAppCode();

        AdminUser admin = baseMapper.selectByAppCodeAndUsername(appCode, loginDTO.getAccount());
        if (admin == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        if (admin.getStatus() == UserConstant.USER_STATUS_DISABLED) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_DISABLED);
        }
        if (!passwordEncoder.matches(loginDTO.getCredential(), admin.getPassword())) {
            throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
        }

        List<String> roles = getAdminRoles(admin.getId());
        List<String> permissions = getAdminPermissions(admin.getId(), appCode);

        // Sa-Token loginId 格式：appCode:admin:adminId，与 C端隔离
        String loginId = appCode + ":admin:" + admin.getId();
        StpUtil.login(loginId);
        StpUtil.getTokenSession().set("userId", admin.getId());
        StpUtil.getTokenSession().set("appCode", appCode);
        StpUtil.getTokenSession().set("userType", UserConstant.USER_TYPE_ADMIN);
        StpUtil.getTokenSession().set("username", admin.getUsername());
        StpUtil.getTokenSession().set("roles", new HashSet<>(roles));
        StpUtil.getTokenSession().set("permissions", new HashSet<>(permissions));

        admin.setLastLoginTime(LocalDateTime.now());
        updateById(admin);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(StpUtil.getTokenValue());
        tokenDTO.setTokenType(UserConstant.TOKEN_TYPE_BEARER);
        tokenDTO.setExpiresIn(StpUtil.getTokenTimeout());
        tokenDTO.setUserId(admin.getId());
        tokenDTO.setAppCode(appCode);
        tokenDTO.setUserType(UserConstant.USER_TYPE_ADMIN);
        tokenDTO.setUsername(admin.getUsername());
        tokenDTO.setNickname(admin.getRealName());
        tokenDTO.setAvatar(admin.getAvatar());
        tokenDTO.setRoles(new HashSet<>(roles));
        tokenDTO.setPermissions(new HashSet<>(permissions));

        log.info("管理员登录成功 - appCode: {}, adminId: {}", appCode, admin.getId());
        return tokenDTO;
    }

    @Override
    public void logout(Long adminId, String appCode) {
        String loginId = appCode + ":admin:" + adminId;
        StpUtil.logout(loginId);
        log.info("管理员登出 - appCode: {}, adminId: {}", appCode, adminId);
    }

    @Override
    public AdminUser getByAppCodeAndUsername(String appCode, String username) {
        return baseMapper.selectByAppCodeAndUsername(appCode, username);
    }

    @Override
    public List<String> getAdminRoles(Long adminId) {
        return baseMapper.selectAdminRoles(adminId);
    }

    @Override
    public List<String> getAdminPermissions(Long adminId, String appCode) {
        return baseMapper.selectAdminPermissions(adminId, appCode);
    }
}
