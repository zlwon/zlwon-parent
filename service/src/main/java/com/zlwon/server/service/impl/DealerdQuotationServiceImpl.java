package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.server.service.DealerdQuotationService;

/**
 * 物性表材料报价单ServiceImpl
 * @author yangy
 *
 */

@Service
public class DealerdQuotationServiceImpl implements DealerdQuotationService {

	@Autowired
	private DealerdQuotationMapper dealerdQuotationMapper;
	
	/**
	 * 新增材料报价单
	 * @param record
	 * @return
	 */
	@Override
	public int insertDealerdQuotation(DealerdQuotation record){
		
		//判断物性规格和色号是否已经存在
		DealerdQuotation validExist = dealerdQuotationMapper.selectDealerdQuotationBySpecAndColor(record.getSid(),record.getColor());
		if(validExist != null){
			throw new CommonException(StatusCode.DEALERDQUOTATION_IS_EXIST);
		}
		
		//新增材料报价单
		int count = dealerdQuotationMapper.insertSelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据id删除材料报价单
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDealerdQuotationById(Integer id){
		
		int count = dealerdQuotationMapper.deleteByPrimaryKey(id);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
}
