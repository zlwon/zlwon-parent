package com.zlwon.server.service;

import java.util.List;

import com.zlwon.rdb.entity.Answer;

/**
 * 提问回答Service
 * @author yangy
 *
 */

public interface AnswerService {

	/**
	 * 根据ID查询提问回答
	 * @param id
	 * @return
	 */
	Answer findAnswerById(Integer id);
	
	/**
	 * 根据提问ID查询所有提问回答
	 * @param qid
	 * @return
	 */
	List<Answer> findAnswerByQId(Integer qid);
	
	/**
	 * 根据用户ID查询所有属于该用户的提问回答
	 * @param uid
	 * @return
	 */
	List<Answer> findAnswerByUId(Integer uid);
}
