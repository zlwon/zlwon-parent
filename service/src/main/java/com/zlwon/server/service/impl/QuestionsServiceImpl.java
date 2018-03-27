package com.zlwon.server.service.impl;

import com.zlwon.rdb.entity.Questions;
import com.zlwon.server.service.QuestionsService;
import org.springframework.stereotype.Service;

/**
 * 提问ServiceImpl
 * @author yangy
 *
 */

@Service
public class QuestionsServiceImpl implements QuestionsService {

	/**
	 * 根据提问ID查询提问
	 * @param id
	 * @return
	 */
	@Override
	public Questions findQuestionsById(Integer id){
		Questions temp = null;
		return temp;
	}
	
	/**
	 * 根据用户ID查询其所有的提问
	 * @param uid
	 * @return
	 */
	@Override
	public Questions findQuestionsByUId(Integer uid){
		Questions temp = null;
		return temp;
	}
}
