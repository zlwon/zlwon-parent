package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.ProcessingAdviceClass;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ProcessingAdviceClassService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物性表加工建议主题api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("processingAdviceClass")
@AuthLogin
public class ProcessingAdviceClassController {

	@Autowired
	private ProcessingAdviceClassService processingAdviceClassService;
	
	/**
	 * 得到所有加工建议主题，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllProcessingAdviceClass",method=RequestMethod.GET)
	public  ResultPage   queryAllProcessingAdviceClass(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		PageInfo<ProcessingAdviceClass> info = processingAdviceClassService.findAllProcessingAdviceClass(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	/**
	 * 得到所有加工建议主题，不分页
	 * @return
	 */
	@RequestMapping(value="queryProcessingAdviceClassList",method=RequestMethod.GET)
	public  ResultData  queryProcessingAdviceClassList(){
		List<ProcessingAdviceClass>  list = processingAdviceClassService.findProcessingAdviceClassList();
		return   ResultData.one(list);
	}
	
	
	/**
	 * 根据加工建议主题id，得到加工建议主题信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryProcessingAdviceClassById",method=RequestMethod.GET)
	public  ResultData  queryProcessingAdviceClassById(Integer  id){
		ProcessingAdviceClass processingAdviceClass = processingAdviceClassService.findProcessingAdviceClassById(id);
		return  ResultData.one(processingAdviceClass);
	}
	
	
	/**
	 * 添加加工建议主题
	 * @param processingAdviceClass
	 * @return
	 */
	@RequestMapping(value="addProcessingAdviceClass",method=RequestMethod.POST)
	public   ResultData  addProcessingAdviceClass(ProcessingAdviceClass  processingAdviceClass){
		//执行添加操作，需要先判断主题是否存在(主题唯一)
		processingAdviceClassService.saveProcessingAdviceClass(processingAdviceClass);
		return  ResultData.ok();
	}
	
	/**
	 * 保存修改后的加工建议主题
	 * @param processingAdviceClass
	 * @return
	 */
	@RequestMapping(value="editProcessingAdviceClassById",method=RequestMethod.POST)
	public  ResultData editProcessingAdviceClassById(ProcessingAdviceClass  processingAdviceClass){
		//执行更新操作，需要先判断主题信息是否存在，然后在判断主题是否存在
		processingAdviceClassService.alterProcessingAdviceClassById(processingAdviceClass);
		return  ResultData.ok();
	}
	
	/**
	 * 根据主题信息id，删除主题信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelProcessingAdviceClassById",method=RequestMethod.GET)
	public  ResultData  cancelProcessingAdviceClassById(Integer  id){
		processingAdviceClassService.removeProcessingAdviceClassById(id);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
