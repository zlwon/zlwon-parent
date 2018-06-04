package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.integration.QueryMyIntegrationDeatilMapPageDto;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.vo.pc.integration.IntegrationDetailMapVo;

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
	
	/**
	 * 分页查询当前用户异动明细流水记录
	 * @param form
	 * @return
	 */
	PageInfo<IntegrationDetailMapVo> findMyIntegrationDeatilMapByPage(QueryMyIntegrationDeatilMapPageDto form);
}
