package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.api.question.QueryDefineClearQuestionsDto;
import com.zlwon.dto.api.question.QueryQuestionListByInfoIdDto;
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
	
	/**
	 * 分页查询特定类型的问题（可指定具体）
	 * 小程序端
	 * @param form
	 * @return
	 */
	PageInfo<QuestionsDetailVo> findWCSpecifyQuestions(QueryDefineClearQuestionsDto form);
	
	/**
	 * 根据信息ID查询提问列表
	 * @param form
	 * @return
	 */
	List<QuestionsDetailVo> findQuestionsLsitByInfoId(QueryQuestionListByInfoIdDto form);
	
	/**
	 * 根据问题ID查询问题详情
	 * @param questionId
	 * @param userId
	 * @return
	 */
	QuestionsDetailVo findSingleQuestionDetailById(Integer questionId,Integer userId);

	/**
	 * 得到首页最热门的问答(根据提问回答最多查询，最多4个)
	 * @return
	 */
	List<IndexHotApplicationCaseQuestionAndAnswerVo> findHotQuestions();
	
	/**
	 * 查询我的提问问题数量
	 * @param userId
	 * @return
	 */
	int findQuestionsCountByMyLaunch(Integer userId);

	/**
	 * 得到所有提问信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo findAllQuestionsPage(Integer pageIndex, Integer pageSize);

	/**
	 * 设置提问信息为通过
	 * @param id 提问id
	 * @return
	 */
	int alterQuestionsSuccess(Integer id);

	/**
	 * 设置提问信息为驳回
	 * @param id 提问id
	 * @param content 驳回内容
	 * @return
	 */
	int alterQuestionsFailed(Integer id, String content);

	/**
	 * 得到提问驳回信息
	 * @param id 提问id
	 * @return
	 */
	String findQuestionsFailedContent(Integer id);

	/**
	 * 管理员发送邀请问答邮件
	 * @param uids 被邀请的用户id，多个逗号隔开
	 * @param id 问题id
	 * @return
	 */
	int sendAnInvitationEmail(String uids, Integer id);
}
