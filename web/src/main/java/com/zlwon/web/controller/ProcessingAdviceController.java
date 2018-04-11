package com.zlwon.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultList;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ProcessingAdviceService;
import com.zlwon.vo.processingAdvice.ProcessingAdviceVo;
import com.zlwon.web.annotations.AuthLogin;

import io.swagger.annotations.Api;

/**
 * 加工建议api
 * @author yuand
 *
 */
@Api
@RestController
@AuthLogin
@RequestMapping("processingAdvice")
public class ProcessingAdviceController {

	@Autowired
	private ProcessingAdviceService processingAdviceService;
	
	/**
	 * 根据物性id，得到所有加工建议，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param sid 物性id
	 * @return
	 */
	@RequestMapping(value="queryAllProcessingAdvice",method=RequestMethod.GET)
	public  ResultPage  queryAllProcessingAdviceBySid(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,Integer  sid){
		PageInfo<ProcessingAdviceVo>  info = processingAdviceService.findAllProcessingAdviceById(pageIndex,pageSize,sid);
		return  ResultPage.list(info);
	}
	
	
	
	/**
	 * 根据加工建议id，得到加工建议详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryProcessingAdviceById",method=RequestMethod.GET)
	public  ResultData  queryProcessingAdviceById(Integer  id){
		ProcessingAdviceVo  processingAdviceVo = processingAdviceService.findProcessingAdviceById(id);
		return  ResultData.one(processingAdviceVo);
	}
	
	
	/**
	 * 管理员添加加工建议
	 * @param processingAdvice
	 * @return
	 */
	@RequestMapping(value="addProcessingAdvice",method=RequestMethod.POST)
	public  ResultData  addProcessingAdvice(ProcessingAdvice  processingAdvice){
		processingAdviceService.saveProcessingAdvice(processingAdvice);
		return ResultData.ok();
	}
	
	/**
	 * 保存修改后的加工建议
	 * @param processingAdvice
	 * @return
	 */
	@RequestMapping(value="editProcessingAdviceById",method=RequestMethod.POST)
	public  ResultData  editProcessingAdviceById(ProcessingAdvice  processingAdvice){
		//执行更新操作，先判断加工建议是否存在
		processingAdviceService.alterProcessingAdviceById(processingAdvice);
		return ResultData.ok();
	}
	
	
	/**
	 * 删除加工建议，根据加工建议id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelProcessingAdviceById",method=RequestMethod.GET)
	public  ResultData cancelProcessingAdviceById(Integer  id){
		processingAdviceService.removeProcessingAdviceById(id);
		return ResultData.ok();
	}
	
	/**
	 * 修改指定加工建议为审核成功
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editProcessingAdviceToSuccessById",method=RequestMethod.GET)
	public  ResultData editProcessingAdviceToSuccessById(Integer  id){
		processingAdviceService.alterProcessingAdviceToSuccessById(id);
		return ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
