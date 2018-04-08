package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.rdb.entity.Questions;
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
}
