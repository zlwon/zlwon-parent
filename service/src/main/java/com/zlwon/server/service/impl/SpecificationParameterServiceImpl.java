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

	/**
	 * 根据类型，得到所有物性参数信息
	 * @param classType
	 * @return
	 */
	@Override
	public List<SpecificationParameter> findSpecificationParameterByClasstype(Integer classType){
		List<SpecificationParameter> list = specificationParameterMapper.selectSpecificationParameterByClasstype(classType);
		return list;
	}
	
	/**
	 * 根据类型和父ID，得到所有物性参数信息
	 * @param classType
	 * @param parentId
	 * @return
	 */
	@Override
	public List<SpecificationParameter> findSpecificationParameterByClasstypeParent(Integer classType,Integer parentId){
		List<SpecificationParameter> list = specificationParameterMapper.selectSpecificationParameterByClasstypeParent(classType,parentId);
		return list;
	}
	
	
	/**
	 * 根据类型，得到所有物性参数，不分页,可根据名称模糊查询
	 * @param classType 类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
	 * @param key 模糊查询关键字
	 * @return
	 */
	@Override
	public List<SpecificationParameter> findByClasstypeAndKeySelective(Integer classType,String  key){
		List<SpecificationParameter> list = specificationParameterMapper.selectByClasstypeAndKeySelective(classType,key);
		return list;
	}

	/**
	 * 根据ID字符串查询所在其内的物性案例参数
	 * @param idStr
	 * @return
	 */
	@Override
	public List<SpecificationParameter> findSpecificationParameterByIdStr(String idStr){
		List<SpecificationParameter> list = specificationParameterMapper.selectSpecificationParameterByIdStr(idStr);
		return list;
	}

	
	/**
	 * 根据应用行业id，得到所有应用市场数据，不分页，可根据名称模糊查询
	 * @param id
	 * @param key  只查应用行业下的应用市场名称
	 * @return
	 */
	public List<SpecificationParameter> findParamByIndustryId(Integer id, String key) {
		return specificationParameterMapper.selectParamByIndustryId(id,key);
	}

	
	/**
	 * 根据生产商id，得到该生产商的所有商标，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	public PageInfo findByCustomerIdPage(Integer pageIndex, Integer pageSize, Integer cid) {
		PageHelper.startPage(pageIndex, pageSize);
		List<SpecificationParameter>  list = specificationParameterMapper.selectByCustomerId(cid);
		return new  PageInfo<>(list);
	}

	/**
	 * 根据生产商id，得到该生产商的所有商标，不分页
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	public List<SpecificationParameter> findByCustomerId(Integer cid) {
		return specificationParameterMapper.selectByCustomerId(cid);
	}

	/**
	 * 得到所有安规认证信息(阻燃等级，食品接触等),不分页
	 * @return
	 */
	public List<SpecificationParameter> findAllSafety() {
		return specificationParameterMapper.selectAllSafety();
	}

	
	/**
	 * 根据安规认证标签id，得到标签下所有信息,不分页
	 * @param id 安规认证标签id，其实就是阻燃等级(食品接触等)id
	 * @return
	 */
	public List<SpecificationParameter> findBySafetyId(Integer id) {
		return specificationParameterMapper.selectBySafetyId(id);
	}
	
	
}
