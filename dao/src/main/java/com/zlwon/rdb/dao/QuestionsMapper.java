package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.Questions;

/**
 * 提问Mapper
 * @author yangy
 *
 */

public interface QuestionsMapper {

	/**
	 * 根据提问ID查询提问
	 * @param id
	 * @return
	 */
	Questions findQuestionsById(Integer id);
	
	/**
	 * 根据用户ID查询其所有的提问
	 * @param uid
	 * @return
	 */
	Questions findQuestionsByUId(Integer uid);
}
