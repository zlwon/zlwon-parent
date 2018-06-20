package com.zlwon.mobile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/mobile/test")
public class TestController {

	@ApiOperation(value = "测试")
    @RequestMapping(value = "/testdispaly", method = RequestMethod.GET)
	public String displayData(){
		return "test!!!";
	}
}
