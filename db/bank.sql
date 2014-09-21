-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 19, 2014 at 03:13 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bank`
--
CREATE DATABASE IF NOT EXISTS `bank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bank`;

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

CREATE TABLE IF NOT EXISTS `access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL COMMENT '卡号',
  `amount` decimal(20,3) DEFAULT NULL COMMENT '存取款金额',
  `time` varchar(45) NOT NULL COMMENT '交易时间',
  `tid` int(11) NOT NULL COMMENT '经办人（柜员）ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='存取款信息表' AUTO_INCREMENT=4 ;

--
-- Dumping data for table `access`
--

INSERT INTO `access` (`id`, `uuid`, `amount`, `time`, `tid`) VALUES
(1, '9559980196491319357', '9999999719.900', '2014-09-17 11:11:18', 7),
(2, '6228480190682907420', '99.000', '2014-09-17 11:14:11', 7),
(3, '9559980194612723103', '2104.400', '2014-09-17 02:59:37', 7);

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) NOT NULL COMMENT '管理员账号',
  `password` varchar(45) NOT NULL COMMENT '管理员密码',
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `time` varchar(45) DEFAULT NULL COMMENT '登录时间',
  `tid` int(11) DEFAULT NULL COMMENT '相应职位id（例如柜员id）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表' AUTO_INCREMENT=8 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `account`, `password`, `rid`, `time`, `tid`) VALUES
(1, 'admin', 'admin', 1, '1411095855543', 7),
(2, '1', '1', 2, '1409507136547', 5),
(3, 'cIZoBbZ7', 'zhoutianjing', 3, '1409507166485', 177),
(4, 'uFqLuV9v', 'qianxiancheng', 3, NULL, 176),
(5, 'xiaohua', '212544', 3, NULL, 5),
(7, 'xiaobai', '544554', 3, '1410941309859', 7);

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE IF NOT EXISTS `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL COMMENT '卡号',
  `identity` varchar(45) NOT NULL COMMENT '持卡人身份证',
  `name` varchar(20) DEFAULT NULL COMMENT '持卡人名称',
  `type` int(11) DEFAULT '0' COMMENT '银行卡类型',
  `status` int(8) NOT NULL COMMENT '卡的审核状态: 0 待审核，1 通过',
  `balance` decimal(20,3) NOT NULL DEFAULT '0.000' COMMENT '卡内金额',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  `errorNum` int(11) NOT NULL DEFAULT '0' COMMENT '锁卡条件：密码输入错误次数大于3次',
  `password` varchar(6) NOT NULL COMMENT '取款密码',
  `createTime` varchar(45) NOT NULL COMMENT '创建卡的时间',
  `lockTime` varchar(45) NOT NULL DEFAULT '0' COMMENT '锁卡时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='银行卡' AUTO_INCREMENT=24 ;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`id`, `uuid`, `identity`, `name`, `type`, `status`, `sort`, `errorNum`, `password`, `createTime`, `lockTime`) VALUES
(18, '6228480197473235588', '130182198812123347', '张三', 11, 1, 0, 0, '235588', '2014-09-18 03:50:36', '0'),
(19, '6228368019323498', '130182198812123347', '张三', 622836, 1, 0, 0, '323498', '2014-09-18 03:52:49', '0'),
(20, '6228378019111634', '130182198812123347', '张三', 622837, 1, 0, 0, '111634', '2014-09-18 04:08:49', '0'),
(21, '6228368019884398', '130182198812123347', '张三', 622836, 1, 0, 0, '884398', '2014-09-18 04:31:24', '0'),
(22, '6228378019924143', '130182198812123347', '张三', 622837, 2, 0, 0, '924143', '2014-09-18 04:53:36', '0'),
(23, '6228368019892020', '130182198812123347', '张三', 622836, 0, 0, 0, '892020', '2014-09-18 04:57:15', '0');

-- --------------------------------------------------------

--
-- Table structure for table `credit_info`
--

CREATE TABLE IF NOT EXISTS `credit_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `marry` int(11) NOT NULL DEFAULT '0' COMMENT '婚姻状况  未婚、已有子女、已婚无子女、其他',
  `hosing` int(11) NOT NULL COMMENT '住宅情况：外地租房、与父母同住、自己住',
  `liveTime` varchar(10) NOT NULL COMMENT '现在住处居住时间',
  `income` decimal(9,3) NOT NULL COMMENT '个人年收入',
  `company` varchar(45) NOT NULL COMMENT '公司名称',
  `experience` int(11) NOT NULL COMMENT '工作年限',
  `career` varchar(45) NOT NULL COMMENT '职业',
  `careerType` varchar(45) NOT NULL COMMENT '行业种类',
  `title` varchar(45) NOT NULL COMMENT '职称',
  `position` varchar(45) NOT NULL COMMENT '职务',
  `cid` int(11) NOT NULL COMMENT '银行卡Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='信用卡申请信息表' AUTO_INCREMENT=8 ;

