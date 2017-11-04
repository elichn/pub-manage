/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1-本地测试
Source Server Version : 50153
Source Host           : 127.0.0.1:3306
Source Database       : pub-manage

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2017-11-01 10:09:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `se_home_page_notice`
-- ----------------------------
DROP TABLE IF EXISTS `se_home_page_notice`;
CREATE TABLE `se_home_page_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` mediumtext COMMENT '公告内容',
  `url` varchar(100) DEFAULT NULL COMMENT '链接',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1-发布 0-不发布',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1-url 2-content',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='首页公告';

-- ----------------------------
-- Records of se_home_page_notice
-- ----------------------------
INSERT INTO `se_home_page_notice` VALUES ('1', '<p><span style=\"color:#ff0000\"><strong>【<img src=\"http://img.baidu.com/hi/face/i_f39.gif\" _src=\"http://img.baidu.com/hi/face/i_f39.gif\"/> 通知哦 <img src=\"http://img.baidu.com/hi/face/i_f38.gif\" _src=\"http://img.baidu.com/hi/face/i_f38.gif\"/>】</strong></span><br/></p><ol><li><h4>hello，首页通知test!</h4></li></ol><p align=\"left\"><br/></p>', '', '1', '2017-10-30 15:52:15', '2017-11-02 22:12:13', '2');

-- ----------------------------
-- Table structure for `se_log`
-- ----------------------------
DROP TABLE IF EXISTS `se_log`;
CREATE TABLE `se_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `log_type` varchar(200) DEFAULT NULL COMMENT '日志类型',
  `log_content` varchar(4000) DEFAULT NULL COMMENT '日志内容',
  `log_time` datetime DEFAULT NULL COMMENT '记录日志时间',
  PRIMARY KEY (`id`),
  KEY `idx_log_type` (`log_type`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of se_log
-- ----------------------------
INSERT INTO `se_log` VALUES ('1', '0:0:0:0:0:0:0:1', 'admin', '用户登录', null, '2017-11-01 09:52:36');
INSERT INTO `se_log` VALUES ('2', '0:0:0:0:0:0:0:1', 'admin', '新增资源', '新增资源(资源名:首页通知,资源ID:18)', '2017-11-01 09:53:21');
INSERT INTO `se_log` VALUES ('3', '0:0:0:0:0:0:0:1', 'admin', '添加角色', '添加角色(普通管理员)', '2017-11-01 09:53:56');
INSERT INTO `se_log` VALUES ('4', '0:0:0:0:0:0:0:1', 'admin', '添加角色', '添加角色(产品)', '2017-11-01 09:57:28');
INSERT INTO `se_log` VALUES ('5', '0:0:0:0:0:0:0:1', 'admin', '添加角色', '添加角色(运营)', '2017-11-01 09:57:34');
INSERT INTO `se_log` VALUES ('6', '0:0:0:0:0:0:0:1', 'admin', '添加角色', '添加角色(开发)', '2017-11-01 09:57:40');
INSERT INTO `se_log` VALUES ('7', '0:0:0:0:0:0:0:1', 'admin', '新增资源', '新增资源(资源名:二级菜单test,资源ID:19)', '2017-11-01 10:06:24');
INSERT INTO `se_log` VALUES ('8', '0:0:0:0:0:0:0:1', 'admin', '新增资源', '新增资源(资源名:操作日志列表test,资源ID:20)', '2017-11-01 10:06:59');
INSERT INTO `se_log` VALUES ('9', '0:0:0:0:0:0:0:1', 'admin', '新增资源', '新增资源(资源名:三级菜单test,资源ID:21)', '2017-11-01 10:07:24');
INSERT INTO `se_log` VALUES ('10', '0:0:0:0:0:0:0:1', 'admin', '新增资源', '新增资源(资源名:操作日志列表test,资源ID:22)', '2017-11-01 10:07:46');
INSERT INTO `se_log` VALUES ('11', '0:0:0:0:0:0:0:1', 'admin', '用户注销', null, '2017-11-01 10:08:51');
INSERT INTO `se_log` VALUES ('12', '0:0:0:0:0:0:0:1', 'test', '用户登录', null, '2017-11-01 10:08:56');

-- ----------------------------
-- Table structure for `se_public_permission`
-- ----------------------------
DROP TABLE IF EXISTS `se_public_permission`;
CREATE TABLE `se_public_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `resource` varchar(50) NOT NULL COMMENT '资源',
  `filter` varchar(50) NOT NULL COMMENT '过滤器',
  `system` varchar(50) DEFAULT NULL COMMENT '系统名称，为空表示所有系统都适用',
  `comment` varchar(50) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='公共权限';

