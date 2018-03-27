package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CharacteristicService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物性表主要特性标签api
 * @author yuand
 *
 */
@Api
@AuthLogin
@RestController
@RequestMapping("characteristic")
public class CharacteristicController {

	@Autowired
	private CharacteristicService characteristicService;
	
	/**
	 * 得到所有标签，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllCharacteristic",method=RequestMethod.GET)
	public   ResultPage   queryAllCharacteristic(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		PageInfo<Characteristic> info = characteristicService.findAllCharacteristic(pageIndex,pageSize);
		return   ResultPage.list(info);
	}
	
	
	/**
	 * 标签审核通过
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editCharacteristicToSuccess",method=RequestMethod.GET)
	public  ResultData  editCharacteristicToSuccess(Integer  id){
		//更新为审核通过，需要先判断给标签是否存在，然后在判断是否是审核状态
		characteristicService.alterCharacteristicToSuccess(id);
		return  ResultData.ok();
	}
	
	
	/**
	 * 删除指定标签，通过标签id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelCharacteristicById",method=RequestMethod.GET)
	public  ResultData  cancelCharacteristicById(Integer  id){
		characteristicService.removeCharacteristicById(id);
		return  ResultData.ok();
	}
}
