package com.zlwon.rdb.dao;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.AnswerRecord;

public interface AnswerRecordMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(AnswerRecord record);

    int insertSelective(AnswerRecord record);

    AnswerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnswerRecord record);

    int updateByPrimaryKey(AnswerRecord record);
    
    /**
     * 根据用户ID和回答ID查询点赞记录
     * @param userId
     * @param answerId
     * @return
     */
    AnswerRecord selectAnswerRecordByUserAnswer(@Param("userId")Integer userId,@Param("answerId")Integer answerId);
}