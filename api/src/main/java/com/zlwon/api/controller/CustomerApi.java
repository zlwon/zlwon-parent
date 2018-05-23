package com.zlwon.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.vo.pc.customer.ProducerVo;

/**
 * 用户api
 * @author yuand
 *
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerApi {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	@RequestMapping(value = "queryProducer", method = RequestMethod.GET)
	public  ResultData  queryProducer(){
		List<ProducerVo> list = customerService.findProducer();
		return  ResultData.one(list);
	}
	
}