--
-- Dumping data for table `credit_info`
--

INSERT INTO `credit_info` (`id`, `uid`, `marry`, `hosing`, `liveTime`, `income`, `company`, `experience`, `career`, `careerType`, `title`, `position`, `cid`) VALUES
(4, 1, 12, 16, '1', '12.000', '北京中科软科技有限公司销售部', 2, '销售', '销售', '销售经理', '管理', 20),
(3, 1, 12, 16, '1', '12.000', '北京中科软科技有限公司销售部', 2, '销售', '销售', '销售经理', '管理', 19),
(5, 1, 12, 17, '1', '12.000', '北京中科软科技有限公司销售部', 2, '销售', '销售', '销售经理', '管理', 21),
(6, 1, 12, 16, '1', '12.000', '北京中科软科技有限公司销售部', 2, '销售', '销售', '销售经理', '管理', 22),
(7, 1, 12, 16, '1', '12.000', '北京中科软科技有限公司销售部', 2, '销售', '销售', '销售经理', '管理', 23);

-- --------------------------------------------------------

--
-- Table structure for table `detail`
--

CREATE TABLE IF NOT EXISTS `detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identity` varchar(18) NOT NULL COMMENT '用户身份证号',
  `uuid` varchar(45) NOT NULL COMMENT '银行卡号',
  `time` varchar(45) DEFAULT NULL COMMENT '交易产生时间',
  `type` int(11) DEFAULT '0' COMMENT '交易类型（0：支出，1：收入）',
  `amount` decimal(20,3) DEFAULT NULL COMMENT '交易额',
  `balance` decimal(20,3) NOT NULL DEFAULT '0.000' COMMENT '交易后余额',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='交易明细表' AUTO_INCREMENT=21 ;

--
-- Dumping data for table `detail`
--

INSERT INTO `detail` (`id`, `identity`, `uuid`, `time`, `type`, `amount`, `balance`, `sort`) VALUES
(18, '130182198812123347', '9559980194612723103', '2014-09-17 02:58:59', 1, '1.400', '2003.400', 0),
(19, '130182198812123347', '9559980194612723103', '2014-09-17 02:59:07', 1, '112.000', '2115.400', 0),
(20, '130182198812123347', '9559980194612723103', '2014-09-17 02:59:37', 0, '11.000', '2104.400', 0);

-- --------------------------------------------------------

--
-- Table structure for table `dictionary`
--

