package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.VoteProject;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.VoteProjectService;
import com.zlwon.vo.voteActivity.VoteProjectDetailVo;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 投票活动参与项目信息api
 * @author yuand
 *
 */
@Api
@AuthLogin
@RestController
@RequestMapping("voteProject")
public class VoteProjectController {

	@Autowired
	private VoteProjectService voteProjectService;
	
	/**
	 * 根据活动id，得到所有参与的活动项目信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param aid 活动id
	 * @return
	 */
	@RequestMapping(value="queryAllVoteProject",method=RequestMethod.GET)
	public  ResultPage  queryAllVoteProjectByAid(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,Integer aid){
		//需要参与用户标记状态正常
		PageInfo<VoteProjectDetailVo>  info = voteProjectService.findAllVoteProjectByAidMake(pageIndex,pageSize,aid);
		return  ResultPage.list(info);
	}
	
	/**
	 * 修改投票活动参与项目信息审核状态
	 * @param voteProject 
	 * @return
	 */
	@RequestMapping(value="editVoteProjectExamineById",method=RequestMethod.POST)
	public  ResultData  editVoteProjectExamineById(VoteProject  voteProject){
		//根据参与项目id，修改审核状态，需要先判断参与项目是否存在
		voteProjectService.alterVoteProjectExamineById(voteProject);
		return ResultData.ok();
	}
	
	/**
	 * 根据参与项目id，删除参与项目信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelVoteProjectById",method=RequestMethod.GET)
	public  ResultData  cancelVoteProjectById(Integer id){
		voteProjectService.removeVoteProjectById(id);
		return ResultData.ok();
	}
	
	
	
	
}