-- ----------------------------
-- Records of se_public_permission
-- ----------------------------
INSERT INTO `se_public_permission` VALUES ('1', '/category/public/**', 'user', null, null);

-- ----------------------------
-- Table structure for `se_resc`
-- ----------------------------
DROP TABLE IF EXISTS `se_resc`;
CREATE TABLE `se_resc` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `show_url` varchar(100) DEFAULT NULL,
  `res_string` varchar(300) DEFAULT NULL,
  `priority` int(11) DEFAULT '0',
  `descn` varchar(200) DEFAULT NULL,
  `father_id` int(20) NOT NULL DEFAULT '0',
  `show_menu` int(4) DEFAULT '0',
  `module_id` int(11) DEFAULT '0' COMMENT '资源所属模块',
  PRIMARY KEY (`id`),
  KEY `idx_father_id` (`father_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of se_resc
-- ----------------------------
INSERT INTO `se_resc` VALUES ('1', '系统管理', null, null, '100000', '', '0', '1', '0');
INSERT INTO `se_resc` VALUES ('2', '资源管理', '/resc/rescManager', '/resc:rescManager,getResourceListByRole', '102000', '', '1', '1', '0');
INSERT INTO `se_resc` VALUES ('3', '角色管理', '/role/roleManager', '/role:roleManager,getRescByRole,updateRoleResc,getRoleTree,isRoleExist,isRole,getTags,getTagsOfRole,getTagIdsOfRole', '101000', '角色管理', '1', '1', null);
INSERT INTO `se_resc` VALUES ('4', '用户管理', '/user/userManager', '/user:userManager,viewUsers,selectRoles,setStatus,pullUser', '104000', '', '1', '1', null);
INSERT INTO `se_resc` VALUES ('5', '增加资源', null, '/resc:addResc', '102010', '增加资源权限', '2', '0', '0');
INSERT INTO `se_resc` VALUES ('6', '编辑资源', null, '/resc:updateResc', '102011', '编辑资源权限', '2', '0', '0');
INSERT INTO `se_resc` VALUES ('7', '删除资源', null, '/resc:deleteResc', '102012', '删除资源权限', '2', '0', '0');
INSERT INTO `se_resc` VALUES ('8', '增加角色', null, '/role:addRole', '101010', '增加角色权限', '3', '0', '0');
INSERT INTO `se_resc` VALUES ('9', '修改角色', null, '/role:updateRole', '101011', '修改角色权限', '3', '0', '0');
INSERT INTO `se_resc` VALUES ('10', '删除角色', null, '/role:deleteRole', '101012', '删除角色', '3', '0', '0');
INSERT INTO `se_resc` VALUES ('11', '添加用户', null, '/user:addUserPage,addUser,isUser,getRoleTree', '104012', '', '4', '0', '0');
INSERT INTO `se_resc` VALUES ('12', '重置密码', null, '/user:resetPassword', '104011', '重置密码', '11', '0', '0');
INSERT INTO `se_resc` VALUES ('13', '编辑用户', null, '/user:editUser,isUser,getRoleTree,updateUser', '104013', '', '4', '0', '0');
INSERT INTO `se_resc` VALUES ('14', '添加用户', '/user/addUserPage', '/user:addUserPage,addUser,isUser,getRoleTree', '105000', '', '1', '0', null);
INSERT INTO `se_resc` VALUES ('15', '修改密码', '/user/password', '/user:password,updatePassword', '106000', '修改密码', '1', '1', '0');
INSERT INTO `se_resc` VALUES ('16', '日志管理', null, null, '110000', '', '0', '1', '0');
INSERT INTO `se_resc` VALUES ('17', '操作日志列表', '/log/logManager', '/log:*', '111000', '操作日志列表', '16', '1', null);
INSERT INTO `se_resc` VALUES ('18', '首页通知', '/homePageNotice/view', '/homePageNotice:view,list,add,edit,updateRoleNotice,getNotice,changeStatus,getRelationRole,updateAsNew', '106001', '首页通知', '1', '1', '0');
INSERT INTO `se_resc` VALUES ('19', '二级菜单', null, null, '99', '二级菜单', '16', '1', '0');
INSERT INTO `se_resc` VALUES ('20', '操作日志列表', '/log/logManager', '/log:*', '101', '操作日志列表', '19', '1', '0');
INSERT INTO `se_resc` VALUES ('21', '三级菜单', null, null, '103', '三级菜单', '19', '1', '0');
INSERT INTO `se_resc` VALUES ('22', '操作日志列表', '/log/logManager', '/log:*', '104', '操作日志列表', '21', '1', '0');

-- ----------------------------
-- Table structure for `se_resc_role`
-- ----------------------------
DROP TABLE IF EXISTS `se_resc_role`;
CREATE TABLE `se_resc_role` (
  `resc_id` int(20) NOT NULL DEFAULT '0',
  `role_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`resc_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of se_resc_role
-- ----------------------------
INSERT INTO `se_resc_role` VALUES ('1', '1');
INSERT INTO `se_resc_role` VALUES ('1', '4');
INSERT INTO `se_resc_role` VALUES ('1', '5');
INSERT INTO `se_resc_role` VALUES ('1', '6');
INSERT INTO `se_resc_role` VALUES ('2', '1');
INSERT INTO `se_resc_role` VALUES ('2', '4');
INSERT INTO `se_resc_role` VALUES ('2', '5');
INSERT INTO `se_resc_role` VALUES ('3', '1');
INSERT INTO `se_resc_role` VALUES ('3', '4');
INSERT INTO `se_resc_role` VALUES ('3', '5');
INSERT INTO `se_resc_role` VALUES ('4', '1');
INSERT INTO `se_resc_role` VALUES ('4', '4');
INSERT INTO `se_resc_role` VALUES ('4', '5');
INSERT INTO `se_resc_role` VALUES ('5', '1');
INSERT INTO `se_resc_role` VALUES ('5', '4');
INSERT INTO `se_resc_role` VALUES ('5', '5');
INSERT INTO `se_resc_role` VALUES ('6', '1');
INSERT INTO `se_resc_role` VALUES ('6', '4');
INSERT INTO `se_resc_role` VALUES ('6', '5');
INSERT INTO `se_resc_role` VALUES ('7', '1');
INSERT INTO `se_resc_role` VALUES ('7', '4');
INSERT INTO `se_resc_role` VALUES ('7', '5');
INSERT INTO `se_resc_role` VALUES ('8', '1');
INSERT INTO `se_resc_role` VALUES ('8', '4');
INSERT INTO `se_resc_role` VALUES ('8', '5');
INSERT INTO `se_resc_role` VALUES ('9', '1');
INSERT INTO `se_resc_role` VALUES ('9', '4');
INSERT INTO `se_resc_role` VALUES ('9', '5');
INSERT INTO `se_resc_role` VALUES ('10', '1');
INSERT INTO `se_resc_role` VALUES ('10', '4');
INSERT INTO `se_resc_role` VALUES ('10', '5');
INSERT INTO `se_resc_role` VALUES ('11', '1');
INSERT INTO `se_resc_role` VALUES ('11', '4');
INSERT INTO `se_resc_role` VALUES ('11', '5');
INSERT INTO `se_resc_role` VALUES ('12', '1');
INSERT INTO `se_resc_role` VALUES ('12', '4');
INSERT INTO `se_resc_role` VALUES ('12', '5');
INSERT INTO `se_resc_role` VALUES ('13', '1');
INSERT INTO `se_resc_role` VALUES ('13', '4');
INSERT INTO `se_resc_role` VALUES ('13', '5');
INSERT INTO `se_resc_role` VALUES ('14', '1');
INSERT INTO `se_resc_role` VALUES ('14', '4');
INSERT INTO `se_resc_role` VALUES ('14', '5');
INSERT INTO `se_resc_role` VALUES ('15', '1');
INSERT INTO `se_resc_role` VALUES ('15', '4');
INSERT INTO `se_resc_role` VALUES ('15', '5');
INSERT INTO `se_resc_role` VALUES ('16', '1');
INSERT INTO `se_resc_role` VALUES ('16', '2');
INSERT INTO `se_resc_role` VALUES ('16', '4');
INSERT INTO `se_resc_role` VALUES ('16', '5');
INSERT INTO `se_resc_role` VALUES ('16', '6');
INSERT INTO `se_resc_role` VALUES ('17', '1');
INSERT INTO `se_resc_role` VALUES ('17', '2');
INSERT INTO `se_resc_role` VALUES ('17', '4');
INSERT INTO `se_resc_role` VALUES ('17', '5');
INSERT INTO `se_resc_role` VALUES ('17', '6');
INSERT INTO `se_resc_role` VALUES ('18', '1');
INSERT INTO `se_resc_role` VALUES ('18', '4');
INSERT INTO `se_resc_role` VALUES ('18', '5');
INSERT INTO `se_resc_role` VALUES ('18', '6');
INSERT INTO `se_resc_role` VALUES ('19', '1');
INSERT INTO `se_resc_role` VALUES ('20', '1');
INSERT INTO `se_resc_role` VALUES ('21', '1');
INSERT INTO `se_resc_role` VALUES ('22', '1');

-- ----------------------------
-- Table structure for `se_role`
-- ----------------------------
DROP TABLE IF EXISTS `se_role`;
CREATE TABLE `se_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(200) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_code` (`code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of se_role
-- ----------------------------
INSERT INTO `se_role` VALUES ('1', '超级管理员', '0', 'SUPER_ADMIN');
INSERT INTO `se_role` VALUES ('2', '普通管理员', '1', null);
INSERT INTO `se_role` VALUES ('3', 'BI开发', '2', null);
INSERT INTO `se_role` VALUES ('4', '产品', '1', null);
INSERT INTO `se_role` VALUES ('5', '运营', '1', null);
INSERT INTO `se_role` VALUES ('6', '开发', '1', null);

-- ----------------------------
-- Table structure for `se_role_notice`
-- ----------------------------
DROP TABLE IF EXISTS `se_role_notice`;
CREATE TABLE `se_role_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `notice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_role_id_notice_id` (`role_id`,`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色通知关联表';

-- ----------------------------
-- Records of se_role_notice
-- ----------------------------
INSERT INTO `se_role_notice` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `se_user`
-- ----------------------------
DROP TABLE IF EXISTS `se_user`;
CREATE TABLE `se_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `cn_name` varchar(50) DEFAULT NULL COMMENT '中文名',
  `password` varchar(50) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
  `user_type` int(11) NOT NULL DEFAULT '1' COMMENT '1-ldap, 2-standard',
  `descn` varchar(200) DEFAULT NULL COMMENT '描述',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of se_user
-- ----------------------------
INSERT INTO `se_user` VALUES ('1', 'admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '1', '2', '管理员', null, '2017-10-26 10:13:14');
INSERT INTO `se_user` VALUES ('2', 'test', '测试账号', 'e10adc3949ba59abbe56e057f20f883e', '1', '2', '测试账号', 'test@163.com', '2017-10-25 16:00:12');
INSERT INTO `se_user` VALUES ('3', 'hehe', 'hehe', 'e10adc3949ba59abbe56e057f20f883e', '1', '2', 'hehe', 'hehe@163.com', '2017-10-30 18:01:07');

-- ----------------------------
-- Table structure for `se_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `se_user_role`;
CREATE TABLE `se_user_role` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of se_user_role
-- ----------------------------
INSERT INTO `se_user_role` VALUES ('1', '1');
INSERT INTO `se_user_role` VALUES ('2', '6');
INSERT INTO `se_user_role` VALUES ('3', '6');
