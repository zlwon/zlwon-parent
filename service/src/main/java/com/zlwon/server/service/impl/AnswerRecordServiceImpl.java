package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.AnswerMapper;
import com.zlwon.rdb.dao.AnswerRecordMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.AnswerRecord;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
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
	
	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
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
	@Transactional
    public int insertAnswerRecord(AnswerRecord record){
		
		//新增点赞记录
    	int count = answerRecordMapper.insertSelective(record);
    	
    	//更新点赞数
    	Answer answerInfo = answerMapper.selectByPrimaryKey(record.getAnswerId());
    	answerInfo.setSupportNums(answerInfo.getSupportNums()+1);
    	
    	int answerCount = answerMapper.updateByPrimaryKeySelective(answerInfo);
    	
    	//增加回答者积分（非点赞者）
		int upCount = customerMapper.updateIntegrationByUid(answerInfo.getUid(), IntegrationDeatilCode.ANSWER_LIKE.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.ANSWER_LIKE.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.ANSWER_LIKE.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.ANSWER_LIKE.getNum());
		interDeatil.setChangeType(1);
		interDeatil.setUid(answerInfo.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
    	
    	return count;
    }
    
    /**
     * 根据ID删除回答点赞记录
     * @param id
     * @return
     */
	@Transactional
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
    	
    	//减少回答者积分（非点赞者）
		int upCount = customerMapper.updateIntegrationByUid(answerInfo.getUid(), IntegrationDeatilCode.ANSWER_CANCEL_LIKE.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.ANSWER_CANCEL_LIKE.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.ANSWER_CANCEL_LIKE.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.ANSWER_CANCEL_LIKE.getNum());
		interDeatil.setChangeType(0);
		interDeatil.setUid(answerInfo.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
    	
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

	/**
	 * 删除指定推介邀请回答
	 * @param id 推介邀请回答id
	 * @return
	 */
	@Override
	public int removeAnswerRecordById(Integer id) {
		return answerRecordMapper.deleteByPrimaryKey(id);
	}
	
	
	
}
