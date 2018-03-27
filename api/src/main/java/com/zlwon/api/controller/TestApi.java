package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.VoteActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/test")
public class TestApi extends BaseApi {
	
	@Autowired
	private VoteActivityService voteActivityService;
	
	/**
	 * 根据活动ID查询投票活动信息
	 * @param id
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据活动ID查询投票活动信息")
    @RequestMapping(value = "/queryVoteActivityById", method = RequestMethod.GET)
    public ResultData queryVoteActivityById(@RequestParam Integer id){
	
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据活动ID查询投票活动信息
		VoteActivity temp = voteActivityService.selectVoteActivityById(id);
		
		return ResultData.one(temp);
	}
}
