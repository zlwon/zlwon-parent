package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.Inform;

public interface InformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inform record);

    int insertSelective(Inform record);

    Inform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inform record);

    int updateByPrimaryKey(Inform record);

    /**
     * 根据提问信息id，得到提问通知信息
     * @param iid 提问信息id
     * @return
     */
	Inform selectQuestionsFailedByIid(Integer iid);
}