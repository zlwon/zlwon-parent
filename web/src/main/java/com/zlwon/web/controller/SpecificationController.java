package com.zlwon.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.specification.SpecificationDto;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.specification.SpecificationDetailVo;
import com.zlwon.web.annotations.AuthLogin;

import io.swagger.annotations.Api;

/**
 * 物性api
 * @author yuand
 *
 */
@Api
@AuthLogin
@RestController
@RequestMapping("specification")
public class SpecificationController {

	@Autowired
	private SpecificationService specificationService;
	
	/**
	 * 得到所有物性，分页查找,可模糊搜索
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllSpecification",method=RequestMethod.GET)
	public   ResultPage  queryAllSpecification(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize,String  message){
		PageInfo<SpecificationDetailVo>  info = specificationService.findAllSpecificationMake(pageIndex,pageSize,message);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 添加物性
	 * @param specification
	 * @return
	 */
	@RequestMapping(value="addSpecification",method=RequestMethod.POST)
	public  ResultData  addSpecification(SpecificationDto  specification){
		//添加物性，需要判断规格名称是否重复
		specificationService.saveSpecificationMake(specification);
		return  ResultData.ok();
	}
	
	
	/**
	 * 保存修改后的物性信息，根据物性id
	 * @param specification
	 * @return
	 */
	@RequestMapping(value="editSpecificationById",method=RequestMethod.POST)
	public  ResultData  editSpecificationById(Specification  specification){
		//执行保存更新后的物性信息，需要先判断该物性是否存在，然后在判断要修改后的规格名称是否存在
		specificationService.alterSpecificationById(specification);
		return  ResultData.ok();
	}
	
	
	/**
	 * 根据物性id，删除指定的物性信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelSpecificationById",method=RequestMethod.GET)
	public  ResultData  cancelSpecificationById(Integer  id){
		specificationService.removeSpecificationById(id);
		return  ResultData.ok();
	}
	
	
	/**
	 * 根据物性id，得到物性详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="querySpecificationById",method=RequestMethod.GET)
	public  ResultData  querySpecificationById(Integer  id){
		Specification record = specificationService.findSpecificationById(id);
		return  ResultData.one(record);
	}
	
	
	/**
	 * 根据生产商id，得到所有物性，不分页
	 * @param id 生产商id
	 * @return
	 */
	@RequestMapping(value="querySpecificationByMid",method=RequestMethod.GET)
	public  ResultData  querySpecificationByMid(Integer  id){
		List<Specification>  list = specificationService.findSpecificationByMid(id);
		return  ResultData.one(list);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
