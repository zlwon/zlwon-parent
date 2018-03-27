package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.VoteActivityService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 投票活动api
 * @author yuand
 *
 */
@Api
@AuthLogin
@RestController
@RequestMapping("voteActivity")
public class VoteActivityController {

	@Autowired
	private VoteActivityService voteActivityService;
	
	/**
	 * 得到所有活动，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllVoteActivity",method=RequestMethod.GET)
	public  ResultPage  queryAllVoteActivity(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		//得到所有正常的活动
		PageInfo<VoteActivity>  info = voteActivityService.findAllVoteActivityMake(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	/**
	 * 根据活动id，得到活动信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryVoteActivityById",method=RequestMethod.GET)
	public  ResultData  queryVoteActivityById(Integer  id){
		//根据id，得到正常的活动
		VoteActivity voteActivity = voteActivityService.findVoteActivityByIdMake(id);
		return  ResultData.one(voteActivity);
	}
	
	/**
	 * 添加活动
	 * @param voteActivity
	 * @return
	 */
	@RequestMapping(value="addVoteActivity",method=RequestMethod.POST)
	public  ResultData  addVoteActivity(VoteActivity  voteActivity){
		//添加活动，需要判断标题是否存在，标题唯一
		voteActivityService.saveVoteActivity(voteActivity);
		return  ResultData.ok();
	}
	
	/**
	 * 保存更新后的活动，根据活动id
	 * @param voteActivity
	 * @return
	 */
	@RequestMapping(value="editVoteActivityById",method=RequestMethod.POST)
	public  ResultData  editVoteActivityById(VoteActivity  voteActivity){
		//更新活动，需要判断活动是否正常存在，然后在判断标题是否存在，标题唯一
		voteActivityService.alterVoteActivityByIdMake(voteActivity);
		return  ResultData.ok();
	}
	
	
	/**
	 * 删除活动，根据活动id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelVoteActivityById",method=RequestMethod.GET)
	public  ResultData  cancelVoteActivityById(Integer  id){
		//执行更新操作
		voteActivityService.removeVoteActivityById(id);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
