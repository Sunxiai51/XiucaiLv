/*
Navicat MySQL Data Transfer

Source Server         : root@localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : xiucailv

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-07-24 16:22:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
