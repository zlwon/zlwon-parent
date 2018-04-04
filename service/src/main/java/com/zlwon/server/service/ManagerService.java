package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Sysadmin;

public interface ManagerService {

	/**
	 * 管理员登录
	 * @param usernam  账号
	 * @param password   密码
	 * @return
	 */
	Sysadmin  login(String  usernam,String  password);

	/**
	 * 添加管理员，需要判断管理员账号是否重复
	 * @param sysadmin
	 * @return
	 */
	int saveManager(Sysadmin sysadmin);

	/**
	 * 修改管理员信息
	 * @param sysadmin
	 * @return
	 */
	int alterManager(Sysadmin sysadmin);

	/**
	 * 得到所有正常的管理员，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<Sysadmin> findAllManager(Integer pageIndex, Integer pageSize);

	/**
	 * 根据管理员id，删除管理员
	 * @param id
	 * @return
	 */
	int removeManagerById(Integer id);

	/**
	 * 根据管理员id，得到管理员详情
	 * @param id
	 * @return
	 */
	Sysadmin findManagerById(Integer id);
	
	
	
}
