package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.SpecificationParameter;

public interface SpecificationParameterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpecificationParameter record);

    int insertSelective(SpecificationParameter record);

    SpecificationParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecificationParameter record);

    int updateByPrimaryKey(SpecificationParameter record);

    /**
     * 得到所有物性参数
     * @return
     */
	List<SpecificationParameter> selectAllSpecificationParameter();

	/**
	 * 根据名称和类别得到物性参数信息
	 * @param specificationParameter
	 * @return
	 */
	SpecificationParameter selectByClasstypeAdminName(SpecificationParameter specificationParameter);

	/**
	 * 根据类型，得到所有物性参数信息
	 * @param classType
	 * @return
	 */
	List<SpecificationParameter> selectSpecificationParameterByClasstype(Integer classType);
}