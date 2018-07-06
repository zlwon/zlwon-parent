package com.zlwon.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.DealerProductMapService;
import com.zlwon.vo.pc.dealerdQuotation.DealerProductMapDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 经销商可供产品pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/dealerProduct")
public class DealerProductController extends BaseController {

	@Autowired
	private DealerProductMapService dealerProductMapService;
	
	/**
	 * pc端根据物性ID查询经销商可供产品列表
	 * @param specId
	 * @return
	 */
	@ApiOperation(value = "pc端根据物性ID查询经销商可供产品列表")
    @RequestMapping(value = "/queryDealerProductBySpecId", method = RequestMethod.GET)
    public ResultData queryDealerProductBySpecId(@RequestParam Integer specId){
		
		List<DealerProductMapDetailVo> list = dealerProductMapService.findDealerProductMapBySpecId(specId);
		
		return ResultData.one(list);
	}
	
}
