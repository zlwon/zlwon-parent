package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.dto.pc.specification.PcSearchProcessAdvicePageDto;
import com.zlwon.nosql.entity.SpecProcessAdvice;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.vo.pc.processAdvice.ProcessingAdviceDetailVo;
import com.zlwon.vo.processingAdvice.ProcessingAdviceVo;

public interface ProcessingAdviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessingAdvice record);

    int insertSelective(ProcessingAdvice record);

    ProcessingAdvice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessingAdvice record);

    int updateByPrimaryKey(ProcessingAdvice record);

    /**
     * 根据物性id，得到所有加工建议
     * @param sid
     * @return
     */
	List<ProcessingAdviceVo> selectAllProcessingAdviceJoinClass(Integer sid);

	/**
	 *  根据加工建议id，得到加工建议详情
	 * @param id
	 * @return
	 */
	ProcessingAdviceVo selectProcessingAdviceById(Integer id);
	
	/**
	 * 根据id数组，得到加工建议信息
	 * @param ids
	 * @return
	 */
	List<SpecProcessAdvice> selectByPrimaryKeys(@Param("ids")Integer[] ids);
	
	/**
	 * 根据物性ID查询加工建议（已审核通过）
	 * @param form
	 * @return
	 */
	List<ProcessingAdviceDetailVo> selectProcessAdviceBySpecId(PcSearchProcessAdvicePageDto form);
}