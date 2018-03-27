package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ProcessingAdviceMapper;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.server.service.ProcessingAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加工建议
 * @author yuand
 *
 */
@Service
public class ProcessingAdviceServiceImpl implements ProcessingAdviceService {

	@Autowired
	private  ProcessingAdviceMapper  processingAdviceMapper;
	
	/**
	 * 分页得到所有的加工建议
	 */
	@Override
	public PageInfo<ProcessingAdvice> findAllProcessingAdvice(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		//主题名称存在的才会显示（zl_processing_advice_class，zl_processing_advice），内连接
		List<ProcessingAdvice> list= processingAdviceMapper.selectAllProcessingAdviceJoinClass();
		return new  PageInfo<ProcessingAdvice>(list);
	}

	/**
	 * 管理员添加加工建议
	 */
	@Override
	public int saveProcessingAdvice(ProcessingAdvice processingAdvice) {
		processingAdvice.setUid(0);//用户ID,如果为0表示官方数据
		processingAdvice.setExamine(1);//用户创建数据审核结果，0未审核，1审核通过
		return processingAdviceMapper.insertSelective(processingAdvice);
	}

	/**
	 * 更新加工建议，根据主键id
	 */
	@Override
	public int alterProcessingAdviceById(ProcessingAdvice processingAdvice) {
		ProcessingAdvice record = processingAdviceMapper.selectByPrimaryKey(processingAdvice.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		return  processingAdviceMapper.updateByPrimaryKeySelective(processingAdvice);
	}

	/**
	 * 根据加工建议id，得到加工建议详情
	 */
	@Override
	public ProcessingAdvice findProcessingAdviceById(Integer id) {
		return processingAdviceMapper.selectByPrimaryKey(id);
	}

	/**
	 * 删除加工建议，根据加工建议id
	 */
	@Override
	public int removeProcessingAdviceById(Integer id) {
		return processingAdviceMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改指定加工建议为审核成功
	 */
	@Override
	public int alterProcessingAdviceToSuccessById(Integer id) {
		ProcessingAdvice processingAdvice = processingAdviceMapper.selectByPrimaryKey(id);
		if(processingAdvice == null  || processingAdvice.getExamine() == 1){
			throw   new  CommonException(processingAdvice == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATA_IS_EXAMINE);
		}
		ProcessingAdvice record = new ProcessingAdvice();
		record.setId(id);
		record.setExamine(1);
		return processingAdviceMapper.updateByPrimaryKeySelective(record );
	}

}
