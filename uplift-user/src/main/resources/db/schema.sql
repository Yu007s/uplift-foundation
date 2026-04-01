-- ===========================================
-- Uplift User Center - 多应用用户体系数据库设计
-- 支持：AI鉴宝(jb-c/jb-admin) + 路径记录(gostep-c/gostep-admin)
-- ===========================================

-- 应用表
CREATE TABLE IF NOT EXISTS sys_app (
    id BIGINT NOT NULL PRIMARY KEY COMMENT '应用ID',
    code VARCHAR(32) NOT NULL COMMENT '应用编码，如 jb-c / jb-admin / gostep-c / gostep-admin',
    name VARCHAR(64) NOT NULL COMMENT '应用名称',
    company_id INT NOT NULL DEFAULT 1 COMMENT '所属公司 1-AI鉴宝 2-路径记录',
    app_type INT NOT NULL DEFAULT 1 COMMENT '应用类型 1-C端 2-后台管理',
    app_secret VARCHAR(128) COMMENT '应用密钥',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_code (code),
    INDEX idx_company (company_id),
    INDEX idx_type (app_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- C端用户表（按 app_code 隔离）
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL PRIMARY KEY COMMENT '用户ID',
    app_code VARCHAR(32) NOT NULL COMMENT '应用编码',
    username VARCHAR(64) COMMENT '用户名',
    password VARCHAR(128) COMMENT '密码（BCrypt加密）',
    nickname VARCHAR(64) COMMENT '昵称',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(64) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像URL',
    open_id VARCHAR(64) COMMENT '开放平台标识（微信/支付宝）',
    register_source INT DEFAULT 1 COMMENT '注册来源 1-手机 2-邮箱 3-微信 4-支付宝',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    last_login_ip VARCHAR(64) COMMENT '最后登录IP',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_app_username (app_code, username),
    UNIQUE KEY uk_app_phone (app_code, phone),
    INDEX idx_app_code (app_code),
    INDEX idx_open_id (open_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='C端用户表';

-- 后台管理员表（按 app_code 隔离）
CREATE TABLE IF NOT EXISTS sys_admin (
    id BIGINT NOT NULL PRIMARY KEY COMMENT '管理员ID',
    app_code VARCHAR(32) NOT NULL COMMENT '应用编码（仅后台应用）',
    username VARCHAR(64) NOT NULL COMMENT '登录账号',
    password VARCHAR(128) NOT NULL COMMENT '密码（BCrypt加密）',
    real_name VARCHAR(64) COMMENT '姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(64) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    dept_id BIGINT COMMENT '部门ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    last_login_ip VARCHAR(64) COMMENT '最后登录IP',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_app_username (app_code, username),
    INDEX idx_app_code (app_code),
    INDEX idx_dept (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理员表';

-- 角色表（按 app_code 隔离）
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL PRIMARY KEY COMMENT '角色ID',
    app_code VARCHAR(32) NOT NULL COMMENT '应用编码',
    code VARCHAR(64) NOT NULL COMMENT '角色编码',
    name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_type INT NOT NULL DEFAULT 1 COMMENT '角色类型 1-C端角色 2-后台角色',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    sort INT DEFAULT 0 COMMENT '排序',
    remark VARCHAR(255) COMMENT '备注',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_app_code (app_code, code),
    INDEX idx_app_type (app_code, role_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表（菜单+按钮+接口）
CREATE TABLE IF NOT EXISTS sys_permission (
    id BIGINT NOT NULL PRIMARY KEY COMMENT '权限ID',
    app_code VARCHAR(32) NOT NULL COMMENT '应用编码',
    parent_id BIGINT NOT NULL DEFAULT 0 COMMENT '父级ID，顶级为0',
    name VARCHAR(64) NOT NULL COMMENT '权限名称',
    code VARCHAR(128) COMMENT '权限编码，如 user:list、user:add',
    perm_type INT NOT NULL DEFAULT 1 COMMENT '权限类型 1-菜单 2-按钮 3-接口',
    path VARCHAR(128) COMMENT '路由路径（菜单类型）',
    component VARCHAR(128) COMMENT '前端组件（菜单类型）',
    icon VARCHAR(64) COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '排序',
    hidden TINYINT DEFAULT 0 COMMENT '是否隐藏 0-显示 1-隐藏',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_app_code (app_code),
    INDEX idx_parent (parent_id),
    INDEX idx_type (perm_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- C端用户角色关联
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT NOT NULL PRIMARY KEY COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    UNIQUE KEY uk_user_role (user_id, role_id),
    INDEX idx_user (user_id),
    INDEX idx_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='C端用户角色关联';

-- 管理员角色关联
CREATE TABLE IF NOT EXISTS sys_admin_role (
    id BIGINT NOT NULL PRIMARY KEY COMMENT 'ID',
    admin_id BIGINT NOT NULL COMMENT '管理员ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    UNIQUE KEY uk_admin_role (admin_id, role_id),
    INDEX idx_admin (admin_id),
    INDEX idx_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员角色关联';

-- 角色权限关联
CREATE TABLE IF NOT EXISTS sys_role_permission (
    id BIGINT NOT NULL PRIMARY KEY COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    UNIQUE KEY uk_role_perm (role_id, permission_id),
    INDEX idx_role (role_id),
    INDEX idx_perm (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联';

-- ===========================================
-- 初始化数据
-- ===========================================

-- 初始化应用
INSERT INTO sys_app (id, code, name, company_id, app_type, status, remark) VALUES
(1, 'jb-c', 'AI鉴宝C端', 1, 1, 1, 'AI鉴宝小程序/APP'),
(2, 'jb-admin', 'AI鉴宝后台', 1, 2, 1, 'AI鉴宝后台管理系统'),
(3, 'gostep-c', '路径记录C端', 2, 1, 1, '路径记录小程序/APP'),
(4, 'gostep-admin', '路径记录后台', 2, 2, 1, '路径记录后台管理系统');

-- 初始化角色
INSERT INTO sys_role (id, app_code, code, name, role_type, status, sort, remark) VALUES
-- AI鉴宝C端角色
(1, 'jb-c', 'user', '普通用户', 1, 1, 1, '默认注册用户'),
(2, 'jb-c', 'vip', 'VIP用户', 1, 1, 2, '付费会员'),
-- AI鉴宝后台角色
(3, 'jb-admin', 'super_admin', '超级管理员', 2, 1, 1, '拥有所有权限'),
(4, 'jb-admin', 'admin', '管理员', 2, 1, 2, '日常运营'),
(5, 'jb-admin', 'operator', '运营人员', 2, 1, 3, '内容运营'),
-- 路径记录C端角色
(6, 'gostep-c', 'user', '普通用户', 1, 1, 1, '默认注册用户'),
(7, 'gostep-c', 'vip', 'VIP用户', 1, 1, 2, '付费会员'),
-- 路径记录后台角色
(8, 'gostep-admin', 'super_admin', '超级管理员', 2, 1, 1, '拥有所有权限'),
(9, 'gostep-admin', 'admin', '管理员', 2, 1, 2, '日常运营'),
(10, 'gostep-admin', 'operator', '运营人员', 2, 1, 3, '内容运营');

-- 初始化超级管理员（密码：admin123，BCrypt加密）
INSERT INTO sys_admin (id, app_code, username, password, real_name, status) VALUES
(1, 'jb-admin', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '超级管理员', 1),
(2, 'gostep-admin', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '超级管理员', 1);

-- 关联超级管理员角色
INSERT INTO sys_admin_role (id, admin_id, role_id) VALUES
(1, 1, 3),
(2, 2, 8);
