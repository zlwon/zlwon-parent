package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.SysParam;

public interface SysParamMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
}