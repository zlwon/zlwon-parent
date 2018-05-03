package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CharacteristicService;
import com.zlwon.vo.characteristic.CharacteristicListVo;
import com.zlwon.web.annotations.AuthLogin;

import io.swagger.annotations.Api;

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
	public   ResultPage   queryAllCharacteristic(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		PageInfo<CharacteristicListVo> info = characteristicService.findAllCharacteristic(pageIndex,pageSize);
		return   ResultPage.list(info);
	}
	
	
	/**
	 * 标签驳回
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editCharacteristicToFailed",method=RequestMethod.POST)
	public  ResultData  editCharacteristicToFailed(Integer  id,String  content){
		characteristicService.alterCharacteristicToFailed(id,content);
		return  ResultData.ok();
	}
	
	
	/**
	 * 得到标签驳回信息
	 * @param id 标签id
	 * @return
	 */
	@RequestMapping(value="queryCharacteristicFailedContent",method=RequestMethod.GET)
	public  ResultData  queryCharacteristicFailedContent(Integer  id){
		String  content = characteristicService.findCharacteristicFailedContent(id);
		return  ResultData.one(content);
	}
	
	/**
	 * 删除指定标签，通过标签id,不删除了，只有驳回
	 * @param id
	 * @return
	 */
	/*@RequestMapping(value="cancelCharacteristicById",method=RequestMethod.GET)
	public  ResultData  cancelCharacteristicById(Integer  id){
		characteristicService.removeCharacteristicById(id);
		return  ResultData.ok();
	}*/
}
