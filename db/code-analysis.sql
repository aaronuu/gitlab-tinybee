SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ca_commit_record
-- ----------------------------
DROP TABLE IF EXISTS `ca_commit_record`;
CREATE TABLE `ca_commit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `commit_record_id` varchar(60) NOT NULL DEFAULT '' COMMENT '提交记录id',
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_email` varchar(255) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `addition` int(11) NOT NULL DEFAULT '0' COMMENT '新增代码',
  `deletion` int(11) NOT NULL DEFAULT '0' COMMENT '删除代码',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '总计',
  `project_id` varchar(255) NOT NULL DEFAULT '' COMMENT '项目id',
  `project_name` varchar(255) NOT NULL COMMENT '项目名称',
  `web_url` varchar(255) NOT NULL COMMENT '页面地址',
  `branche` varchar(255) NOT NULL DEFAULT '' COMMENT '分支名称',
  `commit_id` varchar(60) NOT NULL DEFAULT '' COMMENT '提交Id',
  `commit_name` varchar(255) NOT NULL DEFAULT '' COMMENT '提交名称',
  `commit_message` varchar(1000) NOT NULL DEFAULT '' COMMENT '提交信息',
  `commit_title` varchar(255) NOT NULL DEFAULT '' COMMENT '提交头信息',
  `commit_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_commit_record_id` (`commit_record_id`) USING BTREE,
  UNIQUE KEY `uk_commit_i` (`commit_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=414250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户每天提交记录';

-- ----------------------------
-- Table structure for ca_holiday
-- ----------------------------
DROP TABLE IF EXISTS `ca_holiday`;
CREATE TABLE `ca_holiday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `holiday_id` varchar(64) NOT NULL DEFAULT '' COMMENT '节日id',
  `holiday_name` varchar(20) NOT NULL DEFAULT '' COMMENT '节日名称',
  `date` varchar(20) NOT NULL DEFAULT '' COMMENT '节日',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_holiday_id` (`holiday_id`) USING BTREE,
  UNIQUE KEY `uk_date` (`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='法定节假日';

-- ----------------------------
-- Table structure for ca_project
-- ----------------------------
DROP TABLE IF EXISTS `ca_project`;
CREATE TABLE `ca_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` varchar(64) NOT NULL DEFAULT '' COMMENT '项目id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '项目名称',
  `description` varchar(1000) NOT NULL DEFAULT '' COMMENT '项目描述',
  `default_branch` varchar(255) NOT NULL DEFAULT '' COMMENT '默认分支',
  `forks_count` int(11) NOT NULL DEFAULT '0',
  `http_url_to_repo` varchar(1000) NOT NULL DEFAULT '0' COMMENT 'http',
  `path_with_namespace` varchar(1000) NOT NULL DEFAULT '' COMMENT 'namespace',
  `ssh_url_to_repo` varchar(1000) NOT NULL COMMENT 'ssh',
  `star_count` int(11) NOT NULL COMMENT '页面地址',
  `web_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'web地址',
  `wiki_enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用wiki',
  `last_activity_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_ca_project_id` (`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='项目';

-- ----------------------------
-- Table structure for ca_user
-- ----------------------------
DROP TABLE IF EXISTS `ca_user`;
CREATE TABLE `ca_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `position` varchar(32) DEFAULT '',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `avatar_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '头像地址',
  `user_email` varchar(100) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `other_email` varchar(1000) NOT NULL DEFAULT '' COMMENT '其他邮箱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_ca_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `uk_ca_user_email` (`user_email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户';

SET FOREIGN_KEY_CHECKS = 1;
