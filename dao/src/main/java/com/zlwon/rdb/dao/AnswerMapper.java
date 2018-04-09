package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.vo.pc.answer.AnswerDetailVo;

/**
 * 提问回答Mapper
 * @author yangy
 *
 */

public interface AnswerMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);
    
    /**
     * 根据问题ID分页查询回答
     * @param form
     * @return
     */
    List<AnswerDetailVo> selectAnswerByquestionId(QueryAnswerByQuestionIdDto form);
}
