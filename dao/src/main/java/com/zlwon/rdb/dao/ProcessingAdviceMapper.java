package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.ProcessingAdvice;

public interface ProcessingAdviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessingAdvice record);

    int insertSelective(ProcessingAdvice record);

    ProcessingAdvice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessingAdvice record);

    int updateByPrimaryKey(ProcessingAdvice record);

    /**
     * 得到所有加工建议(zl_processing_advice_class，zl_processing_advice内连接)
     * @return
     */
	List<ProcessingAdvice> selectAllProcessingAdviceJoinClass();
}