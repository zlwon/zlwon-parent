package com.zlwon.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.server.service.SpecificationService;

import io.swagger.annotations.Api;

/**
 * 小程序端物性接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/specification")
public class SpecificationApi extends BaseApi {

	@Autowired
	private SpecificationService specificationService;
	
	
}
