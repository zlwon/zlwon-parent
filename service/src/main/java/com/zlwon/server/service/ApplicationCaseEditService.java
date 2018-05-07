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
	
	/**
	 * 根据用户ID查询编辑案例数量
	 * @param userId
	 * @return
	 */
	int countApplicationCaseEditByUserId(Integer userId);

	/**
	 * 设置编辑案例通过(需要添加到通知表)
	 * @param id
	 * @return
	 */
	int alterApplicationCaseEditSuccess(Integer id);

	/**
	 * 设置编辑案例驳回(需要添加到通知表)
	 * @param id
	 * @return
	 */
	int alterApplicationCaseEditFailed(Integer id,String  content);

	/**
	 * 得到编辑案例驳回信息
	 * @param id
	 * @return
	 */
	String findApplicationCaseEditFailedContent(Integer id);
	
}
