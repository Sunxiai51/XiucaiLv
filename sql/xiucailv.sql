/*
Navicat MySQL Data Transfer

Source Server         : root@localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : xiucailv

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-08-02 16:26:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_code` varchar(32) NOT NULL COMMENT '账户编号',
  `user_code` varchar(32) NOT NULL COMMENT '所属用户编号',
  `name` varchar(16) NOT NULL COMMENT '账户名',
  `account_no` varchar(32) DEFAULT NULL COMMENT '账号',
  `type` varchar(16) NOT NULL COMMENT '账户类型',
  `description` varchar(256) DEFAULT NULL COMMENT '账户描述',
  `status` varchar(16) NOT NULL COMMENT '账户状态',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ACCOUNT_CODE` (`account_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Table structure for account_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `account_snapshot`;
CREATE TABLE `account_snapshot` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '账户快照表主键',
  `account_code` varchar(32) NOT NULL COMMENT '账户编号',
  `snapshot_code` varchar(32) NOT NULL COMMENT '快照编号',
  `user_code` varchar(32) NOT NULL COMMENT '账户对应用户编号',
  `type` varchar(16) NOT NULL COMMENT '账户类型',
  `snapshot_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '快照创建时间',
  `balance` bigint(32) NOT NULL COMMENT '余额',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ACCOUNT_SNAPSHOT` (`account_code`,`snapshot_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户快照表';

-- ----------------------------
-- Table structure for assets_event
-- ----------------------------
DROP TABLE IF EXISTS `assets_event`;
CREATE TABLE `assets_event` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '资产事件表主键',
  `assets_event_code` varchar(32) NOT NULL COMMENT '资产事件编号',
  `event_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '事件发生时间',
  `title` varchar(32) NOT NULL COMMENT '事件标题',
  `description` varchar(128) DEFAULT NULL COMMENT '事件描述',
  `user_code` varchar(32) NOT NULL COMMENT '事件关联用户',
  `amount` bigint(32) DEFAULT NULL COMMENT '事件涉及金额',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ASSETS_EVENT_CODE` (`assets_event_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产事件表';

-- ----------------------------
-- Table structure for snapshot
-- ----------------------------
DROP TABLE IF EXISTS `snapshot`;
CREATE TABLE `snapshot` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '快照表主键',
  `snapshot_code` varchar(32) NOT NULL COMMENT '快照编号',
  `name` varchar(16) NOT NULL COMMENT '快照名称',
  `snapshot_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '快照创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_SNAPSHOT_CODE` (`snapshot_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快照表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_code` varchar(32) NOT NULL COMMENT '用户编号',
  `name` varchar(16) NOT NULL COMMENT '名称',
  `login_name` varchar(16) NOT NULL COMMENT '登录名',
  `login_password` varchar(32) NOT NULL COMMENT '登录密码',
  `login_password_salt` varchar(8) NOT NULL COMMENT '登录密码掩码',
  `type` varchar(16) NOT NULL COMMENT '用户类型',
  `status` varchar(16) NOT NULL COMMENT '状态',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USER_CODE` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';
