/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2017-04-11 14:12:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `sys_user_id` varchar(50) NOT NULL COMMENT '系统用户编号',
  `wx_public_account_id` varchar(50) DEFAULT NULL COMMENT '所属公账号ID',
  `sys_user_name` varchar(20) DEFAULT NULL COMMENT '系统用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `register_time` varchar(20) DEFAULT '' COMMENT '注册时间',
  `sys_user_role` int(1) DEFAULT '2' COMMENT '角色：0：系统管理员；1：系统员工；2：系统用户',
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('6784a1e7-425a-47e9-8b70-4363416bb00f-1483702770271', null, '杨志钊', '10470c3b4b1fed12c3baac014be15fac67c6e815', '../../resources/img/userLogo.jpg', '', '', '2017-01-16 10:52:01', '1');
