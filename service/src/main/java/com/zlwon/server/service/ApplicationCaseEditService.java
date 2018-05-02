package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;

/**
 * 案例编辑service
 * @author yuand
 *
 */
public interface ApplicationCaseEditService {

	/**
	 * 得到所有编辑案例信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo findAllApplicationCaseEdit(Integer pageIndex, Integer pageSize);
	
}
