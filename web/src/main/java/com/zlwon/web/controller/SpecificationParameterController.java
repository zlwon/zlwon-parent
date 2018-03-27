package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.SpecificationParameterService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物性参数api
 * @author yuand
 *
 */
@RestController
@Api
@AuthLogin
@RequestMapping("specificationParameter")
public class SpecificationParameterController {

	@Autowired
	private SpecificationParameterService specificationParameterService;
	
	/**
	 * 得到所有物性参数，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllSpecificationParameter",method=RequestMethod.GET)
	public  ResultPage  queryAllSpecificationParameter(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		PageInfo<SpecificationParameter> info = specificationParameterService.findAllSpecificationParameter(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 根据物性参数id，得到物性参数信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="querySpecificationParameterById",method=RequestMethod.GET)
	public  ResultData  querySpecificationParameterById(Integer  id){
		SpecificationParameter  specificationParameter = specificationParameterService.querySpecificationParameterById(id);
		return  ResultData.one(specificationParameter);
	}
	
	
	/**
	 * 新增物性参数
	 * @param specificationParameter
	 * @return
	 */
	@RequestMapping(value="addSpecificationParameter",method=RequestMethod.POST)
	public  ResultData  addSpecificationParameter(SpecificationParameter  specificationParameter){
		//执行添加，需要先判断数据(类型和名称)是否已存在
		specificationParameterService.saveSpecificationParameter(specificationParameter);
		return  ResultData.ok();
	}
	
	
	/**
	 * 保存修改后的物性参数
	 * @param specificationParameter
	 * @return
	 */
	@RequestMapping(value="editSpecificationParameterById",method=RequestMethod.POST)
	public  ResultData  editSpecificationParameterById(SpecificationParameter  specificationParameter){
		//执行更新操作，需要先判断该物性参数数据是否存在，然后在判断修改后的数据是否存在
		specificationParameterService.alterSpecificationParameterById(specificationParameter);
		return   ResultData.ok();
	}
	
	/**
	 * 根据物性参数id，删除物性参数
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelSpecificationParameterById",method=RequestMethod.GET)
	public  ResultData cancelSpecificationParameterById(Integer  id){
		specificationParameterService.removeSpecificationParameterById(id);
		return  ResultData.ok();
	}
	
	
	/**
	 * 根据类型，得到所有物性参数，分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param classType
	 * @return
	 */
	@RequestMapping(value="querySpecificationParameterByClasstype",method=RequestMethod.GET)
	public  ResultPage  querySpecificationParameterByClasstype(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize,Integer  classType){
		PageInfo<SpecificationParameter>  info = specificationParameterService.findSpecificationParameterByClasstype(pageIndex,pageSize,classType);
		return  ResultPage.list(info);
	}
	
	
	
	
	
	
	
}
