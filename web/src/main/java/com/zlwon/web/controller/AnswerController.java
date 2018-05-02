package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AnswerService;


/**
 * 回答
 * @author yuand
 *
 */
@RestController
@RequestMapping("answer")
public class AnswerController {
	
	@Autowired
	private  AnswerService  answerService;

	/**
	 * 得到所有问答信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllAnswer",method=RequestMethod.GET)
	public   ResultPage   queryAllAnswer(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		PageInfo  info = answerService.findAllAnswerPage(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	/**
	 * 设置回答信息为驳回
	 * @param id 回答id
	 * @param content 驳回内容
	 * @return
	 */
	@RequestMapping(value="editAnswerFailed",method=RequestMethod.POST)
	public   ResultData   editAnswerFailed(Integer  id,String  content){
		answerService.alterAnswerFailed(id,content);
		return  ResultData.ok();
	}
	
	/**
	 * 得到回答驳回信息
	 * @param id 回答id
	 * @return
	 */
	@RequestMapping(value="queryAnswerFailedContent",method=RequestMethod.GET)
	public  ResultData   queryAnswerFailedContent(Integer  id){
		String   content = answerService.findAnswerFailedContent(id);
		return  ResultData.one(content);
	}
}
