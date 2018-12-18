/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-12-18 15:45:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_datasource`
-- ----------------------------
DROP TABLE IF EXISTS `t_datasource`;
CREATE TABLE `t_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `driver` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_datasource
-- ----------------------------
INSERT INTO `t_datasource` VALUES ('1', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC', 'root', 'root');
INSERT INTO `t_datasource` VALUES ('2', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@127.0.0.1:1521:orcl', 'cqwu729', '123456');
