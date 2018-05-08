#用户表添加用户申请类型
ALTER TABLE `zl_customer` ADD COLUMN role_apply INT(1)  DEFAULT -1   COMMENT '用户申请成为的类型：-1不申请1认证用户6企业用户，申请成功后，需要把role修改为申请后的类型，该字段置为-1'  AFTER `role_apply`

#新建企业信息表
CREATE TABLE `zl_company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增长',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '企业名称',
  `cid` INT(11) DEFAULT '0' COMMENT '企业简称(用户表的生产商)id，如果是企业简称就是0',
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
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='企业表(包含简称和全称)'