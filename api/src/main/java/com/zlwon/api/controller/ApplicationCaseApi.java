package com.zlwon.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.applicationCase.QueryApplicationCaseListDto;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;

/**
 * 案例api
 * @author yuand
 *
 */
@RestController
@RequestMapping("/api/applicationCase")
public class ApplicationCaseApi {

	@Autowired
	private   ApplicationCaseService   applicationCaseService;
	
	/**
	 * 得到所有案例，条件查询，分页
	 * @param pageIndex
	 * @param pageSize
	 * @param listVo 条件查询
	 * @return
	 */
	@RequestMapping(value="queryAllApplicationCase",method=RequestMethod.POST)
	public  ResultPage  queryAllApplicationCase(HttpServletRequest  request,@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="10")Integer  pageSize,QueryApplicationCaseListDto  listDto){
		PageInfo  info = applicationCaseService.findAllApplicationCaseSelective(request,pageIndex, pageSize,listDto,2);
		return   ResultPage.list(info);
	}
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @return 案例id
	 */
	@RequestMapping(value="queryApplicationCaseDetails",method=RequestMethod.GET)
	public   ResultData   queryApplicationCaseDetails(Integer  id,HttpServletRequest  request){
		ApplicationCaseDetailsVo   record = applicationCaseService.findApplicationCaseDetailsMake(id,request,2);
		return   ResultData.one(record);
	}
}
