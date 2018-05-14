package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AnswerRecordService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 案例(物性)推介邀请回答人
 * @author yuand
 *
 */
@AuthLogin
@RestController
@RequestMapping("answerRecommend")
public class AnswerRecommendController {

	@Autowired
	private  AnswerRecordService  answerRecordService;
	
	/**
	 * 得到所有案例(物性)推介邀请回答人信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param type 0:查询所有1:物性2:案例
	 * @return
	 */
	@RequestMapping(value="queryAllAnswerRecord",method=RequestMethod.GET)
	public   ResultPage   queryAllAnswerRecord(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,@RequestParam(defaultValue="0")Integer  type){
		PageInfo  info = answerRecordService.findAllAnswerRecord(pageIndex,pageSize,type);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 删除指定推介邀请回答
	 * @param id 推介邀请回答id
	 * @return
	 */
	@RequestMapping(value="cancelAnswerRecord",method=RequestMethod.GET)
	public  ResultData   cancelAnswerRecord(Integer  id){
		answerRecordService.removeAnswerRecordById(id);
		return  ResultData.ok();
	}
}
