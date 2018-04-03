package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.pc.specification.PcSearchSpecDealerPageDto;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

public interface DealerdQuotationMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(DealerdQuotation record);

    int insertSelective(DealerdQuotation record);

    DealerdQuotation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealerdQuotation record);

    int updateByPrimaryKey(DealerdQuotation record);
    
    /**
     * pc端分页查询物性表经销商报价记录
     * @param form
     * @return
     */
    List<DealerdQuotationDetailVo> selectDealerdQuotationDetail(PcSearchSpecDealerPageDto form);
}