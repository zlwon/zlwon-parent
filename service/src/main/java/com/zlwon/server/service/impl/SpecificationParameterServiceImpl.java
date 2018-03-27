package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.SpecificationParameterMapper;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.server.service.SpecificationParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物性参数service
 * @author yuand
 *
 */
@Service
public class SpecificationParameterServiceImpl implements SpecificationParameterService {

	@Autowired
	private  SpecificationParameterMapper    specificationParameterMapper;
	
	@Override
	public PageInfo<SpecificationParameter> findAllSpecificationParameter(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<SpecificationParameter>  list = specificationParameterMapper.selectAllSpecificationParameter();
		return new  PageInfo<SpecificationParameter>(list);
	}

	/**
	 * 根据物性参数id，得到物性参数信息
	 */
	@Override
	public SpecificationParameter querySpecificationParameterById(Integer id) {
		return specificationParameterMapper.selectByPrimaryKey(id);
	}

	/**
	 * 执行添加，需要先判断数据(类型和名称)是否已存在
	 */
	@Override
	public int saveSpecificationParameter(SpecificationParameter specificationParameter) {
		//根据名称和类别找到数据，如果存在，抛异常
		SpecificationParameter  record = specificationParameterMapper.selectByClasstypeAdminName(specificationParameter);
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		//执行添加操作
		return  specificationParameterMapper.insert(specificationParameter);
	}

	/**
	 * 保存修改后的物性参数
	 */
	@Override
	public int alterSpecificationParameterById(SpecificationParameter specificationParameter) {
		//查看该数据是否还存在
		SpecificationParameter record = specificationParameterMapper.selectByPrimaryKey(specificationParameter.getId());
		if(record == null ){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//查看要修改后的数据是否存在
		record = specificationParameterMapper.selectByClasstypeAdminName(specificationParameter);
		if(record != null && record.getId() != specificationParameter.getId()){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		
		return specificationParameterMapper.updateByPrimaryKey(specificationParameter);
	}

	/**
	 * 根据物性参数id，删除物性参数
	 */
	@Override
	public int removeSpecificationParameterById(Integer id) {
		return specificationParameterMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据类型，得到所有物性参数，分页查询
	 */
	@Override
	public PageInfo<SpecificationParameter> findSpecificationParameterByClasstype(Integer pageIndex, Integer pageSize,
			Integer classType) {
		PageHelper.startPage(pageIndex, pageSize);
		List<SpecificationParameter>  list = specificationParameterMapper.selectSpecificationParameterByClasstype(classType);
		return new  PageInfo<>(list);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
