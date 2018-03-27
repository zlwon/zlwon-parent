package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.collection.InsertCollectionDto;
import com.zlwon.dto.collection.JudgeCollectionDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户收藏api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/collection")
public class CollectionApi extends BaseApi {

	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 新增用户收藏
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "新增用户收藏")
    @RequestMapping(value = "/insertCollection", method = RequestMethod.POST)
    public ResultData insertCollection(InsertCollectionDto form){
        
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问
		String entryKey = form.getEntryKey();  //微信加密字符串
		Integer iid = form.getIid();  //信息ID
		
		if(type == null || StringUtils.isBlank(entryKey) || iid == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据openId获取用户
		Customer user = customerService.selectCustomerByOpenId(openId);
		if(user == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		Collection temp = new Collection();
		temp.setType(type);
		temp.setUid(user.getId());
		temp.setIid(iid);
		temp.setCreateTime(new Date());
		
		//新增用户收藏
		Collection result = collectionService.insertCollection(temp);
		
		return ResultData.one(result);
	}
	
	/**
	 * 判断用户是否收藏当前信息
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "判断用户是否收藏当前信息")
    @RequestMapping(value = "/judgeCollectionStatus", method = RequestMethod.POST)
    public ResultData judgeCollectionStatus(JudgeCollectionDto form){
        
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问
		String entryKey = form.getEntryKey();  //微信加密字符串
		Integer iid = form.getIid();  //信息ID
		
		if(type == null || StringUtils.isBlank(entryKey) || iid == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		form.setOpenId(openId);
		
		Collection temp = collectionService.selectCollectionByUserAndInfo(form);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 根据ID删除用户收藏
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID删除用户收藏")
    @RequestMapping(value = "/deleteCollection/{id}/{entryKey}", method = RequestMethod.GET)
    public ResultData deleteCollection(@PathVariable Integer id,@PathVariable String entryKey){
        
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//删除用户收藏
		int count = collectionService.deleteCollectionById(id);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
}
