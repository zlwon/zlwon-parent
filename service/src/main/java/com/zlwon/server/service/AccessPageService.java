package com.zlwon.server.service;

import com.zlwon.rdb.entity.AccessPage;

/**
 * 访问记录统计service
 * @author yangy
 *
 */

public interface AccessPageService {

	/**
	 * 编辑访问记录统计
	 * @param record
	 * @return
	 */
	int updateAccessPage(AccessPage record);
	
	/**
     * 根据类型更新数量
     * @param type
     * @return
     */
    int updateCountByType(Integer type);
}
