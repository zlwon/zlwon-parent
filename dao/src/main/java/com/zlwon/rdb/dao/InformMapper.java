package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.Inform;

public interface InformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inform record);

    int insertSelective(Inform record);

    Inform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inform record);

    int updateByPrimaryKey(Inform record);

    /**
     * 根据提问信息id，得到驳回提问通知信息
     * @param iid 提问信息id
     * @return
     */
	Inform selectQuestionsFailedByIid(Integer iid);

	 /**
     * 根据回答信息id，得到避讳回答通知信息
     * @param iid 回答信息id
     * @return
     */
	Inform selectAnswerFailedByIid(Integer iid);
}