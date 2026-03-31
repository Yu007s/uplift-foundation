package com.uplift.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.api.user.dto.UserDTO;
import com.uplift.common.constant.UserConstant;
import com.uplift.common.exception.BusinessException;
import com.uplift.common.result.ResultCode;
import com.uplift.user.entity.User;
import com.uplift.user.mapper.UserMapper;
import com.uplift.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        // 根据登录类型获取用户
        User user = null;
        switch (loginDTO.getLoginType()) {
            case UserConstant.LOGIN_TYPE_PASSWORD:
                user = baseMapper.selectByUsername(loginDTO.getAccount());
                if (user == null) {
                    user = baseMapper.selectByPhone(loginDTO.getAccount());
                }
                break;
            case UserConstant.LOGIN_TYPE_SMS:
                user = baseMapper.selectByPhone(loginDTO.getAccount());
                break;
            case UserConstant.LOGIN_TYPE_EMAIL:
                user = baseMapper.selectByEmail(loginDTO.getAccount());
                break;
            default:
                throw new BusinessException(ResultCode.PARAM_IS_INVALID, "不支持的登录类型");
        }

        // 用户不存在
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        // 检查用户状态
        if (user.getStatus() == UserConstant.USER_STATUS_DISABLED) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_DISABLED);
        }
        if (user.getStatus() == UserConstant.USER_STATUS_LOCKED) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_LOCKED);
        }

        // 密码登录需要验证密码
        if (UserConstant.LOGIN_TYPE_PASSWORD.equals(loginDTO.getLoginType())) {
            String encryptedPassword = DigestUtil.md5Hex(loginDTO.getCredential());
            if (!encryptedPassword.equals(user.getPassword())) {
                throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
            }
        }

        // 获取用户角色和权限
        List<String> roles = getUserRoles(user.getId());
        List<String> permissions = getUserPermissions(user.getId());

        // 登录并生成 Token
        StpUtil.login(user.getId());
        StpUtil.getTokenSession().set("username", user.getUsername());
        StpUtil.getTokenSession().set("tenantId", user.getTenantId());
        StpUtil.getTokenSession().set("roles", new HashSet<>(roles));
        StpUtil.getTokenSession().set("permissions", new HashSet<>(permissions));

        // 更新登录信息
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);

        // 构建返回对象
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(StpUtil.getTokenValue());
        tokenDTO.setTokenType(UserConstant.TOKEN_TYPE_BEARER);
        tokenDTO.setExpiresIn(StpUtil.getTokenTimeout());
        tokenDTO.setUserId(user.getId());
        tokenDTO.setUsername(user.getUsername());
        tokenDTO.setNickname(user.getNickname());
        tokenDTO.setAvatar(user.getAvatar());
        tokenDTO.setRoles(new HashSet<>(roles));
        tokenDTO.setPermissions(new HashSet<>(permissions));

        log.info("用户登录成功 - userId: {}, username: {}", user.getId(), user.getUsername());
        return tokenDTO;
    }

    @Override
    public void logout(Long userId) {
        StpUtil.logout(userId);
        log.info("用户登出 - userId: {}", userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = baseMapper.selectByUsername(username);
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        User user = baseMapper.selectByPhone(phone);
        return convertToDTO(user);
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        return baseMapper.selectUserRoles(userId);
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        return baseMapper.selectUserPermissions(userId);
    }

    @Override
    public boolean validatePassword(Long userId, String password) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        String encryptedPassword = DigestUtil.md5Hex(password);
        return encryptedPassword.equals(user.getPassword());
    }

    @Override
    public TokenDTO refreshToken(String refreshToken) {
        // TODO: 实现刷新令牌逻辑
        return null;
    }

    /**
     * 转换为 DTO
     */
    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtil.copyProperties(user, dto);
        
        // 填充角色和权限
        List<String> roles = getUserRoles(user.getId());
        List<String> permissions = getUserPermissions(user.getId());
        dto.setRoles(roles);
        dto.setPermissions(permissions);
        
        return dto;
    }
}
