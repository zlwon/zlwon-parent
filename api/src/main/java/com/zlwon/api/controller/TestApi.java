package com.zlwon.api.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.voteActivity.VoteProjectPageDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.VoteActivityService;
import com.zlwon.server.service.VoteProjectService;
import com.zlwon.vo.voteActivity.VoteProjectDetailListVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private VoteProjectService voteProjectService;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
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
	
	/**
	 * 发送队列信息
	 * @param redisKey
	 * @param redisValue
	 * @return
	 */
	@ApiOperation(value = "发送队列")
    @RequestMapping(value = "/sendRedisQuene", method = RequestMethod.GET)
    public ResultData sendRedisQuene(@RequestParam String redisKey,@RequestParam String redisValue){
	
		//验证参数
		if(StringUtils.isBlank(redisKey) || StringUtils.isBlank(redisValue)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//发送队列信息
		stringRedisTemplate.convertAndSend(redisKey, redisValue);
		
		return ResultData.ok();
	}
}
