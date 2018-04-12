package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.programAccess.AddProgramAccessRecordDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.ProgramAccessRecord;
import com.zlwon.rdb.entity.SysParam;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.ProgramAccessRecordService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.SysParamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 小程序用户访问浏览行为api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/programAccess")
public class ProgramAccessApi extends BaseApi {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProgramAccessRecordService programAccessRecordService;
	
	@Autowired
	private SysParamService sysParamService;
	
	/**
	 * 新增小程序用户访问浏览行为记录-访问路径
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "新增小程序用户访问浏览行为记录")
    @RequestMapping(value = "/addProgramAccessRecord", method = RequestMethod.POST)
    public ResultData addProgramAccessRecord(AddProgramAccessRecordDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}

		String route = form.getRoute();  //页面路由
		String remark = form.getRemark();  //备注
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		//验证参数
		if(StringUtils.isBlank(route) || StringUtils.isBlank(remark) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据openId获取用户信息
		Customer user = customerService.selectCustomerByOpenId(openId);
		if(user == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		ProgramAccessRecord addInfo = new ProgramAccessRecord();
		addInfo.setUid(user.getId());
		addInfo.setRoute(route);
		addInfo.setRemark(remark);
		addInfo.setCreateTime(new Date());
		
		//新增小程序用户访问浏览行为记录
		int count = programAccessRecordService.insertProgramAccessRecord(addInfo);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		//返回对应版本
		//SysParam resultBack = sysParamService.findSysParamById(1);
		
		return ResultData.ok();
	}
}
