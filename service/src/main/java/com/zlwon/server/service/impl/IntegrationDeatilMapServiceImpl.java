package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.integration.QueryMyIntegrationDeatilMapPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.server.service.IntegrationDeatilMapService;
import com.zlwon.vo.pc.integration.IntegrationDetailMapVo;

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
	
	/**
	 * 分页查询当前用户异动明细流水记录
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<IntegrationDetailMapVo> findMyIntegrationDeatilMapByPage(QueryMyIntegrationDeatilMapPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<IntegrationDetailMapVo> list = integrationDeatilMapMapper.selectIntegrationDeatilMapByUserId(form.getUserId());
		PageInfo<IntegrationDetailMapVo> result = new PageInfo<IntegrationDetailMapVo>(list);
		return result;
	}
}
