package com.zlwon.server.service;

import com.zlwon.rdb.entity.AnswerRecord;

/**
 * 回答点赞记录Service
 * @author yangy
 *
 */

public interface AnswerRecordService {

	/**
     * 根据用户ID和回答ID查询点赞记录
     * @param userId
     * @param answerId
     * @return
     */
    AnswerRecord findAnswerRecordByUserAnswer(Integer userId,Integer answerId);
    
    /**
     * 新增回答点赞记录
     * @param record
     * @return
     */
    int insertAnswerRecord(AnswerRecord record);
    
    /**
     * 根据ID删除回答点赞记录
     * @param id
     * @return
     */
    int deleteAnswerRecordById(Integer id);
}
