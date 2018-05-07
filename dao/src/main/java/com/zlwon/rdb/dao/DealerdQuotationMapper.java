package com.zlwon.rdb.dao;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.DealerdQuotation;

/**
 * 物性表材料报价记录Mapper
 * @author yangy
 *
 */

public interface DealerdQuotationMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(DealerdQuotation record);

    int insertSelective(DealerdQuotation record);

    DealerdQuotation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealerdQuotation record);

    int updateByPrimaryKey(DealerdQuotation record);
    
    /**
     * 根据物性规格和色号查询材料报价单（未驳回）
     * @param specId
     * @param color
     * @return
     */
    DealerdQuotation selectDealerdQuotationBySpecAndColor(@Param("specId") Integer specId,@Param("color") String color);
}