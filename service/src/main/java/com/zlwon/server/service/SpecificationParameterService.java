package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.SpecificationParameter;

/**
 * 物性参数
 * @author yuand
 *
 */
public interface SpecificationParameterService {

	/**
	 * 得到所有物性参数，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<SpecificationParameter> findAllSpecificationParameter(Integer pageIndex, Integer pageSize);

	/**
	 * 根据物性参数id，得到物性参数信息
	 * @param id
	 * @return
	 */
	SpecificationParameter querySpecificationParameterById(Integer id);

	
	/**
	 * 执行添加，需要先判断数据(类型和名称)是否已存在
	 * @param specificationParameter
	 * @return
	 */
	int saveSpecificationParameter(SpecificationParameter specificationParameter);

	/**
	 * 保存修改后的物性参数
	 * @param specificationParameter
	 * @return
	 */
	int alterSpecificationParameterById(SpecificationParameter specificationParameter);

	
	/**
	 * 根据物性参数id，删除物性参数
	 * @param id
	 * @return
	 */
	int removeSpecificationParameterById(Integer id);

	/**
	 * 根据类型，得到所有物性参数，分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param classType
	 * @return
	 */
	PageInfo<SpecificationParameter> findSpecificationParameterByClasstype(Integer pageIndex, Integer pageSize,
			Integer classType);

	/**
	 * 根据类型，得到所有物性参数信息
	 * @param classType
	 * @return
	 */
	List<SpecificationParameter> findSpecificationParameterByClasstype(Integer classType);
}
