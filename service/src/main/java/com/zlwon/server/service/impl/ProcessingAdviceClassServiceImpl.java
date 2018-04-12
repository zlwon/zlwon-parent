package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ProcessingAdviceClassMapper;
import com.zlwon.rdb.entity.ProcessingAdviceClass;
import com.zlwon.server.service.ProcessingAdviceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物性表加工建议主题serviceImpl
 * @author yuand
 *
 */
@Service
public class ProcessingAdviceClassServiceImpl implements ProcessingAdviceClassService {

	@Autowired
	private  ProcessingAdviceClassMapper   processingAdviceClassMapper;

	/**
	 * 得到所有加工建议主题，分页查找
	 */
	@Override
	public PageInfo<ProcessingAdviceClass> findAllProcessingAdviceClass(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<ProcessingAdviceClass>  list = processingAdviceClassMapper.selectAllProcessingAdviceClass();
		return new  PageInfo<>(list);
	}

	/**
	 * 添加加工建议主题，需要判断主题是否存在
	 */
	@Override
	public int saveProcessingAdviceClass(ProcessingAdviceClass  processingAdviceClass) {
		//查看主题是否已存在
		ProcessingAdviceClass  record = processingAdviceClassMapper.selectByTitle(processingAdviceClass.getTitle());
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		//执行添加操作
		return  processingAdviceClassMapper.insert(processingAdviceClass);
	}

	/**
	 * 根据加工建议主题id，得到加工建议主题信息
	 */
	@Override
	public ProcessingAdviceClass findProcessingAdviceClassById(Integer id) {
		return processingAdviceClassMapper.selectByPrimaryKey(id);
	}

	/**
	 * 执行更新操作，需要先判断主题信息是否存在，然后在判断主题是否存在
	 */
	@Override
	public int alterProcessingAdviceClassById(ProcessingAdviceClass processingAdviceClass) {
		//判断主题信息是否存在
		ProcessingAdviceClass record = processingAdviceClassMapper.selectByPrimaryKey(processingAdviceClass.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//判断主题是否存在
		record = processingAdviceClassMapper.selectByTitle(processingAdviceClass.getTitle());
		if(record != null && record.getId() != processingAdviceClass.getId()){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		
		return processingAdviceClassMapper.updateByPrimaryKey(processingAdviceClass);
	}

	/**
	 * 根据主题信息id，删除主题信息
	 */
	@Override
	public int removeProcessingAdviceClassById(Integer id) {
		return processingAdviceClassMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 得到所有加工建议主题，不分页
	 */
	public List<ProcessingAdviceClass> findProcessingAdviceClassList() {
		return processingAdviceClassMapper.selectAllProcessingAdviceClass();
	}
	
	
}
