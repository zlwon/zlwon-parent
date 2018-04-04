package com.zlwon.pc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;

/**
 * 案例api
 * @author yuand
 *
 */
@RestController
@RequestMapping("/pc/applicationCase")
public class ApplicationCaseController {

	@Autowired
	private   ApplicationCaseService   applicationCaseService;
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @return 案例id
	 */
	@RequestMapping(value="queryApplicationCaseDetails",method=RequestMethod.GET)
	public   ResultData   queryApplicationCaseDetails(Integer  id){
		ApplicationCaseDetailsVo   record = applicationCaseService.findApplicationCaseDetailsMake(id);
		return   ResultData.one(record);
	}
	
	
	/**
	 * 用户收藏案例,未实现
	 * @param id 案例id
	 * @return
	 */
	public   ResultData  addApplicationCaseCollection(Integer  id){
		applicationCaseService.saveApplicationCaseCollection(id);
		return  ResultData.ok();
	}
	
	//根据案例id，得到问答信息，分页
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
