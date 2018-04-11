package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Attribute;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AttributeService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物性属性api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("attribute")
@AuthLogin
public class AttributeController {

	@Autowired
	private AttributeService attributeService;
	
	/**
	 * 根据物性id，得到所有物性属性，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param sid 物性id
	 * @return
	 */
	@RequestMapping(value="queryAllAttribute",method=RequestMethod.GET)
	public   ResultPage  queryAllAttribute(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,Integer  sid){
		PageInfo<Attribute> info = attributeService.findAllAttribute(pageIndex,pageSize,sid);
		return  ResultPage.list(info);
	}
	
	/**
	 * 得到物性属性信息，根据属性id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryAttributeById",method=RequestMethod.GET)
	public  ResultData   queryAttributeById(Integer  id){
		Attribute  attribute = attributeService.findAttributeById(id);
		return  ResultData.one(attribute);
	}
	
	/**
	 * 添加物性属性
	 * @param attribute
	 * @return
	 */
	@RequestMapping(value="addAttribute",method=RequestMethod.POST)
	public   ResultData  addAttribute(Attribute   attribute){
		if(StringUtils.isBlank(attribute.getClassname()) || StringUtils.isBlank(attribute.getName())){
			return  ResultData.error(StatusCode.INVALID_PARAM);
		}
		//添加物性属性，任何字段都不判断重复
		attributeService.saveAttribute(attribute);
		return   ResultData.ok();
	}
	
	
	/**
	 * 根据属性id，保存修改后的属性信息
	 * @param attribute
	 * @return
	 */
	@RequestMapping(value="editAttributeById",method=RequestMethod.POST)
	public  ResultData  editAttributeById(Attribute   attribute){
		//执行更新操作，需要先判断属性是否存在
		attributeService.alterAttributeById(attribute);
		return  ResultData.ok();
	}
	
	/**
	 * 根据属性id，删除属性信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelAttributeById",method=RequestMethod.GET)
	public  ResultData  cancelAttributeById(Integer  id){
		//执行删除操作
		attributeService.removeAttributeById(id);
		return  ResultData.ok();
	}
	
}
