package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用案例api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/applicationCase")
public class ApplicationCaseApi extends BaseApi {

	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据应用案例ID查询应用案例详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据应用案例ID查询应用案例详情")
    @RequestMapping(value = "/queryCaseById/{id}/{entryKey}", method = RequestMethod.GET)
    public ResultData queryApplicationCaseById(@PathVariable Integer id,@PathVariable String entryKey){
        
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据应用案例ID查询应用案例详情
		//ApplicationCase temp =  applicationCaseService.findAppCaseById(id);
		ApplicationCaseDetailVo temp = applicationCaseService.findAppCaseDetailById(id);
		
		return ResultData.one(temp);
    }
	
	
}
