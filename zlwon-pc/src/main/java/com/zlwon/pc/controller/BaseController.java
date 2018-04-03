package com.zlwon.pc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 共用基类
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/base")
public class BaseController {

	@ApiOperation(value = "测试环境")
    @RequestMapping(value = "/testHello", method = RequestMethod.GET)
    public ResultData testHello(@RequestParam String text){
	
		String result = "You say "+text;
		
		return ResultData.one(result);
	}
}
