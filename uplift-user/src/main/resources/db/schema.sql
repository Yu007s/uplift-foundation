-- ============================================
-- Uplift User Center Database Schema
-- 用户中心数据库初始化脚本
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS uplift_user DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE uplift_user;

-- ============================================
-- 1. 租户表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_tenant (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '租户ID',
    code VARCHAR(64) NOT NULL COMMENT '租户编码',
    name VARCHAR(128) NOT NULL COMMENT '租户名称',
    contact_name VARCHAR(64) DEFAULT NULL COMMENT '联系人',
    contact_phone VARCHAR(32) DEFAULT NULL COMMENT '联系电话',
    contact_email VARCHAR(128) DEFAULT NULL COMMENT '联系邮箱',
    type TINYINT DEFAULT 1 COMMENT '租户类型 1-企业 2-个人',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    expire_time DATE DEFAULT NULL COMMENT '过期时间',
    account_limit INT DEFAULT 100 COMMENT '账号数量限制',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ============================================
-- 2. 用户表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    tenant_id BIGINT DEFAULT NULL COMMENT '租户ID',
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码',
    nickname VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    real_name VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    phone VARCHAR(32) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    avatar VARCHAR(512) DEFAULT NULL COMMENT '头像',
    gender TINYINT DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用 2-锁定',
    dept_id BIGINT DEFAULT NULL COMMENT '部门ID',
    last_login_ip VARCHAR(64) DEFAULT NULL COMMENT '最后登录IP',
    last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT DEFAULT NULL COMMENT '创建人',
    update_by BIGINT DEFAULT NULL COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_phone (phone),
    UNIQUE KEY uk_email (email),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status),
    KEY idx_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================
-- 3. 角色表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    tenant_id BIGINT DEFAULT NULL COMMENT '租户ID',
    code VARCHAR(64) NOT NULL COMMENT '角色编码',
    name VARCHAR(64) NOT NULL COMMENT '角色名称',
    data_scope TINYINT DEFAULT 1 COMMENT '数据权限范围 1-全部 2-本部门 3-本部门及子部门 4-仅本人 5-自定义',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    sort INT DEFAULT 0 COMMENT '排序',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ============================================
-- 4. 菜单/权限表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    name VARCHAR(64) NOT NULL COMMENT '菜单名称',
    path VARCHAR(256) DEFAULT NULL COMMENT '路由路径',
    component VARCHAR(256) DEFAULT NULL COMMENT '组件路径',
    permission VARCHAR(256) DEFAULT NULL COMMENT '权限标识',
    type TINYINT DEFAULT 1 COMMENT '菜单类型 1-目录 2-菜单 3-按钮',
    icon VARCHAR(128) DEFAULT NULL COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    hidden TINYINT DEFAULT 0 COMMENT '是否隐藏 0-显示 1-隐藏',
    keep_alive TINYINT DEFAULT 0 COMMENT '是否缓存 0-不缓存 1-缓存',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_status (status),
    KEY idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- ============================================
-- 5. 用户角色关联表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id),
    KEY idx_user_id (user_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ============================================
-- 6. 角色菜单关联表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_menu (role_id, menu_id),
    KEY idx_role_id (role_id),
    KEY idx_menu_id (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- ============================================
-- 7. 部门表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    tenant_id BIGINT DEFAULT NULL COMMENT '租户ID',
    name VARCHAR(64) NOT NULL COMMENT '部门名称',
    code VARCHAR(64) DEFAULT NULL COMMENT '部门编码',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ============================================
-- 8. 岗位表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    tenant_id BIGINT DEFAULT NULL COMMENT '租户ID',
    code VARCHAR(64) NOT NULL COMMENT '岗位编码',
    name VARCHAR(64) NOT NULL COMMENT '岗位名称',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- ============================================
-- 9. 用户岗位关联表
-- ============================================
CREATE TABLE IF NOT EXISTS sys_user_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '岗位ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_post (user_id, post_id),
    KEY idx_user_id (user_id),
    KEY idx_post_id (post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户岗位关联表';

-- ============================================
-- 10. 应用表（支持多应用接入）
-- ============================================
CREATE TABLE IF NOT EXISTS sys_app (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '应用ID',
    tenant_id BIGINT DEFAULT NULL COMMENT '租户ID',
    code VARCHAR(64) NOT NULL COMMENT '应用编码',
    name VARCHAR(128) NOT NULL COMMENT '应用名称',
    secret VARCHAR(256) NOT NULL COMMENT '应用密钥',
    icon VARCHAR(512) DEFAULT NULL COMMENT '应用图标',
    url VARCHAR(512) DEFAULT NULL COMMENT '应用地址',
    callback_url VARCHAR(512) DEFAULT NULL COMMENT '回调地址',
    type TINYINT DEFAULT 1 COMMENT '应用类型 1-Web 2-APP 3-小程序',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    expire_time DATE DEFAULT NULL COMMENT '过期时间',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_tenant_id (tenant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- ============================================
-- 初始化数据
-- ============================================

-- 初始化租户
INSERT INTO sys_tenant (id, code, name, type, status, account_limit) VALUES
(1, 'default', '默认租户', 1, 1, 1000);

-- 初始化角色
INSERT INTO sys_role (id, tenant_id, code, name, data_scope, status, sort) VALUES
(1, 1, 'super_admin', '超级管理员', 1, 1, 1),
(2, 1, 'admin', '管理员', 2, 1, 2),
(3, 1, 'user', '普通用户', 4, 1, 3);

-- 初始化菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, permission, type, icon, sort, status) VALUES
(1, 0, '系统管理', '/system', NULL, NULL, 1, 'SettingOutlined', 1, 1),
(2, 1, '用户管理', '/system/user', 'system/user/index', 'system:user:list', 2, 'UserOutlined', 1, 1),
(3, 2, '用户查询', NULL, NULL, 'system:user:query', 3, NULL, 1, 1),
(4, 2, '用户新增', NULL, NULL, 'system:user:add', 3, NULL, 2, 1),
(5, 2, '用户修改', NULL, NULL, 'system:user:edit', 3, NULL, 3, 1),
(6, 2, '用户删除', NULL, NULL, 'system:user:delete', 3, NULL, 4, 1),
(7, 1, '角色管理', '/system/role', 'system/role/index', 'system:role:list', 2, 'TeamOutlined', 2, 1),
(8, 7, '角色查询', NULL, NULL, 'system:role:query', 3, NULL, 1, 1),
(9, 7, '角色新增', NULL, NULL, 'system:role:add', 3, NULL, 2, 1),
(10, 7, '角色修改', NULL, NULL, 'system:role:edit', 3, NULL, 3, 1),
(11, 7, '角色删除', NULL, NULL, 'system:role:delete', 3, NULL, 4, 1);

-- 初始化部门
INSERT INTO sys_dept (id, parent_id, tenant_id, name, code, status, sort) VALUES
(1, 0, 1, '总部', 'HQ', 1, 1),
(2, 1, 1, '技术部', 'TECH', 1, 1),
(3, 1, 1, '市场部', 'MKT', 1, 2);

-- 初始化超级管理员用户 (密码: 123456)
INSERT INTO sys_user (id, tenant_id, username, password, nickname, real_name, phone, email, gender, status, dept_id) VALUES
(1, 1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '管理员', '13800138000', 'admin@uplift.com', 1, 1, 1);

-- 关联用户角色
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1);

-- 关联角色菜单（超级管理员拥有所有权限）
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11);
