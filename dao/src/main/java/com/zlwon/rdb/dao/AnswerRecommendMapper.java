package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.AnswerRecommend;

/**
 * 回答指定推荐用户表Mapper
 * @author yangy
 *
 */

public interface AnswerRecommendMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(AnswerRecommend record);

    int insertSelective(AnswerRecommend record);

    AnswerRecommend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnswerRecommend record);

    int updateByPrimaryKey(AnswerRecommend record);
}