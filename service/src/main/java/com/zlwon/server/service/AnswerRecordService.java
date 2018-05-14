package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
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

    /**
	 * 得到所有案例(物性)推介邀请回答人信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param type 0:查询所有1:物性2:案例
	 * @return
	 */
	PageInfo findAllAnswerRecord(Integer pageIndex, Integer pageSize, Integer type);

	/**
	 * 删除指定推介邀请回答
	 * @param id 推介邀请回答id
	 * @return
	 */
	int removeAnswerRecordById(Integer id);
}
