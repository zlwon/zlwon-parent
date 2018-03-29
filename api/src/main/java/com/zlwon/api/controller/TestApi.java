package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.VoteActivityService;
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
	 * 测试添加用户
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "测试添加用户")
    @RequestMapping(value = "/insertTestCustomer", method = RequestMethod.GET)
    public ResultData insertTestCustomer(@RequestParam String mobile){
	
		//验证参数
		if(StringUtils.isBlank(mobile)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//生成随机短信验证码
		String mobile_code = String.valueOf((int)((Math.random()*9+1)*100000));
		//将短信验证码存储入redis，命名规则mobilecode_+对应mobile符串
		//短信验证码10分钟内有效
		redisService.set("mobilecode_"+mobile, mobile_code,60*10,TimeUnit.SECONDS);
		
		String validcode = redisService.get("mobilecode_"+mobile);
		
		Collection temp = new Collection();
		temp.setIid(111);
		temp.setUid(111);
		temp.setType(1);
		temp.setCreateTime(new Date());
		
		Collection count = collectionService.insertCollection(temp);
		
		return ResultData.one(count);
	}
	
	/**
	 * 获取收藏集合
	 * @return
	 */
	@ApiOperation(value = "获取收藏集合")
    @RequestMapping(value = "/getTestCollection", method = RequestMethod.GET)
    public ResultData getTestCollection(){
	
		String mobile = "18278267889";
		
		//生成随机短信验证码
		String mobile_code = String.valueOf((int)((Math.random()*9+1)*100000));
		//将短信验证码存储入redis，命名规则mobilecode_+对应mobile符串
		//短信验证码10分钟内有效
		redisService.set("mobilecode_"+mobile, mobile_code,60*10,TimeUnit.SECONDS);
		
		String validcode = redisService.get("mobilecode_"+mobile);
		
		/*Collection temp = new Collection();
		temp.setIid(111);
		temp.setUid(111);
		temp.setType(1);
		temp.setCreateTime(new Date());
		
		Collection count = collectionService.insertCollection(temp);*/
		
		List<Collection> mnb = collectionService.getCollectionTestList();
		
		return ResultData.one(mnb);
	}
}
