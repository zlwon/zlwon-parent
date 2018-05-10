#用户表添加用户申请类型
ALTER TABLE `zl_customer` ADD COLUMN role_apply INT(1)  DEFAULT -1   COMMENT '用户申请成为的类型：-1不申请1认证用户6企业用户，申请成功后，需要把role修改为申请后的类型，该字段置为-1'  AFTER `role_apply`

#新建企业信息表
CREATE TABLE `zl_company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增长',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '企业名称',
  `parent_id` INT(11) DEFAULT '0' COMMENT '企业简称(用户表生产商或该表企业简称)id，如果该数据是企业简称就是0',
  `status` TINYINT(4) DEFAULT '0' COMMENT '企业全称关联简称所对应的表0对应用户表的生产商1对应该表的企业简称',
  `charter` VARCHAR(150) DEFAULT NULL COMMENT '企业营业执照',
  `intro` VARCHAR(150) DEFAULT NULL COMMENT '企业介绍',
  `link_person` VARCHAR(10) DEFAULT NULL COMMENT '企业联系人',
  `link_email` VARCHAR(25) DEFAULT NULL COMMENT '企业联系人邮箱',
  `link_tel` VARCHAR(25) DEFAULT NULL COMMENT '企业联系人电话',
  `uid` INT(11) DEFAULT NULL COMMENT '提交用户id',
  `examine` TINYINT(4) DEFAULT '0' COMMENT '用户创建数据审核结果，0未审核，1审核通过，2驳回',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='企业表(包含简称和全称)'

#新建用户认证申请表
CREATE TABLE `zl_customer_auth` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户认证信息id',
  `uid` INT(11) NOT NULL COMMENT '用户id',
  `shortCompany_id` INT(11) DEFAULT NULL COMMENT '企业简称id',
  `fullCompany_id` INT(11) DEFAULT NULL COMMENT '企业全称id',
  `type` TINYINT(4) DEFAULT NULL COMMENT '用户认证类型1:个人认证6企业认证',
  `nickname` VARCHAR(25) DEFAULT NULL COMMENT '昵称',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '注册邮箱',
  `occupation` VARCHAR(255) DEFAULT NULL COMMENT '职业经历',
  `label` VARCHAR(255) DEFAULT NULL COMMENT '业务标签',
  `bcard` VARCHAR(200) DEFAULT NULL COMMENT '名片路径',
  `myinfo` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
  `status` TINYINT(4) DEFAULT '0' COMMENT '用户认证审核结果，0未审核，1审核通过，2驳回',
  `create_time` DATETIME DEFAULT NULL COMMENT '申请时间',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户认证申请记录表'