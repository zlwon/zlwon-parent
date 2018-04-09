package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.vo.pc.answer.AnswerDetailVo;

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
}
