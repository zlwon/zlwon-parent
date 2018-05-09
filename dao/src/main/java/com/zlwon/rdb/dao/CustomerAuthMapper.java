package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.CustomerAuth;

public interface CustomerAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAuth record);

    int insertSelective(CustomerAuth record);

    CustomerAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerAuth record);

    int updateByPrimaryKey(CustomerAuth record);
}