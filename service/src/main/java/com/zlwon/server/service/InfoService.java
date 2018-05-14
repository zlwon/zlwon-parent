package com.zlwon.server.service;

import com.zlwon.rdb.entity.Info;

/**
 * 资讯service
 * @author yangy
 *
 */

public interface InfoService {

	/**
	 * 新增资讯记录
	 * @param record
	 * @return
	 */
	int insertInfo(Info record);
	
	/**
	 * 根据资讯id删除资讯记录
	 * @param id
	 * @return
	 */
	int deleteInfoById(Integer id);
	
	/**
	 * 根据资讯id更新资讯热门状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updateInfoHotStatusById(Integer id);
}
