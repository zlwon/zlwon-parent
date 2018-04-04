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
	 * 新增提问回答
	 * @param record
	 * @return
	 */
	int insertAnswer(Answer record);
}
