package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.DealerdQuotation;

public interface DealerdQuotationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DealerdQuotation record);

    int insertSelective(DealerdQuotation record);

    DealerdQuotation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealerdQuotation record);

    int updateByPrimaryKey(DealerdQuotation record);
}