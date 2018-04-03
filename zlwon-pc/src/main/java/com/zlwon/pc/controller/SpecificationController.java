package com.zlwon.pc.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CharacteristicSpecMapService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 物性表pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/specification")
public class SpecificationController {

	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private CharacteristicSpecMapService characteristicSpecMapService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据物性ID查询物性表详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询物性表详情")
    @RequestMapping(value = "/querySpecInfoById", method = RequestMethod.GET)
	public ResultData querySpecInfoById(@RequestParam Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}

		//根据物性表ID查询物性表详情
		SpecificationDetailVo temp = specificationService.findSpecDetailById(id);
		
		//根据物性规格ID查询标签详情
		List<CharacteristicDetailVo> characterList = characteristicSpecMapService.selectCharacteristicSpecMapBySepcId(id);
		temp.setCharacterTap(characterList);
		
		//查询收藏信息
		
		
		return ResultData.one(temp);
	}
}
