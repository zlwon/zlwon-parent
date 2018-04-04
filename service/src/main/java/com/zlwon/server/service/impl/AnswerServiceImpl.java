package com.zlwon.server.service.impl;

import java.util.List;

import com.zlwon.rdb.dao.AnswerMapper;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.server.service.AnswerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提问回答ServiceImpl
 * @author yangy
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerMapper answerMapper;
	
	/**
	 * 根据ID查询提问回答
	 * @param id
	 * @return
	 */
	@Override
	public Answer findAnswerById(Integer id){
		Answer temp = answerMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 新增提问回答
	 * @param record
	 * @return
	 */
	@Override
	public int insertAnswer(Answer record){
		int count = answerMapper.insertSelective(record);
		return count;
	}
}
