-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 11, 2014 at 07:17 AM
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
  `amount` int(20) DEFAULT NULL COMMENT '存取款金额',
  `time` varchar(45) NOT NULL COMMENT '交易时间',
  `tid` int(11) NOT NULL COMMENT '经办人（柜员）ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='存取款信息表' AUTO_INCREMENT=25 ;

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
  `tid` int(11) DEFAULT NULL COMMENT '教师ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表' AUTO_INCREMENT=5 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `account`, `password`, `rid`, `time`, `tid`) VALUES
(1, 'admin', 'admin', 1, '1409506814760', NULL),
(2, '1', '1', 2, '1409507136547', NULL),
(3, 'cIZoBbZ7', 'zhoutianjing', 3, '1409507166485', 177),
(4, 'uFqLuV9v', 'qianxiancheng', 3, NULL, 176);

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE IF NOT EXISTS `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL COMMENT '卡号',
  `name` varchar(20) DEFAULT NULL COMMENT '持卡人名称',
  `type` int(11) DEFAULT '0' COMMENT '银行卡类型',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='银行卡' AUTO_INCREMENT=25 ;

-- --------------------------------------------------------

--
-- Table structure for table `detail`
--

CREATE TABLE IF NOT EXISTS `detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL COMMENT '序号',
  `cid` int(11) NOT NULL COMMENT '银行卡ID',
  `time` varchar(45) DEFAULT NULL COMMENT '交易产生时间',
  `type` int(11) DEFAULT '0' COMMENT '交易类型（0：支出，1：收入）',
  `amount` int(11) DEFAULT NULL COMMENT '交易额',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='交易明细表' AUTO_INCREMENT=25 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=26 ;

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
(16, '添加银行卡', '/admin/card/add', 5),
(17, '明细查询', '/admin/detail/index', 6),
(18, '柜员信息列表', '/admin/teller/index', 7),
(19, '录入柜员信息', '/admin/teller/add', 7),
(20, '信用卡审核', '/admin/verify/credit', 8),
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表' AUTO_INCREMENT=5 ;

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
(2, 3),
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
  `uuid` varchar(8) NOT NULL COMMENT '11位教师编号',
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='柜员信息表' AUTO_INCREMENT=191 ;

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
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户信息表' AUTO_INCREMENT=361 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;