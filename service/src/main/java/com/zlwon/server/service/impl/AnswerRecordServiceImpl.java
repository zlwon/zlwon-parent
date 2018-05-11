package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.dao.AnswerMapper;
import com.zlwon.rdb.dao.AnswerRecordMapper;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.AnswerRecord;
import com.zlwon.server.service.AnswerRecordService;
import com.zlwon.vo.answerRecord.AnswerRecordListVo;

/**
 * 回答点赞记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class AnswerRecordServiceImpl implements AnswerRecordService {

	@Autowired
	private AnswerRecordMapper answerRecordMapper;
	
	@Autowired
	private AnswerMapper answerMapper;
	
	/**
     * 根据用户ID和回答ID查询点赞记录
     * @param userId
     * @param answerId
     * @return
     */
	@Override
    public AnswerRecord findAnswerRecordByUserAnswer(Integer userId,Integer answerId){
    	AnswerRecord temp = answerRecordMapper.selectAnswerRecordByUserAnswer(userId, answerId);
    	return temp;
    }
    
    /**
     * 新增回答点赞记录
     * @param record
     * @return
     */
	@Override
    public int insertAnswerRecord(AnswerRecord record){
		
		//新增点赞记录
    	int count = answerRecordMapper.insertSelective(record);
    	
    	//更新点赞数
    	Answer answerInfo = answerMapper.selectByPrimaryKey(record.getAnswerId());
    	answerInfo.setSupportNums(answerInfo.getSupportNums()+1);
    	
    	int answerCount = answerMapper.updateByPrimaryKeySelective(answerInfo);
    	
    	return count;
    }
    
    /**
     * 根据ID删除回答点赞记录
     * @param id
     * @return
     */
	@Override
    public int deleteAnswerRecordById(Integer id){
		
		//根据点赞记录查找回答信息
		AnswerRecord record = answerRecordMapper.selectByPrimaryKey(id);
		
		Answer answerInfo = answerMapper.selectByPrimaryKey(record.getAnswerId());
    	if(answerInfo.getSupportNums() > 0){
    		answerInfo.setSupportNums(answerInfo.getSupportNums()-1);
    		int answerCount = answerMapper.updateByPrimaryKeySelective(answerInfo);
    	}else{
    		return 0;
    	}
		
		//删除点赞记录
    	int count = answerRecordMapper.deleteByPrimaryKey(id);
    	
    	return count;
    }

	/**
	 * 得到所有案例(物性)推介邀请回答人信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param type 0:查询所有1:物性2:案例
	 * @return
	 */
	public PageInfo findAllAnswerRecord(Integer pageIndex, Integer pageSize, Integer type) {
		PageHelper.startPage(pageIndex, pageSize);
		List<AnswerRecordListVo>  list = answerRecordMapper.selectAllAnswerRecord(type);
		return new  PageInfo<>(list);
	}
	
	
}
