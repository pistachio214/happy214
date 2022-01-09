
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) NOT NULL,
  `path` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型: 0目录; 1菜单; 2按钮;',
  `icon` varchar(32) DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int DEFAULT NULL COMMENT '排序',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='系统菜单表';
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `perms`, `component`, `type`, `icon`, `orderNum`, `created_at`, `updated_at`, `status`)
VALUES
	(1,0,'仪表盘','/dashboard','sys:home',NULL,0,'DashboardOutlined',5,'2021-10-06 23:46:52',NULL,1),
	(5,0,'系统工具','/utils','sys:tools',NULL,0,'RadiusSettingOutlined',1,'2021-01-15 19:06:11','2022-01-08 15:50:36',1),
	(6,5,'数字字典','/utils/dicts','sys:dict:list','sys/Dict',1,'',1,'2021-01-15 19:07:18','2021-01-18 16:32:13',1),
	(21,0,'系统设置','/system','sys:home',NULL,0,'SettingOutlined',2,'2021-10-04 00:00:00','2022-01-08 15:50:29',1),
	(22,21,'人员管理','/system/users','sys:user:list','sys/User',1,'',1,'2021-01-15 19:03:45','2021-11-10 11:15:05',1),
	(23,21,'角色管理','/system/roles','sys:role:list','sys/Role',1,'',2,'2021-01-15 19:03:45','2021-01-15 19:03:48',1),
	(24,21,'菜单管理','/system/menus','sys:menu:list','sys/Menu',1,'',3,'2021-01-15 19:03:45','2021-01-15 19:03:48',1),
	(27,23,'添加角色','','sys:role:save','',2,'',1,'2021-01-15 23:02:25','2021-01-17 21:53:14',0),
	(29,22,'添加用户',NULL,'sys:user:save',NULL,2,NULL,1,'2021-01-17 21:48:32',NULL,1),
	(30,22,'修改用户',NULL,'sys:user:update',NULL,2,NULL,2,'2021-01-17 21:49:03','2021-01-17 21:53:04',1),
	(31,22,'删除用户',NULL,'sys:user:delete',NULL,2,NULL,3,'2021-01-17 21:49:21',NULL,1),
	(32,23,'分配角色',NULL,'sys:user:role',NULL,2,NULL,4,'2021-01-17 21:49:58',NULL,1),
	(33,22,'重置密码',NULL,'sys:user:repass',NULL,2,NULL,5,'2021-01-17 21:50:36',NULL,1),
	(34,23,'修改角色',NULL,'sys:role:update',NULL,2,NULL,2,'2021-01-17 21:51:14',NULL,1),
	(35,23,'删除角色',NULL,'sys:role:delete',NULL,2,NULL,3,'2021-01-17 21:51:39',NULL,1),
	(36,23,'分配权限',NULL,'sys:role:perm',NULL,2,NULL,5,'2021-01-17 21:52:02',NULL,1),
	(37,24,'添加菜单',NULL,'sys:menu:save',NULL,2,NULL,1,'2021-01-17 21:53:53','2021-01-17 21:55:28',1),
	(38,24,'修改菜单',NULL,'sys:menu:update',NULL,2,NULL,2,'2021-01-17 21:56:12',NULL,1),
	(39,24,'删除菜单',NULL,'sys:menu:delete',NULL,2,NULL,3,'2021-01-17 21:56:36',NULL,1);

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='系统角色表';
INSERT INTO `sys_role` (`id`, `name`, `code`, `remark`, `created_at`, `updated_at`, `status`)
VALUES
	(1,'超级管理员','administer','系统默认最高权限，不可以编辑和任意修改','2021-01-16 13:29:03','2022-01-05 22:33:59',1),
	(3,'普通用户','normal','只有基本查看功能','2021-01-04 10:09:14','2022-01-06 20:32:27',1);

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色菜单链接表';
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`, `status`)
VALUES
	(1,1,1,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(2,1,5,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(3,1,6,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(4,1,21,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(5,1,22,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(6,1,23,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(7,1,24,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(8,1,27,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(9,1,29,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(10,1,30,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(11,1,31,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(12,1,32,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(13,1,33,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(14,1,34,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(15,1,35,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(16,1,36,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(17,1,37,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(18,1,38,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(19,1,39,'2021-12-29 10:20:57','2021-12-29 10:20:57',1),
	(34,3,1,'2022-01-08 15:54:37',NULL,1),
	(35,3,5,'2022-01-08 15:54:37',NULL,1),
	(36,3,6,'2022-01-08 15:54:37',NULL,1);

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='系统用户表';
INSERT INTO `sys_user` (`id`, `nickname`, `username`, `password`, `avatar`, `email`, `city`, `last_login`, `created_at`, `updated_at`, `status`)
VALUES
	(1,'超级管理员','admin','$2a$10$apWMyb4PYXtWxrssCqnt0eXVG06C9y1pZPJSpA6m70gi1MzOGDbz6',NULL,NULL,NULL,NULL,'2021-10-02 22:44:46',NULL,1),
	(3,'测试管理员','test','$2a$10$nIoa6i2ZFfIevdTE4.x84.Jnj6nls7ybUSLB2Jm9JUSGt.uj6FM2i','https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg','test@qq.com',NULL,'2021-01-30 08:20:22','2021-01-30 08:55:57','2022-01-08 15:56:07',1);


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统管理员角色链接表';
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`, `status`)
VALUES
	(13,2,3,'2021-01-17 21:56:36',NULL,1),
	(22,1,1,'2022-01-06 22:28:14',NULL,0),
	(23,1,3,'2022-01-06 22:28:14',NULL,0),
	(26,3,3,'2022-01-06 22:28:21',NULL,0),
	(28,6,1,'2022-01-07 15:48:49',NULL,1);


ALTER TABLE `sys_user` ADD `type` INT(3) DEFAULT 1 COMMENT '用户类型: 1管理员; 2普通用户; ' AFTER `password`;

DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL COMMENT '编号',
  `type` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL,
  `system` char(1) DEFAULT '0',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典表';

DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` bigint NOT NULL COMMENT '编号',
  `dict_id` bigint NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序（升序）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典项';

