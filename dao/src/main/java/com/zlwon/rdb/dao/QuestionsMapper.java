package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.dto.pc.questions.QueryAllSpecifyQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAttentionMeQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAttentionQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseQuestionAndAnswerVo;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;

/**
 * 提问Mapper
 * @author yangy
 *
 */

public interface QuestionsMapper {
	
	int deleteByPrimaryKey(Integer id);

    int insert(Questions record);

    int insertSelective(Questions record);

    Questions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Questions record);

    int updateByPrimaryKeyWithBLOBs(Questions record);

    int updateByPrimaryKey(Questions record);
	
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
	int countQuestionsByInfoId(@Param("infoId") Integer infoId,@Param("type") Integer type);
	
	/**
	 * 分页查询我的提问问题
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectQuestionsByMyLaunch(QueryMyLaunchQuestionsDto form);
	
	/**
	 * 分页查询我收藏的问题
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectQuestionsByMyCollect(QueryMyCollectQuestionsDto form);
	
	/**
	 * 分页查询我回答的问题
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectQuestionsByMyAnswer(QueryMyAnswerQuestionsDto form);
	
	/**
	 * 分页查询我关注的人的问题
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectMyAttentionQuestions(QueryMyAttentionQuestionsDto form);
	
	/**
	 * 分页查询关注我的人的问题
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectAttentionMeQuestions(QueryAttentionMeQuestionsDto form);
	
	/**
	 * 分页查询特定类型的问题（可指定具体）
	 * 未登录状态
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectAllSpecifyQuestionsNoLogin(QueryAllSpecifyQuestionsDto form);
	
	/**
	 * 分页查询特定类型的问题（可指定具体）
	 * 登录状态
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> selectAllSpecifyQuestionsLogin(QueryAllSpecifyQuestionsDto form);
	
	/**
	 * 根据问题ID查询问题详情
	 * 用户登录
	 * @param questionId
	 * @param userId
	 * @return
	 */
	QuestionsDetailVo selectSingleQuestionDetailById(@Param("questionId")Integer questionId,@Param("userId")Integer userId);
	
	/**
	 * 根据问题ID查询问题详情
	 * 未登录
	 * @param questionId
	 * @return
	 */
	QuestionsDetailVo selectSingleQuestionDetailNoLoginById(Integer questionId);

	/**
	 * 得到首页最热门的问答(根据提问回答最多查询，最多4个)
	 * @return
	 */
	List<IndexHotApplicationCaseQuestionAndAnswerVo> selectHotQuestions();
}
