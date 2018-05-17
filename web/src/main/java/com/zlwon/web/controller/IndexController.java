package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.IndexService;

/**
 * web端首页api
 * @author yuand
 *
 */
@RestController
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private  IndexService   indexService;
	
	/**
	 * 未审核的个数统计(label)，
	 * @return
	 */
	@RequestMapping(value="queryIndexNotExamineNumber",method=RequestMethod.GET)
	public  ResultData   queryIndexNotExamineNumber(){
		Object   obj = indexService.findIndexNotExamineNumber();
		return  ResultData.one(obj);
	}
}