CREATE TABLE IF NOT EXISTS `dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '名称',
  `key` varchar(45) NOT NULL,
  `remark` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='字典表' AUTO_INCREMENT=622838 ;

--
-- Dumping data for table `dictionary`
--

INSERT INTO `dictionary` (`id`, `name`, `key`, `remark`) VALUES
(1, '男', 'sex', '性别'),
(2, '女', 'sex', '性别'),
(3, '党员', 'feature', '政治面貌'),
(4, '团员', 'feature', '政治面貌'),
(5, '群众', 'feature', '政治面貌'),
(6, '专科', 'education', '学历'),
(7, '学士', 'education', '学历'),
(8, '硕士', 'education', '学历'),
(9, '博士', 'education', '学历'),
(10, '其他', 'education', '学历'),
(11, '中国农业银行世纪通宝借记卡', 'cardType', '农行卡类别'),
(12, '未婚', 'marryType', '婚姻状况'),
(13, '已有子女', 'marryType', '婚姻状况'),
(14, '已婚无子女', 'marryType', '婚姻状况'),
(15, '其他', 'marryType', '婚姻状况'),
(16, '租房', 'home', '住房情况'),
(17, '与父母同住', 'home', '住房情况'),
(18, '自己住', 'home', '住房情况'),
(622836, '中国农业银行人民币贷记卡(金卡)', 'cardType', '农行卡类别'),
(622837, '中国农业银行人民币贷记卡(普卡)', 'cardType', '农行卡类别');

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '权限名称',
  `url` varchar(45) DEFAULT NULL COMMENT 'url',
  `pid` int(11) DEFAULT NULL COMMENT '父权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=22 ;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`id`, `name`, `url`, `pid`) VALUES
(2, '个人信息', '/admin/myinfo', 0),
(3, '用户信息管理', '/admin/user', 0),
(4, '存取款管理', '/admin/access', 0),
(5, '银行卡管理', '/admin/card', 0),
(6, '收支明细', '/admin/detail', 0),
(7, '柜员管理', '/admin/teller', 0),
(8, '审核管理', '/admin/verify', 0),
(10, '修改密码', '/admin/myinfo/index', 2),
(11, '用户信息查询', '/admin/user/index', 3),
(12, '录入用户信息', '/admin/user/add', 3),
(13, '取款', '/admin/access/get', 4),
(14, '存款', '/admin/access/save', 4),
(15, '银行卡查询', '/admin/card/index', 5),
(17, '明细查询', '/admin/detail/index', 6),
(18, '柜员信息列表', '/admin/teller/index', 7),
(19, '录入柜员信息', '/admin/teller/add', 7),
(20, '信用卡审核', '/admin/verify/index', 8),
(21, '贷款审核', '/admin/verify/loan', 8);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表' AUTO_INCREMENT=4 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `remark`) VALUES
(1, '系统管理员', '拥有所有权限'),
(2, '审核员', '拥有除柜员管理之外的所有权限'),
(3, '柜员', '拥有除柜员管理和审核管理之外的所有权限');

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE IF NOT EXISTS `role_permission` (
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `pid` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限对应表';

--
-- Dumping data for table `role_permission`
--

INSERT INTO `role_permission` (`rid`, `pid`) VALUES
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 2),
3(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 8),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6);

-- --------------------------------------------------------

--
-- Table structure for table `teller`
--

CREATE TABLE IF NOT EXISTS `teller` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL COMMENT '银行柜员编号',
  `name` varchar(45) NOT NULL COMMENT '姓名',
  `identity` varchar(18) NOT NULL COMMENT '身份证',
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `sex` int(11) NOT NULL COMMENT '性别',
  `birth` varchar(45) NOT NULL COMMENT '出生日期',
  `birthplace` varchar(45) NOT NULL COMMENT '籍贯',
  `national` varchar(10) NOT NULL COMMENT '民族',
  `feature` int(11) NOT NULL DEFAULT '0' COMMENT '政治面貌（党员，团员，群众）',
  `desc` text COMMENT '柜员介绍',
  `education` int(11) NOT NULL DEFAULT '0' COMMENT '学历（学士，硕士，博士）',
  `address` varchar(100) NOT NULL COMMENT '家庭住址',
  `phone` varchar(11) NOT NULL COMMENT '电话',
  `email` varchar(100) NOT NULL COMMENT '电子邮箱',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `image` varchar(100) DEFAULT NULL COMMENT '图片url',
  `time` varchar(45) DEFAULT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='柜员信息表' AUTO_INCREMENT=8 ;

--
-- Dumping data for table `teller`
--

INSERT INTO `teller` (`id`, `uuid`, `name`, `identity`, `rid`, `sex`, `birth`, `birthplace`, `national`, `feature`, `desc`, `education`, `address`, `phone`, `email`, `sort`, `image`, `time`) VALUES
(6, 'RrjGjdVS', '小花', '130182155212544', 3, 2, '1993-01-11', '河北省石家庄藁城市系名镇小乔村', '汉族', 4, '规划局股份很舒服的华盛顿放虎归山的分公司的风格谁的高富商大贾是大法官是大法官', 8, '河北省石家庄藁城市系名镇小乔村', '18231146578', '123123@qq.com', 1, '/upload/avatar/1410851393468.png', '2014-09-16 03:09:58'),
(7, 'N4DmxRWR', '小白', '130182155212544554', 3, 1, '1993-01-11', '河北省石家庄藁城市系名镇小乔村', '汉族', 4, '按时大法师的发生的撒打算打算打算打算打算打算', 7, '河北省石家庄藁城市系名镇小乔村', '18231146578', '123123@qq.com', 1, '/upload/avatar/1410851810140.png', '2014-09-16 03:16:52');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '姓名',
  `identity` varchar(18) NOT NULL COMMENT '身份证',
  `sex` int(11) NOT NULL COMMENT '性别',
  `birth` varchar(45) NOT NULL COMMENT '出生日期',
  `birthplace` varchar(45) NOT NULL COMMENT '籍贯',
  `national` varchar(10) NOT NULL COMMENT '民族',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `desc` text COMMENT '简介',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0：可用，1：已销户）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `image` varchar(100) DEFAULT NULL COMMENT '身份证图片url',
  `time` varchar(45) DEFAULT NULL COMMENT '开户时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `identity_UNIQUE` (`identity`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户信息表' AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `identity`, `sex`, `birth`, `birthplace`, `national`, `phone`, `desc`, `address`, `status`, `sort`, `image`, `time`) VALUES
(1, '张三', '130182198812123347', 1, '1988-12-12', '河北省藁城市', '汉', '13718621965', NULL, '北京市海淀区四季青', 0, 0, '/upload/avatar/1410691084823.jpg', '2014-09-14 06:38:04'),
(2, '李四', '130182198912223349', 1, '1989-12-22', '河北省藁城市', '汉', '13718631365', NULL, '北京市海淀区四季青', 0, 0, '/upload/avatar/1410692218498.png', '2014-09-14 06:56:58');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
