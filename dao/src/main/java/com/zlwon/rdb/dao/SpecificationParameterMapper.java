package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	/**
	 * 根据类型和名称，得到参数信息，
	 * @param type 类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
	 * @param name 名称
	 * @return
	 */
	SpecificationParameter selectByTypeAndName(@Param("type")Integer type, @Param("name") String name);
	
	
	/**
	 * 根据类型，得到所有物性参数，不分页,可根据名称模糊查询
	 * @param classType 类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
	 * @param key 模糊查询关键字,传null不查
	 * @return
	 */
	List<SpecificationParameter> selectByClasstypeAndKeySelective(@Param("classType")Integer classType,@Param("key")String  key);
	
	/**
	 * 根据ID字符串查询所在其内的物性案例参数
	 * @param idStr
	 * @return
	 */
	List<SpecificationParameter> selectSpecificationParameterByIdStr(String idStr);
}