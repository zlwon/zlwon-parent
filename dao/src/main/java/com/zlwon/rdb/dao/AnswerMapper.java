package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.Answer;

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
}
