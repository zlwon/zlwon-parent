package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.server.service.IntegrationDeatilMapService;

/**
 * 积分异动明细表serviceImpl
 * @author yangy
 *
 */

@Service
public class IntegrationDeatilMapServiceImpl implements IntegrationDeatilMapService {

	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	
	/**
	 * 新增积分异动明细流水记录
	 * @param record
	 * @return
	 */
	@Override
	public int insertIntegrationDeatilMap(IntegrationDeatilMap record){
		
		int count = integrationDeatilMapMapper.insertSelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
}
