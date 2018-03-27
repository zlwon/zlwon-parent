package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CharacteristicSpecMapService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物性表api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/specification")
public class SpecificationApi extends BaseApi {

	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private CharacteristicSpecMapService characteristicSpecMapService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据物性表ID查询物性表详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询物性表详情")
    @RequestMapping(value = "/querySpecById/{id}/{entryKey}", method = RequestMethod.GET)
	public ResultData querySpecificationById(@PathVariable Integer id,@PathVariable String entryKey){
		
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}

		//根据物性表ID查询物性表详情
		//Specification temp = specificationService.findSpecificationById(id);
		SpecificationDetailVo temp = specificationService.findSpecDetailById(id);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 根据物性表规格名查询物性表详情
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "根据物性表规格名查询物性表详情")
    @RequestMapping(value = "/querySpecByName/{name}/{entryKey}", method = RequestMethod.GET)
	public ResultData querySpecificationByName(@PathVariable String name,@PathVariable String entryKey){
		
		//验证参数
		if(StringUtils.isBlank(name) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据物性表规格名查询物性表详情
		//Specification temp = specificationService.findSpecificationByName(name);
		SpecificationDetailVo temp = specificationService.findSpecDetailByName(name);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 根据物性规格ID查询标签详情
	 * @param specId
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据物性规格ID查询标签详情")
    @RequestMapping(value = "/queryCharacteristicBySpecId/{specId}/{entryKey}", method = RequestMethod.GET)
	public ResultData queryCharacteristicBySpecId(@PathVariable Integer specId,@PathVariable String entryKey){
		
		//验证参数
		if(specId == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据物性规格ID查询标签详情
		List<CharacteristicDetailVo> list = characteristicSpecMapService.selectCharacteristicSpecMapBySepcId(specId);
		
		return ResultData.one(list);
	}
}
