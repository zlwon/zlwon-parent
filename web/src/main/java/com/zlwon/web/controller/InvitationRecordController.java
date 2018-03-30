package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.InvitationRecordService;

/**
 * 企业邀请知料师
 * @author FelixChen
 *
 */
@RestController
@RequestMapping("invitationRecord")
public class InvitationRecordController {

	@Autowired
	private   InvitationRecordService   invitationRecordService;
	
	/**
	 * 得到所有企业邀请的知料师信息，分页查找
	 * @param pageIndex 
	 * @param pageSize
	 * @param invitation 邀请接受状态，0邀请确认中，1接受，-1拒绝
	 * @return
	 */
	@RequestMapping(value="queryAllInvitationRecord",method=RequestMethod.GET)
	public  ResultPage  queryAllInvitationRecord(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize,Integer  invitation){
		PageInfo pageInfo = invitationRecordService.findAllInvitationRecord(pageIndex,pageSize,invitation);
		return  ResultPage.list(pageInfo);
	}
	
	/**
	 * 根据邀请id，删除邀请信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelInvitationRecordById",method=RequestMethod.GET)
	public  ResultData cancelInvitationRecordById(Integer  id){
		invitationRecordService.removeInvitationRecordById(id);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
