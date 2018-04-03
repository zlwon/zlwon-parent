package com.zlwon.pc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.collection.PcInsertCollectionDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.RedisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 收藏pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 新增用户收藏
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增用户收藏")
    @RequestMapping(value = "/insertCollection", method = RequestMethod.POST)
    public ResultData insertCollection(PcInsertCollectionDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getParameter("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问
		Integer iid = form.getIid();  //信息ID
		
		if(type == null || iid == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Collection temp = new Collection();
		temp.setType(type);
		temp.setUid(user.getId());
		temp.setIid(iid);
		temp.setCreateTime(new Date());
		
		//新增用户收藏
		Collection result = collectionService.insertCollection(temp);
		
		return ResultData.ok();
	}
	
	/**
	 * 根据收藏ID删除用户收藏
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据收藏ID删除用户收藏")
    @RequestMapping(value = "/deleteCollection", method = RequestMethod.GET)
    public ResultData deleteCollection(@RequestParam Integer id){
        
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//删除用户收藏
		int count = collectionService.deleteCollectionById(id);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
}