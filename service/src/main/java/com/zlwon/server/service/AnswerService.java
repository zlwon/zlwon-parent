package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.dto.pc.answer.QueryMyAnswerByCenterPage;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.vo.pc.answer.AnswerDetailVo;
import com.zlwon.vo.pc.answer.AnswerQuestionDetailVo;
import com.zlwon.vo.pc.answer.InvitateAnswerDetailVo;

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
	
	/**
     * 根据问题ID分页查询回答
     * @param form
     * @return
     */
	PageInfo<AnswerDetailVo> findAnswerByquestionId(QueryAnswerByQuestionIdDto form);
	
	/**
     * 分页查询我的回答-个人中心
     * @param form
     * @return
     */
	PageInfo<AnswerQuestionDetailVo> findMyAnswerByCenterPage(QueryMyAnswerByCenterPage form);
	
	/**
     * 查询我的回答数量-个人中心
     * @param userId
     * @return
     */
    int countMyAnswerByCenter(Integer userId);
    
    /**
     * 根据回答ID删除回答
     * @param id
     * @return
     */
    int deleteAnswer(Integer id);
    
    /**
     * 修改回答
     * @param record
     * @return
     */
    int updateAnswer(Answer record);

    /**
	 * 得到所有问答信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo findAllAnswerPage(Integer pageIndex, Integer pageSize);

	/**
	 * 设置回答信息为驳回
	 * @param id 回答id
	 * @param content 驳回内容
	 * @return
	 */
	int alterAnswerFailed(Integer id, String content);

	/**
	 * 得到回答驳回信息
	 * @param id 回答id
	 * @return
	 */
	String findAnswerFailedContent(Integer id);
	
	/**
	 * 根据信息ID和信息类型查询推荐邀请回答用户
	 * @param infoId
	 * @param type
	 * @return
	 */
	List<InvitateAnswerDetailVo> findInvitateAnswerUserList(Integer infoId,Integer type);
}
