SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父id',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `menu_type` int NULL DEFAULT NULL COMMENT '类型：0，目录，1菜单，2：按钮',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径   umsUser',
  `component_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径  如：ums/user/index',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  `status` int NULL DEFAULT NULL COMMENT '状态：0：可用，1：不可用',
  `creator` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `updater` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES (1, 0, '首页', 0, 0, 'index', 'index', 'index', '', 0, 0, 'admin', 'admin', '2023-12-11 18:57:19', '2023-12-11 18:57:21', '');
INSERT INTO `ums_menu` VALUES (2, 0, '系统管理', 1, 0, 'system', 'system', 'system', '', 0, 0, 'admin', 'admin', '2023-12-11 18:58:23', '2023-12-11 18:58:25', '');
INSERT INTO `ums_menu` VALUES (3, 0, '系统工具', 2, 0, 'tools', 'tools', 'tools', '', 0, 0, 'admin', 'admin', '2023-12-11 18:58:53', '2023-12-11 18:58:55', '');
INSERT INTO `ums_menu` VALUES (4, 0, '支付管理', 3, 0, 'pay', 'pay', 'pay', '', 0, 0, 'admin', 'admin', '2023-12-11 18:59:30', '2023-12-11 18:59:32', '');
INSERT INTO `ums_menu` VALUES (5, 2, '用户管理', 4, 1, 'system/user/index', 'sysUser', 'system:user:list', '', 0, 0, 'admin', 'admin', '2023-12-11 19:01:16', '2023-12-11 19:01:18', '');
INSERT INTO `ums_menu` VALUES (6, 2, '角色管理', 5, 1, 'system/role/index', 'sysRole', 'system:role:list', '', 0, 0, 'admin', 'admin', '2023-12-11 19:02:05', '2023-12-11 19:02:08', '');
INSERT INTO `ums_menu` VALUES (7, 2, '菜单管理', 6, 1, 'system/menu/index', 'sysMenu', 'system:menu:list', '', 0, 0, 'admin', 'admin', '2023-12-11 19:02:52', '2023-12-11 19:02:55', '');
INSERT INTO `ums_menu` VALUES (8, 3, '代码生成', 7, 1, 'tools/gen/index', 'toolsGen', 'tools:gen:list', '', 0, 0, 'admin', 'admin', '2023-12-11 19:03:57', '2023-12-11 19:04:00', '');
INSERT INTO `ums_menu` VALUES (9, 5, '新增用户', 8, 2, NULL, NULL, 'sysem:user:add', '', 0, 0, 'admin', 'admin', '2023-12-11 19:04:50', '2023-12-11 19:04:52', '');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色标识',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名字',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `status` int NULL DEFAULT NULL COMMENT '状态：0：可用，1：不可用',
  `deleted` int NULL DEFAULT NULL COMMENT '是否删除：0: 未删除，1：已删除',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `updater` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES (1, 'admin', 'admin', 0, 0, 0, NULL, 'admin', 'admin', '2023-12-11 19:08:39', '2023-12-11 19:08:42');
INSERT INTO `ums_role` VALUES (2, 'developer', '开发者', 1, 0, 0, NULL, 'admin', 'admin', '2023-12-11 19:08:45', '2023-12-11 19:08:47');

-- ----------------------------
-- Table structure for ums_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu`;
CREATE TABLE `ums_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL,
  `menu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_role_menu
-- ----------------------------
INSERT INTO `ums_role_menu` VALUES (1, 2, 5);
INSERT INTO `ums_role_menu` VALUES (2, 2, 6);
INSERT INTO `ums_role_menu` VALUES (3, 2, 7);
INSERT INTO `ums_role_menu` VALUES (4, 2, 8);
INSERT INTO `ums_role_menu` VALUES (5, 2, 9);
INSERT INTO `ums_role_menu` VALUES (6, 2, 1);
INSERT INTO `ums_role_menu` VALUES (7, 2, 2);
INSERT INTO `ums_role_menu` VALUES (8, 2, 3);
INSERT INTO `ums_role_menu` VALUES (9, 2, 4);

-- ----------------------------
-- Table structure for ums_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_sys_user`;
CREATE TABLE `ums_sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` int NULL DEFAULT 0 COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `status` int NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `creator` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1732769264547377155 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_sys_user
-- ----------------------------
INSERT INTO `ums_sys_user` VALUES (1, 'admin', 'admin', 'xiaoli@feidao.com', '123456789', 0, 'avatarurl', '$2a$10$pRwnzWnbVnhAhR8DBzvfrujFzguinBp13siEApFgasTbvqJxi3nrq', 0, 'admin', '2023-12-07 22:29:21', 'admin', '2023-12-07 22:29:21', '备注', 0);
INSERT INTO `ums_sys_user` VALUES (1732769264547377154, 'zs', '张三', 'zhangsan@123.com', '16632156458', 0, 'https://himg.bdimg.com/sys/portraitn/item/public.1.fc4cce31.XtVbJS1kKdVpCfCbNXODUw', '$2a$10$pRwnzWnbVnhAhR8DBzvfrujFzguinBp13siEApFgasTbvqJxi3nrq', 0, 'admin', '2023-12-11 19:06:41', 'admin', '2023-12-11 19:06:46', NULL, 0);

-- ----------------------------
-- Table structure for ums_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_sys_user_role`;
CREATE TABLE `ums_sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_sys_user_role
-- ----------------------------
INSERT INTO `ums_sys_user_role` VALUES (1, 1, 1);
INSERT INTO `ums_sys_user_role` VALUES (2, 1732769264547377154, 2);

SET FOREIGN_KEY_CHECKS = 1;
