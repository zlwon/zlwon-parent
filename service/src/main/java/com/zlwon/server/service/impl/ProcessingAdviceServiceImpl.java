package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.processAdvice.QueryMyProcessAdviceByPageDto;
import com.zlwon.dto.pc.specification.PcSearchProcessAdvicePageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ProcessingAdviceMapper;
import com.zlwon.rdb.entity.ProcessingAdvice;
import com.zlwon.server.service.ProcessingAdviceService;
import com.zlwon.vo.pc.processAdvice.ProcessingAdviceDetailVo;
import com.zlwon.vo.processingAdvice.ProcessingAdviceVo;

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
	 *  根据物性id，得到所有加工建议，分页查找
	 */
	@Override
	public PageInfo<ProcessingAdviceVo> findAllProcessingAdviceById(Integer pageIndex, Integer pageSize,Integer  sid) {
		PageHelper.startPage(pageIndex, pageSize);
		//主题名称存在的才会显示（zl_processing_advice_class，zl_processing_advice），内连接
		List<ProcessingAdviceVo> list= processingAdviceMapper.selectAllProcessingAdviceJoinClass(sid);
		return new  PageInfo<ProcessingAdviceVo>(list);
	}

	
	/**
	 * 根据加工建议id，得到加工建议详情
	 */
	@Override
	public ProcessingAdviceVo findProcessingAdviceById(Integer id) {
		return processingAdviceMapper.selectProcessingAdviceById(id);
	}
	
	/**
	 * 添加加工建议
	 * @param processingAdvice 需要判断 uid，
	 */
	@Override
	public int saveProcessingAdvice(ProcessingAdvice processingAdvice) {
		if(processingAdvice.getUid() == null || "".equals(processingAdvice.getUid()) || 0 == processingAdvice.getUid()){
			processingAdvice.setUid(0);//用户ID,如果为0表示官方数据
			processingAdvice.setExamine(1);//用户创建数据审核结果，0未审核，1审核通过
		}else{
			processingAdvice.setExamine(0);//用户创建数据审核结果，0未审核，1审核通过
		}
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

	/**
	 * 根据物性ID分页查询加工建议
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<ProcessingAdviceDetailVo> findProcessAdviceBySpecIdPage(PcSearchProcessAdvicePageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<ProcessingAdviceDetailVo> list = processingAdviceMapper.selectProcessAdviceBySpecId(form);
		PageInfo<ProcessingAdviceDetailVo> result = new PageInfo<ProcessingAdviceDetailVo>(list);
		return result;
	}
	
	/**
	 * 根据物性ID查询加工建议
	 * @param specId
	 * @return
	 */
	@Override
	public List<ProcessingAdviceDetailVo> findProcessAdviceBySpecIdList(Integer specId){
		List<ProcessingAdviceDetailVo> list = processingAdviceMapper.selectProcessAdviceBySpecIdList(specId);
		return list;
	}

	/**
	 * 根据用户ID查询加工建议（所有）
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<ProcessingAdviceDetailVo> findProcessAdviceByUserIdPage(QueryMyProcessAdviceByPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<ProcessingAdviceDetailVo> list = processingAdviceMapper.selectProcessAdviceByUserId(form);
		PageInfo<ProcessingAdviceDetailVo> result = new PageInfo<ProcessingAdviceDetailVo>(list);
		return result;
	}
}
