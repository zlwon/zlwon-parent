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
	
	/**
	 * 根据类型和父ID，得到所有物性参数信息
	 * @param classType
	 * @param parentId
	 * @return
	 */
	List<SpecificationParameter> findSpecificationParameterByClasstypeParent(Integer classType,Integer parentId);
	
	
	/**
	 * 根据类型，得到所有物性参数，不分页,可根据名称模糊查询
	 * @param classType 类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
	 * @param key 模糊查询关键字
	 * @return
	 */
	List<SpecificationParameter> findByClasstypeAndKeySelective(Integer classType,String  key);
	
	/**
	 * 根据ID字符串查询所在其内的物性案例参数
	 * @param idStr
	 * @return
	 */
	List<SpecificationParameter> findSpecificationParameterByIdStr(String idStr);

	
	/**
	 * 根据应用行业id，得到所有应用市场数据，不分页，可根据名称模糊查询
	 * @param id
	 * @param key  只查应用行业下的应用市场名称
	 * @return
	 */
	List<SpecificationParameter> findParamByIndustryId(Integer id, String key);

	/**
	 * 根据生产商id，得到该生产商的所有商标，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	PageInfo findByCustomerIdPage(Integer pageIndex, Integer pageSize, Integer cid);

	/**
	 * 根据生产商id，得到该生产商的所有商标，不分页
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	List<SpecificationParameter> findByCustomerId(Integer cid);

	/**
	 * 得到所有安规认证信息(阻燃等级，食品接触等),不分页
	 * @return
	 */
	List<SpecificationParameter> findAllSafety();

	
	/**
	 * 根据安规认证标签id，得到标签下所有信息,不分页
	 * @param id 安规认证标签id，其实就是阻燃等级(食品接触等)id
	 * @return
	 */
	List<SpecificationParameter> findBySafetyId(Integer id);

}
