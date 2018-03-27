package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.ProcessingAdvice;

/**
 * 加工建议service
 * @author yuand
 *
 */
public interface ProcessingAdviceService {

	/**
	 * 分页得到所有的加工建议
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<ProcessingAdvice> findAllProcessingAdvice(Integer pageIndex, Integer pageSize);

	/**
	 * 管理员添加加工建议
	 * @param processingAdvice
	 * @return
	 */
	int saveProcessingAdvice(ProcessingAdvice processingAdvice);

	/**
	 * 更新加工建议，根据主键id
	 * @param processingAdvice
	 * @return
	 */
	int alterProcessingAdviceById(ProcessingAdvice processingAdvice);

	/**
	 * 根据加工建议id，得到加工建议详情
	 * @param id
	 * @return
	 */
	ProcessingAdvice findProcessingAdviceById(Integer id);

	/**
	 * 删除加工建议，根据加工建议id
	 * @param id
	 * @return
	 */
	int removeProcessingAdviceById(Integer id);

	/**
	 * 修改指定加工建议为审核成功
	 * @param id
	 * @return
	 */
	int alterProcessingAdviceToSuccessById(Integer id);

}
