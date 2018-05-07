package com.zlwon.server.service;

import java.util.List;

import com.zlwon.rdb.entity.DealerdQuotation;

/**
 * 物性表材料报价单Service
 * @author yangy
 *
 */

public interface DealerdQuotationService {

	/**
	 * 新增材料报价单
	 * @param record
	 * @return
	 */
	int insertDealerdQuotation(DealerdQuotation record);
	
	/**
	 * 根据id删除材料报价单
	 * @param id
	 * @return
	 */
	int deleteDealerdQuotationById(Integer id);
}
