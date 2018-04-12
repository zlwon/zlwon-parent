package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.questions.QueryAllSpecifyQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAttentionMeQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAttentionQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;

/**
 * 提问Service
 * @author yangy
 *
 */

public interface QuestionsService {

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
	
	/**
	 * 根据信息ID查询问题数量
	 * @param infoId
	 * @param type
	 * @return
	 */
	int countQuestionsByInfoId(Integer infoId,Integer type);
	
	/**
	 * 新增提问
	 * @param record
	 * @return
	 */
	int insertQuestions(Questions record);
	
	/**
	 * 分页查询我的提问问题
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findQuestionsByMyLaunch(QueryMyLaunchQuestionsDto form);
	
	/**
	 * 分页查询我收藏的问题
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findQuestionsByMyCollect(QueryMyCollectQuestionsDto form);
	
	/**
	 * 分页查询我回答的问题
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findQuestionsByMyAnswer(QueryMyAnswerQuestionsDto form);
	
	/**
	 * 分页查询我关注的人的问题
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findMyAttentionQuestions(QueryMyAttentionQuestionsDto form);
	
	/**
	 * 分页查询关注我的人的问题
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findAttentionMeQuestions(QueryAttentionMeQuestionsDto form);
	
	/**
	 * 分页查询特定类型的问题（可指定具体）
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findAllSpecifyQuestions(QueryAllSpecifyQuestionsDto form);
}
