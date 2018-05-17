package com.zlwon.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.CaseEditMapper;
import com.zlwon.rdb.dao.CharacteristicMapper;
import com.zlwon.rdb.dao.CustomerAuthMapper;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.server.service.IndexService;

/**
 * web首页service
 * @author yuand
 *
 */
@Service
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	private   CaseEditMapper   caseEditMapper;
	@Autowired
	private   DealerdQuotationMapper  dealerdQuotationMapper;
	@Autowired
	private   CustomerAuthMapper  customerAuthMapper;

	/**
	 * web首页未审核的个数统计(label)，
	 * @return
	 */
	@Override
	public Object findIndexNotExamineNumber() {
		Map<String,Integer>  map = new  HashMap<>();
		int caseEditNumber = caseEditMapper.selectNotExamineNumber();
		int dealNumber = dealerdQuotationMapper.selectNotExamineNumber();
		int authNumber = customerAuthMapper.selectNotExamineNumber();
		map.put("caseEditNumber", caseEditNumber);
		map.put("dealNumber", dealNumber);
		map.put("authNumber", authNumber);
		return map;
	}

}
