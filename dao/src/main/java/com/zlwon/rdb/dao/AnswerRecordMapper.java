package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.AnswerRecord;
import com.zlwon.vo.answerRecord.AnswerRecordListVo;

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

    
    /**
	 * 得到所有案例(物性)推介邀请回答人信息
	 * @param type 0:查询所有1:物性2:案例
	 * @return
	 */
	List<AnswerRecordListVo> selectAllAnswerRecord(@Param("type")Integer type);
}