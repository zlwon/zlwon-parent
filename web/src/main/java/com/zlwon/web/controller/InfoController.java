package com.zlwon.web.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.web.info.InsertInfoDto;
import com.zlwon.dto.web.info.UpdateInfoDto;
import com.zlwon.rdb.entity.Info;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.InfoService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 资讯相关
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("info")
public class InfoController {

	@Autowired
	private InfoService infoService;
	
	/**
	 * 新增资讯信息
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "insertInfo", method = RequestMethod.POST)
	public ResultData insertInfo(InsertInfoDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String mainTitle = form.getMainTitle();  //主标题
		String subTitle = form.getSubTitle();  //副标题
		String source = form.getSource();  //来源
		String content = form.getContent();  //资讯详情内容
		String originPic = form.getOriginPic();  //配图原图
		String thumbPic = form.getThumbPic();  //配图缩略图
		
		if(StringUtils.isBlank(mainTitle) || StringUtils.isBlank(subTitle) || StringUtils.isBlank(source) ||
				StringUtils.isBlank(content) || StringUtils.isBlank(originPic) || StringUtils.isBlank(thumbPic)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Info record = new Info();
		record.setMainTitle(mainTitle);
		record.setSubTitle(subTitle);
		record.setSource(source);
		record.setReadNum(0);
		record.setContent(content);
		record.setOriginPic(originPic);
		record.setThumbPic(thumbPic);
		record.setIsHot(0);
		record.setUid(0);
		record.setCreateTime(new Date());
		
		int count = infoService.insertInfo(record);
		
		return ResultData.ok();
	}
	
	/**
	 * 根据资讯id删除资讯
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteInfoById", method = RequestMethod.GET)
	public ResultData deleteInfoById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = infoService.deleteInfoById(id);
		
		return ResultData.ok();
	}
	
	/**
	 * 置顶/取消热门资讯
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "topHotInfoById", method = RequestMethod.GET)
	public ResultData topHotInfoById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int result = infoService.updateInfoHotStatusById(id);
		
		return ResultData.one(result);  //返回1：置顶热门成功 0：取消置顶成功
	}
	
	/**
	 * 根据ID查询资讯详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryInfoDetailById", method = RequestMethod.GET)
	public ResultData queryInfoDetailById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Info result = infoService.findInfoDetailById(id);
		
		return ResultData.one(result);  
	}
	
	/**
	 * 编辑资讯信息
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "updateInfo", method = RequestMethod.POST)
	public ResultData updateInfo(UpdateInfoDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer id = form.getId();  //资讯ID
		String mainTitle = form.getMainTitle();  //主标题
		String subTitle = form.getSubTitle();  //副标题
		String source = form.getSource();  //来源
		String content = form.getContent();  //资讯详情内容
		String originPic = form.getOriginPic();  //配图原图
		String thumbPic = form.getThumbPic();  //配图缩略图
		
		if(id == null || StringUtils.isBlank(mainTitle) || StringUtils.isBlank(subTitle) || StringUtils.isBlank(source) ||
				StringUtils.isBlank(content) || StringUtils.isBlank(originPic) || StringUtils.isBlank(thumbPic)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Info record = new Info();
		record.setId(id);
		record.setMainTitle(mainTitle);
		record.setSubTitle(subTitle);
		record.setSource(source);
		record.setContent(content);
		record.setOriginPic(originPic);
		record.setThumbPic(thumbPic);
		
		int count = infoService.updateInfo(record);
		
		return ResultData.ok();
	}
}
