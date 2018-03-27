package com.zlwon.server.service.impl;

import java.util.List;

import com.zlwon.rdb.entity.Answer;
import com.zlwon.server.service.AnswerService;
import org.springframework.stereotype.Service;

/**
 * 提问回答ServiceImpl
 * @author yangy
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService {

	/**
	 * 根据ID查询提问回答
	 * @param id
	 * @return
	 */
	@Override
	public Answer findAnswerById(Integer id){
		Answer temp = null;
		return temp;
	}
	
	/**
	 * 根据提问ID查询所有提问回答
	 * @param qid
	 * @return
	 */
	@Override
	public List<Answer> findAnswerByQId(Integer qid){
		List<Answer> list = null;
		return list;
	}
	
	/**
	 * 根据用户ID查询所有属于该用户的提问回答
	 * @param uid
	 * @return
	 */
	@Override
	public List<Answer> findAnswerByUId(Integer uid){
		List<Answer> list = null;
		return list;
	}
}
