package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.ProcessingAdviceClass;

/**
 * 物性表加工建议主题service
 * @author yuand
 *
 */
public interface ProcessingAdviceClassService {

	/**
	 * 得到所有加工建议主题，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<ProcessingAdviceClass> findAllProcessingAdviceClass(Integer pageIndex, Integer pageSize);

	/**
	 * 添加加工建议主题，需要判断主题是否存在
	 * @return
	 */
	int saveProcessingAdviceClass(ProcessingAdviceClass  processingAdviceClass);

	/**
	 * 根据加工建议主题id，得到加工建议主题信息
	 * @param id
	 * @return
	 */
	ProcessingAdviceClass findProcessingAdviceClassById(Integer id);

	/**
	 * 执行更新操作，需要先判断主题信息是否存在，然后在判断主题是否存在
	 * @param processingAdviceClass
	 * @return
	 */
	int alterProcessingAdviceClassById(ProcessingAdviceClass processingAdviceClass);

	/**
	 * 根据主题信息id，删除主题信息
	 * @param id
	 * @return
	 */
	int removeProcessingAdviceClassById(Integer id);

}
