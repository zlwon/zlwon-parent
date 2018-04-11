package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.nosql.entity.SpecAttributeData;
import com.zlwon.rdb.entity.Attribute;

public interface AttributeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attribute record);

    int insertSelective(Attribute record);

    Attribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKey(Attribute record);

    
    /**
     * 根据物性id，得到所有物性属性
     * @return
     */
	List<Attribute> selectAllAttributeBySid(Integer sid);

	/**
	 * 根据物性属性id数组，得到属性信息集合
	 * @param ids
	 * @return
	 */
	List<SpecAttributeData> selectByPrimaryKeys(@Param("ids")Integer[] ids);
}