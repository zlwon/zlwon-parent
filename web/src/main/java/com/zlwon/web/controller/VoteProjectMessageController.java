package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.VoteProjectMessage;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.VoteProjectMessageService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AuthLogin
@Api
@RestController
@RequestMapping("voteProjectMessage")
public class VoteProjectMessageController {

	@Autowired
	private VoteProjectMessageService voteProjectMessageService;
	
	
	/**
	 * 得到所有评论，分页查找，可根据关键字查找评论内容
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllVoteProjectMessage",method=RequestMethod.POST)
	public  ResultPage   queryAllVoteProjectMessage(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,String  message){
		PageInfo<VoteProjectMessage>  info = voteProjectMessageService.findAllVoteProjectMessage(pageIndex,pageSize,message);
		return  ResultPage.list(info);
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	@RequestMapping(value="cancelVoteProjectMessageById",method=RequestMethod.GET)
	public  ResultData  cancelVoteProjectMessageById(Integer  id){
		voteProjectMessageService.removeVoteProjectMessageById(id);
		return  ResultData.ok();
	}
}
