package com.zlwon.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.SpecificationParameterService;
import com.zlwon.vo.specificationParameter.SafetyParameterVo;
import com.zlwon.web.annotations.AuthLogin;

import io.swagger.annotations.Api;

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
	public  ResultPage  queryAllSpecificationParameter(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
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
	public  ResultPage  querySpecificationParameterByClasstype(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,Integer  classType){
		PageInfo<SpecificationParameter>  info = specificationParameterService.findSpecificationParameterByClasstype(pageIndex,pageSize,classType);
		return  ResultPage.list(info);
	}
	
	/**
	 * 根据类型，得到所有物性参数，不分页,可根据名称模糊查询
	 * @param pageIndex
	 * @param pageSize
	 * @param classType 类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
	 * @param key 模糊查询关键字
	 * @return
	 */
	@RequestMapping(value="queryByClasstype",method=RequestMethod.GET)
	public  ResultData  queryByClasstype(Integer  classType,String  key){
		List<SpecificationParameter>  info = specificationParameterService.findByClasstypeAndKeySelective(classType,key);
		return  ResultData.one(info);
	}
	
	
	/**
	 * 根据应用行业id，得到所有应用市场数据，不分页，可根据名称模糊查询
	 * @param id
	 * @param key  只查应用行业下的应用市场名称
	 * @return
	 */
	@RequestMapping(value="queryParamByIndustryId",method=RequestMethod.GET)
	public  ResultData  queryParamByIndustryId(Integer   id,String  key){
		List<SpecificationParameter>  list = specificationParameterService.findParamByIndustryId(id,key);
		return  ResultData.one(list);
	}
	
	
	/**
	 * 根据生产商id，得到该生产商的所有商标，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	@RequestMapping(value="queryByCustomerIdPage",method=RequestMethod.GET)
	public  ResultPage  queryByCustomerIdPage(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,Integer   cid){
		PageInfo  info = specificationParameterService.findByCustomerIdPage(pageIndex,pageSize,cid);
		return   ResultPage.list(info);
	}
	
	/**
	 * 根据生产商id，得到该生产商的所有商标，不分页
	 * @param pageIndex
	 * @param pageSize
	 * @param cid  生产商id
	 * @return
	 */
	@RequestMapping(value="queryByCustomerId",method=RequestMethod.GET)
	public  ResultData  queryByCustomerId(Integer   cid){
		List<SpecificationParameter>  list = specificationParameterService.findByCustomerId(cid);
		return   ResultData.one(list);
	}
	
	
	/**
	 * 得到所有安规认证，包括分类和分类的子类，不分页
	 * @return
	 */
	@RequestMapping(value="queryAllSafety",method=RequestMethod.GET)
	public  ResultData  queryAllSafety(){
		List<SafetyParameterVo>  list = specificationParameterService.findAllSafety();
		return   ResultData.one(list);
	}
	
	
	
	
	//下面俩接口改为一个接口了queryAllSafety
	/**
	 * 得到所有安规认证信息(阻燃等级，食品接触等),不分页
	 * @return
	 */
//	@RequestMapping(value="queryAllSafety",method=RequestMethod.GET)
//	public  ResultData  queryAllSafety(){
//		List<SpecificationParameter>  list = specificationParameterService.findAllSafety();
//		return   ResultData.one(list);
//	}
	/**
	 * 根据安规认证标签id，得到标签下所有信息,不分页
	 * @param id 安规认证标签id，其实就是阻燃等级(食品接触等)id
	 * @return
	 */
//	@RequestMapping(value="queryBySafetyId",method=RequestMethod.GET)
//	public  ResultData  queryBySafetyId(Integer   id){
//		return  ResultData.one(specificationParameterService.findBySafetyId(id));
//	}
}
