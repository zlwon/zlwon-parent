package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.questions.QueryAllSpecifyQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAttentionMeQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAttentionQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseQuestionAndAnswerVo;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;
import com.zlwon.vo.questions.QuestionsListVo;

/**
 * 提问ServiceImpl
 * @author yangy
 *
 */

@Service
public class QuestionsServiceImpl implements QuestionsService {

	@Autowired
	private QuestionsMapper questionsMapper;
	@Autowired
	private InformMapper   informMapper;
	
	/**
	 * 根据提问ID查询提问
	 * @param id
	 * @return
	 */
	@Override
	public Questions findQuestionsById(Integer id){
		Questions temp = questionsMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 根据用户ID查询其所有的提问
	 * @param uid
	 * @return
	 */
	@Override
	public Questions findQuestionsByUId(Integer uid){
		Questions temp = questionsMapper.findQuestionsByUId(uid);
		return temp;
	}
	
	/**
	 * 根据信息ID查询问题数量
	 * @param infoId
	 * @param type
	 * @return
	 */
	@Override
	public int countQuestionsByInfoId(Integer infoId,Integer type){
		int count = questionsMapper.countQuestionsByInfoId(infoId, type);
		return count;
	}
	
	/**
	 * 新增提问
	 * @param record
	 * @return
	 */
	@Override
	public int insertQuestions(Questions record){
		int count = questionsMapper.insertSelective(record);
		return count;
	}
	
	/**
	 * 分页查询我的提问问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findQuestionsByMyLaunch(QueryMyLaunchQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsByMyLaunch(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询我收藏的问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findQuestionsByMyCollect(QueryMyCollectQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsByMyCollect(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询我回答的问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findQuestionsByMyAnswer(QueryMyAnswerQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsByMyAnswer(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询我关注的人的问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findMyAttentionQuestions(QueryMyAttentionQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectMyAttentionQuestions(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询关注我的人的问题
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findAttentionMeQuestions(QueryAttentionMeQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectAttentionMeQuestions(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询特定类型的问题（可指定具体）
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findAllSpecifyQuestions(QueryAllSpecifyQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectAllSpecifyQuestions(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 根据问题ID查询问题详情
	 * @param questionId
	 * @param userId
	 * @return
	 */
	@Override
	public QuestionsDetailVo findSingleQuestionDetailById(Integer questionId,Integer userId){
		QuestionsDetailVo temp = questionsMapper.selectSingleQuestionDetailById(questionId,userId);
		return temp;
	}

	/**
	 * 得到首页最热门的问答(根据提问回答最多查询，最多4个)
	 * @return
	 */
	@Override
	public List<IndexHotApplicationCaseQuestionAndAnswerVo> findHotQuestions() {
		return questionsMapper.selectHotQuestions();
	}
	
	/**
	 * 查询我的提问问题数量
	 * @param userId
	 * @return
	 */
	@Override
	public int findQuestionsCountByMyLaunch(Integer userId){
		int count = questionsMapper.selectQuestionsCountByMyLaunch(userId);
		return count;
	}

	
	/**
	 * 得到所有提问信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageInfo findAllQuestionsPage(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<QuestionsListVo>  list = questionsMapper.selectAllQuestions();
		return new  PageInfo<>(list);
	}

	/**
	 * 设置提问信息为通过
	 * @param id 提问id
	 * @return
	 */
	@Transactional
	public int alterQuestionsSuccess(Integer id) {
		Questions questions = questionsMapper.selectByPrimaryKey(id);
		if(questions == null ||  questions.getExamine() == 1){
			throw   new  CommonException(questions == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_EXAMINE_SUCCESS);
		}
		//设置提问信息为通过
		questions.setExamine(1);
		questionsMapper.updateByPrimaryKeySelective(questions);
		//添加到通知表
		Inform record = new Inform();
		record.setCreateTime(new  Date());
		record.setIid(questions.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 1);
		record.setType((byte) 1);
		record.setUid(questions.getUid());
		return  informMapper.insertSelective(record);
	}

	/**
	 * 设置提问信息为驳回
	 * @param id 提问id
	 * @param content 驳回内容
	 * @return
	 */
	@Transactional
	@Override
	public int alterQuestionsFailed(Integer id, String content) {
		Questions questions = questionsMapper.selectByPrimaryKey(id);
		if(questions == null ||   questions.getExamine() == 2){
			throw   new  CommonException(questions == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_EXAMINE_FAILED);
		}
		//设置提问信息为驳回
		questions.setExamine(2);
		questionsMapper.updateByPrimaryKeySelective(questions);
		//添加到通知表
		Inform record = new Inform();
		record.setCreateTime(new  Date());
		record.setIid(questions.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 0);
		record.setContent(content);
		record.setType((byte) 1);
		record.setUid(questions.getUid());
		return  informMapper.insertSelective(record);
	}

	/**
	 * 得到提问驳回信息
	 * @param id 提问id
	 * @return
	 */
	public String findQuestionsFailedContent(Integer id) {
		Questions questions = questionsMapper.selectByPrimaryKey(id);
		if(questions == null ||  questions.getExamine() != 2){
			throw   new  CommonException(questions == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_NOT_EXAMINE_FAILED);
		}
		Inform  inform = informMapper.selectQuestionsFailedByIid(id);
		if(inform == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		return inform.getContent();
	}
	
	
	
}
