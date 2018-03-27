package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.ProcessingAdviceClass;

public interface ProcessingAdviceClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessingAdviceClass record);

    int insertSelective(ProcessingAdviceClass record);

    ProcessingAdviceClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessingAdviceClass record);

    int updateByPrimaryKey(ProcessingAdviceClass record);

    
    /**
     * 得到所有加工建议主题
     * @return
     */
	List<ProcessingAdviceClass> selectAllProcessingAdviceClass();

	/**
	 * 根据主题，得到加工建议主题信息
	 * @param title
	 * @return
	 */
	ProcessingAdviceClass selectByTitle(String title);
}