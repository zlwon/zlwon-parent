package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.processAdvice.QueryMyProcessAdviceByPageDto;
import com.zlwon.dto.pc.specification.PcSearchProcessAdvicePageDto;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.vo.pc.processAdvice.ProcessingAdviceDetailVo;
import com.zlwon.vo.processingAdvice.ProcessingAdviceVo;

/**
 * 加工建议service
 * @author yuand
 *
 */
public interface ProcessingAdviceService {

	/**
	 * 根据物性id，得到所有加工建议，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param sid 物性id
	 * @return
	 */
	PageInfo<ProcessingAdviceVo> findAllProcessingAdviceById(Integer pageIndex, Integer pageSize,Integer  sid);

	
	/**
	 * 根据加工建议id，得到加工建议详情
	 * @param id
	 * @return
	 */
	ProcessingAdviceVo findProcessingAdviceById(Integer id);
	
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


	/**
	 * 根据物性ID分页查询加工建议
	 * @param form
	 * @return
	 */
	PageInfo<ProcessingAdviceDetailVo> findProcessAdviceBySpecIdPage(PcSearchProcessAdvicePageDto form);
	
	/**
	 * 根据用户ID查询加工建议（所有）
	 * @param form
	 * @return
	 */
	PageInfo<ProcessingAdviceDetailVo> findProcessAdviceByUserIdPage(QueryMyProcessAdviceByPageDto form);
}
