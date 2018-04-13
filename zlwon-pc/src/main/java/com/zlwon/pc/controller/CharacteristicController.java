package com.zlwon.pc.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CharacteristicBusinessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统内标签相关pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/characteristic")
public class CharacteristicController extends BaseController {

	@Autowired
	private CharacteristicBusinessService characteristicBusinessService;
	
	/**
	 * 根据父ID查询个人业务标签
	 * @param parentId
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据父ID查询个人业务标签")
    @RequestMapping(value = "/queryCharacteristicBusinessByParentId", method = RequestMethod.POST)
    public ResultData queryCharacteristicBusinessByParentId(@RequestParam Integer parentId,HttpServletRequest request){
		
		//验证参数
		if(parentId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据父ID查询个人业务标签
		List<CharacteristicBusiness> list = characteristicBusinessService.findCharacteristicBusinessByParentId(parentId);
		
		return ResultData.one(list);
	}
}
