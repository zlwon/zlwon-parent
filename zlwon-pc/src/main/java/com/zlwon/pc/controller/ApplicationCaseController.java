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
	 * @return
	 */
	@RequestMapping(value="queryApplicationCaseDetails",method=RequestMethod.GET)
	public   ResultData   queryApplicationCaseDetails(Integer  id){
		ApplicationCaseDetailsVo   record = applicationCaseService.findApplicationCaseDetailsMake(id);
		return   ResultData.one(record);
	}
	
	
	//根据案例id，得到问答信息，分页
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
