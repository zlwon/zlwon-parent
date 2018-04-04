package com.zlwon.server.service.impl;

import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.server.service.QuestionsService;

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
}
