package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.specification.PcSearchSpecDealerPageDto;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

/**
 * 物性表经销商报价记录Service
 * @author yangy
 *
 */

public interface DealerdQuotationService {

	/**
     * pc端分页查询物性表经销商报价记录
     * @param form
     * @return
     */
	PageInfo<DealerdQuotationDetailVo> findDealerdQuotationDetail(PcSearchSpecDealerPageDto form);
	
	/**
     * pc端查询物性表经销商报价记录List
     * @param specId
     * @return
     */
    List<DealerdQuotationDetailVo> findDealerdQuotationDetailList(Integer specId);
}
