package com.zlwon.mobile.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.collection.OperateCollectionDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CollectionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 移动端官网收藏接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/mobile/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;
	
	/**
	 * 用户操作收藏信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "用户操作收藏信息")
    @RequestMapping(value = "/operateCollection", method = RequestMethod.POST)
	public ResultData operateCollection(OperateCollectionDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
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
		
		Integer returnStatus = 0;  //0：取消收藏成功，1：新增收藏成功
		
		//查询收藏信息是否存在
		Collection collectInfo = collectionService.findCollectionInfoByUser(type,iid,user.getId());
		if(collectInfo == null){
			Collection temp = new Collection();
			temp.setType(type);
			temp.setUid(user.getId());
			temp.setIid(iid);
			temp.setCreateTime(new Date());
			
			//新增用户收藏
			Collection result = collectionService.insertCollection(temp);
			returnStatus = 1;
		}else{
			//删除收藏
			int count = collectionService.deleteCollectionById(collectInfo.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			returnStatus = 0;
		}
		
		return ResultData.one(returnStatus);
	}
}
