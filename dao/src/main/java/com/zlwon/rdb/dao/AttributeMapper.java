package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.Attribute;

public interface AttributeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attribute record);

    int insertSelective(Attribute record);

    Attribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKey(Attribute record);

    
    /**
     * 得到所有物性属性
     * @return
     */
	List<Attribute> selectAllAttribute();
}