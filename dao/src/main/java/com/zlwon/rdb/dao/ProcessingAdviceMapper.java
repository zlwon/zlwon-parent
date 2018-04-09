package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.nosql.entity.SpecProcessAdvice;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.vo.processingAdvice.ProcessingAdviceVo;

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
	List<ProcessingAdviceVo> selectAllProcessingAdviceJoinClass();

	/**
	 * 根据id数组，得到加工建议信息
	 * @param ids
	 * @return
	 */
	List<SpecProcessAdvice> selectByPrimaryKeys(@Param("ids")Integer[] ids);
}