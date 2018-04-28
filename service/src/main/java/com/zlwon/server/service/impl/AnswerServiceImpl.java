package com.zlwon.server.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.dto.pc.answer.QueryMyAnswerByCenterPage;
import com.zlwon.rdb.dao.AnswerMapper;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.server.service.AnswerService;
import com.zlwon.vo.pc.answer.AnswerDetailVo;
import com.zlwon.vo.pc.answer.AnswerQuestionDetailVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提问回答ServiceImpl
 * @author yangy
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerMapper answerMapper;
	
	/**
	 * 根据ID查询提问回答
	 * @param id
	 * @return
	 */
	@Override
	public Answer findAnswerById(Integer id){
		Answer temp = answerMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 新增提问回答
	 * @param record
	 * @return
	 */
	@Override
	public int insertAnswer(Answer record){
		int count = answerMapper.insertSelective(record);
		return count;
	}
	
	/**
     * 根据问题ID分页查询回答
     * @param form
     * @return
     */
	@Override
	public PageInfo<AnswerDetailVo> findAnswerByquestionId(QueryAnswerByQuestionIdDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<AnswerDetailVo> list = answerMapper.selectAnswerByquestionId(form);
		PageInfo<AnswerDetailVo> result = new PageInfo<AnswerDetailVo>(list);
		return result;
	}
	
	/**
     * 分页查询我的回答-个人中心
     * @param form
     * @return
     */
	@Override
	public PageInfo<AnswerQuestionDetailVo> findMyAnswerByCenterPage(QueryMyAnswerByCenterPage form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<AnswerQuestionDetailVo> list = answerMapper.selectMyAnswerByCenterPage(form);
		PageInfo<AnswerQuestionDetailVo> result = new PageInfo<AnswerQuestionDetailVo>(list);
		return result;
	}
	
	/**
     * 查询我的回答数量-个人中心
     * @param userId
     * @return
     */
	@Override
    public int countMyAnswerByCenter(Integer userId){
    	int count = answerMapper.countMyAnswerByCenter(userId);
    	return count;
    }
	
	/**
     * 根据回答ID删除回答
     * @param id
     * @return
     */
	@Override
    public int deleteAnswer(Integer id){
    	int count = answerMapper.deleteByPrimaryKey(id);
    	return count;
    }
}
