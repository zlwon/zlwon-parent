package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlwon.rdb.dao.DealerProductMapMapper;
import com.zlwon.rdb.entity.DealerProductMap;
import com.zlwon.server.service.DealerProductMapService;

/**
 * 经销商可供产品ServiceImpl
 * @author yangy
 *
 */

@Service
public class DealerProductMapServiceImpl implements DealerProductMapService {

	@Autowired
	private DealerProductMapMapper dealerProductMapMapper;
	
	/**
	 * 新增经销商可供产品记录
	 * @param record
	 * @return
	 */
	@Transactional
	public int insertDealerProductMap(DealerProductMap record){
		
		
		
		return 0;
	}
	
	/**
	 * 根据ID删除经销商可供商品记录
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDealerProductMapById(Integer id){
		return 0;
	}
}
