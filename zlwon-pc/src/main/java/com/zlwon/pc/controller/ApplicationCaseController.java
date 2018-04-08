package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CollectionService;
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
	@Autowired
	private   CollectionService   collectionService;
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @return 案例id
	 */
	@RequestMapping(value="queryApplicationCaseDetails",method=RequestMethod.GET)
	public   ResultData   queryApplicationCaseDetails(Integer  id){
		ApplicationCaseDetailsVo   record = applicationCaseService.findApplicationCaseDetailsMake(id);
		return   ResultData.one(record);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
