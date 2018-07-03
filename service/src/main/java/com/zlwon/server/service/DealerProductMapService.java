package com.zlwon.server.service;

import com.zlwon.rdb.entity.DealerProductMap;

/**
 * 经销商可供产品Service
 * @author yangy
 *
 */

public interface DealerProductMapService {

	/**
	 * 新增经销商可供产品记录
	 * @param record
	 * @return
	 */
	int insertDealerProductMap(DealerProductMap record);
	
	/**
	 * 根据ID删除经销商可供商品记录
	 * @param id
	 * @return
	 */
	int deleteDealerProductMapById(Integer id);
}
