/*
 Navicat Premium Data Transfer

 Source Server         : cinema
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : cinema

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 26/08/2020 14:10:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Arrange
-- ----------------------------
DROP TABLE IF EXISTS `Arrange`;
CREATE TABLE `Arrange` (
  `Aud_id` int(11) NOT NULL,
  `Hall_id` int(11) NOT NULL,
  `Movie_id` int(11) NOT NULL,
  `Line` varchar(255) NOT NULL,
  `Row` varchar(255) NOT NULL,
  `Arrange_time` datetime NOT NULL,
  PRIMARY KEY (`Aud_id`,`Hall_id`,`Arrange_time`,`Movie_id`,`Line`,`Row`) USING BTREE,
  KEY `Hall_id` (`Hall_id`),
  KEY `Movie_id` (`Movie_id`),
  KEY `Arrange_time` (`Arrange_time`),
  CONSTRAINT `arrange_ibfk_1` FOREIGN KEY (`Aud_id`) REFERENCES `audience` (`Aud_id`),
  CONSTRAINT `arrange_ibfk_2` FOREIGN KEY (`Hall_id`) REFERENCES `show` (`Hall_id`) on update cascade on delete cascade,
  CONSTRAINT `arrange_ibfk_3` FOREIGN KEY (`Movie_id`) REFERENCES `show` (`Movie_id`) on update cascade on delete cascade,
  CONSTRAINT `arrange_ibfk_4` FOREIGN KEY (`Arrange_time`) REFERENCES `show` (`Show_time`) on update cascade on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of Arrange
-- ----------------------------
BEGIN;
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '1', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '2', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '3', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '4', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '5', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '1', '6', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '10', '1', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '2', '10', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '2', '11', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '2', '12', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '2', '5', '2020-08-29 09:48:15');
INSERT INTO `Arrange` VALUES (4, 1, 1, '2', '6', '2020-08-29 09:48:15');
COMMIT;

-- ----------------------------
-- Table structure for Movie
-- ----------------------------
DROP TABLE IF EXISTS `Movie`;
CREATE TABLE `Movie` (
  `Movie_id` int(11) NOT NULL,
  `Movie_name` varchar(20) NOT NULL,
  `Base_price` float(11,0) DEFAULT NULL,
  `Start_time` datetime DEFAULT NULL,
  `End_time` datetime DEFAULT NULL,
  `Last_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`Movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of Movie
-- ----------------------------
BEGIN;
INSERT INTO `Movie` VALUES (1, '辉夜大小姐', 20, '2020-20-08-25 00:00:00', '2020-10-20 00:00:00', 120);
INSERT INTO `Movie` VALUES (2, '小妇人', 30, '2020-08-25 00:00:00', '2020-09-25 00:00:00', 110);
INSERT INTO `Movie` VALUES (3, '千与千寻', 35, '2020-06-21 00:00:00', '2019-07-10 00:00:00', 100);
INSERT INTO `Movie` VALUES (4, '复仇者联盟', 30, '2012-05-04 00:00:00', '2012-05-31 00:00:00', 120);
INSERT INTO `Movie` VALUES (5, '命运之夜', 30, '2020-08-25 00:00:00', '2008-05-31 00:00:00', 80);
INSERT INTO `Movie` VALUES (6, '名侦探柯南', 30, '2020-08-25 00:00:00', '2020-08-28 00:00:00', 90);
COMMIT;

-- ----------------------------
-- Table structure for MovieHall
-- ----------------------------
DROP TABLE IF EXISTS `MovieHall`;
CREATE TABLE `MovieHall` (
  `Hall_id` int(11) NOT NULL,
  `Type` varchar(30) DEFAULT NULL,
  `Row_sum` int(11) DEFAULT NULL,
  `Line_sum` int(11) DEFAULT NULL,
  PRIMARY KEY (`Hall_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of MovieHall
-- ----------------------------
BEGIN;
INSERT INTO `MovieHall` VALUES (1, '激光巨幕', 20, 10);
INSERT INTO `MovieHall` VALUES (2, '杜比', 20, 10);
INSERT INTO `MovieHall` VALUES (3, '3D 杜比', 20, 10);
INSERT INTO `MovieHall` VALUES (4, 'IMAX 激光巨幕', 30, 15);
INSERT INTO `MovieHall` VALUES (5, '激光', 20, 10);
INSERT INTO `MovieHall` VALUES (6, 'VIP', 10, 5);
INSERT INTO `MovieHall` VALUES (7, '激光', 20, 10);
INSERT INTO `MovieHall` VALUES (8, 'VIP', 10, 5);
INSERT INTO `MovieHall` VALUES (9, '巨幕 杜比', 20, 10);
INSERT INTO `MovieHall` VALUES (10, '激光', 20, 10);
COMMIT;

-- ----------------------------
-- Table structure for Show
-- ----------------------------
DROP TABLE IF EXISTS `Show`;
CREATE TABLE `Show` (
  `Hall_id` int(11) NOT NULL,
  `Movie_id` int(11) NOT NULL,
  `Show_time` datetime NOT NULL,
  PRIMARY KEY (`Hall_id`,`Movie_id`,`Show_time`) USING BTREE,
  KEY `Movie_id` (`Movie_id`),
  KEY `Show_time` (`Show_time`),
  CONSTRAINT `Hall_id` FOREIGN KEY (`Hall_id`) REFERENCES `moviehall` (`Hall_id`),
  CONSTRAINT `Movie_id` FOREIGN KEY (`Movie_id`) REFERENCES `movie` (`Movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of Show
-- ----------------------------
BEGIN;
INSERT INTO `Show` VALUES (1, 1, '2020-08-29 09:48:15');
INSERT INTO `Show` VALUES (1, 6, '2020-08-21 23:00:00');
COMMIT;

-- ----------------------------
-- Table structure for audience
-- ----------------------------
DROP TABLE IF EXISTS `audience`;
CREATE TABLE `audience` (
  `Aud_id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Tel` varchar(30) DEFAULT NULL,
  `Type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Aud_id`,`Name`) USING BTREE,
  KEY `Aud_id` (`Aud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of audience
-- ----------------------------
BEGIN;
INSERT INTO `audience` VALUES (1, 'Bear', 'gf981026', '18341935543', 'Manger');
INSERT INTO `audience` VALUES (2, 'Dean', 'Sansui91200.', '17866553819', 'Manger');
INSERT INTO `audience` VALUES (3, '邵国举', 'shao123.', '17585298606', 'User');
INSERT INTO `audience` VALUES (4, '1', '1', '1', 'User');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
