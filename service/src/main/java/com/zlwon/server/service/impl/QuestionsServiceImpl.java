package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提问ServiceImpl
 * @author yangy
 *
 */

@Service
public class QuestionsServiceImpl implements QuestionsService {

	@Autowired
	private QuestionsMapper questionsMapper;
	
	/**
	 * 根据提问ID查询提问
	 * @param id
	 * @return
	 */
	@Override
	public Questions findQuestionsById(Integer id){
		Questions temp = questionsMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 根据用户ID查询其所有的提问
	 * @param uid
	 * @return
	 */
	@Override
	public Questions findQuestionsByUId(Integer uid){
		Questions temp = questionsMapper.findQuestionsByUId(uid);
		return temp;
	}
	
	/**
	 * 根据信息ID查询问题数量
	 * @param infoId
	 * @param type
	 * @return
	 */
	@Override
	public int countQuestionsByInfoId(Integer infoId,Integer type){
		int count = questionsMapper.countQuestionsByInfoId(infoId, type);
		return count;
	}
	
	/**
	 * 新增提问
	 * @param record
	 * @return
	 */
	@Override
	public int insertQuestions(Questions record){
		int count = questionsMapper.insertSelective(record);
		return count;
	}
	
	/**
	 * 分页查询我的提问问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findQuestionsByMyLaunch(QueryMyLaunchQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsByMyLaunch(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询我收藏的问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findQuestionsByMyCollect(QueryMyCollectQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsByMyCollect(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
}
