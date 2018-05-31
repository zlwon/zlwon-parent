package com.zlwon.server.service;

import com.zlwon.rdb.entity.IntegrationDeatilMap;

/**
 * 积分异动明细表service
 * @author yangy
 *
 */

public interface IntegrationDeatilMapService {

	/**
	 * 新增积分异动明细流水记录
	 * @param record
	 * @return
	 */
	int insertIntegrationDeatilMap(IntegrationDeatilMap record);
}
