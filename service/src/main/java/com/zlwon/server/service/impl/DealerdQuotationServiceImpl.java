package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.specification.PcSearchSpecDealerPageDto;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

/**
 * 物性表经销商报价记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class DealerdQuotationServiceImpl implements DealerdQuotationService {

	@Autowired
	private DealerdQuotationMapper dealerdQuotationMapper;
	
	/**
     * pc端分页查询物性表经销商报价记录
     * @param form
     * @return
     */
	@Override
	public PageInfo<DealerdQuotationDetailVo> findDealerdQuotationDetail(PcSearchSpecDealerPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectDealerdQuotationDetail(form);
		PageInfo<DealerdQuotationDetailVo> result = new PageInfo<>(list);
		return result;
	}
	
	/**
     * pc端查询物性表经销商报价记录List
     * @param specId
     * @return
     */
	@Override
    public List<DealerdQuotationDetailVo> findDealerdQuotationDetailList(Integer specId){
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectDealerdQuotationDetailList(specId);
		return list;
    }
}
