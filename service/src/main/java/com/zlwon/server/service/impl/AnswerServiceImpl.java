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
import com.zlwon.dto.api.answer.QueryAnswerByQuestionIdWCDto;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.dto.pc.answer.QueryInvitateAnswerUsersDto;
import com.zlwon.dto.pc.answer.QueryMyAnswerByCenterPage;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.AnswerMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.server.service.AnswerService;
import com.zlwon.vo.answer.AnswerListVo;
import com.zlwon.vo.pc.answer.AnswerDetailVo;
import com.zlwon.vo.pc.answer.AnswerQuestionDetailVo;
import com.zlwon.vo.pc.answer.InvitateAnswerDetailVo;

/**
 * 提问回答ServiceImpl
 * @author yangy
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerMapper answerMapper;
	
	@Autowired
	private InformMapper informMapper;
	
	@Autowired
	private QuestionsMapper questionsMapper;
	
	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
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
	@Transactional
	public int insertAnswer(Answer record){
		
		//验证问题是否存在
		Questions quesValid = questionsMapper.selectByPrimaryKey(record.getQid());
		if(quesValid == null){
			throw new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//新增回答
		int count = answerMapper.insertSelective(record);
		
		//添加通知消息消息
		Inform recordInfo = new Inform();
		recordInfo.setCreateTime(new Date());
		recordInfo.setIid(record.getId());
		recordInfo.setUid(quesValid.getUid());
		recordInfo.setReadStatus((byte) 0);
		recordInfo.setStatus((byte) 1);
		recordInfo.setType((byte) 7);
		
		int informCount = informMapper.insertSelective(recordInfo);
		if(informCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//增加积分
		int upCount = customerMapper.updateIntegrationByUid(record.getUid(), IntegrationDeatilCode.INSERT_ANSWER.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.INSERT_ANSWER.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.INSERT_ANSWER.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.INSERT_ANSWER.getNum());
		interDeatil.setChangeType(1);
		interDeatil.setUid(record.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
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
     * 根据问题ID分页查询回答
     * 小程序端
     * @param form
     * @return
     */
	@Override
	public PageInfo<AnswerDetailVo> findWCAnswerByquestionId(QueryAnswerByQuestionIdWCDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<AnswerDetailVo> list = answerMapper.selectWCAnswerByquestionId(form);
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
	
	/**
     * 修改回答
     * @param record
     * @return
     */
	@Override
    public int updateAnswer(Answer record){
    	int count = answerMapper.updateByPrimaryKeySelective(record);
    	return count;
    }

	/**
	 * 得到所有问答信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageInfo findAllAnswerPage(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<AnswerListVo>  list = answerMapper.selectAllAnswerPage();
		return new  PageInfo<>(list);
	}

	/**
	 * 设置回答信息为驳回
	 * @param id 回答id
	 * @param content 驳回内容
	 * @return
	 */
	@Transactional
	public int alterAnswerFailed(Integer id, String content) {
		Answer answer = answerMapper.selectByPrimaryKey(id);
		if(answer == null  ||   answer.getExamine() == 2){
			throw   new  CommonException(answer == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_EXAMINE_FAILED);
		}
		//修改回答为驳回
		answer.setExamine(2);
		answerMapper.updateByPrimaryKeySelective(answer);
		
		//减去积分
		customerMapper.updateIntegrationByUid(answer.getUid(), IntegrationDeatilCode.REJECT_ANSWER.getNum());
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.REJECT_ANSWER.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.REJECT_ANSWER.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.REJECT_ANSWER.getNum());
		interDeatil.setChangeType(0);
		interDeatil.setUid(answer.getUid());
		interDeatil.setCreateTime(new Date());
		integrationDeatilMapMapper.insertSelective(interDeatil);
		
		//添加通知消息
		Inform record = new Inform();
		record.setContent(content);
		record.setCreateTime(new  Date());
		record.setIid(answer.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 0);
		record.setType((byte) 2);
		record.setUid(answer.getUid());
		return informMapper.insertSelective(record);
	}

	/**
	 * 得到回答驳回信息
	 * @param id 回答id
	 * @return
	 */
	public String findAnswerFailedContent(Integer id) {
		Answer answer = answerMapper.selectByPrimaryKey(id);
		if(answer == null  ||  answer.getExamine() != 2){
			throw   new  CommonException(answer == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_NOT_EXAMINE_FAILED);
		}
		
		//查看驳回信息
		Inform re = informMapper.selectAnswerFailedByIid(id);
		if(re == null){
			throw   new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		return re.getContent();
	}
	
	/**
	 * 根据信息ID和信息类型查询推荐邀请回答用户
	 * @param infoId
	 * @param type
	 * @return
	 */
	@Override
	public List<InvitateAnswerDetailVo> findInvitateAnswerUserList(Integer infoId,Integer type){
		List<InvitateAnswerDetailVo> list = answerMapper.selectInvitateAnswerUserList(infoId,type);
		return list;
	}
	
	/**
	 * 分页查询推荐邀请回答用户
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<InvitateAnswerDetailVo> findInvitateAnswerUserPage(QueryInvitateAnswerUsersDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<InvitateAnswerDetailVo> list = answerMapper.selectInvitateAnswerUserList(form.getInfoId(),form.getType());
		PageInfo<InvitateAnswerDetailVo> result = new PageInfo<InvitateAnswerDetailVo>(list);
		return result;
	}
	
	/**
	 * 分页查询搜索符合条件回答用户
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<InvitateAnswerDetailVo> findInvitateAnswerUserBySearch(QueryInvitateAnswerUsersDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<InvitateAnswerDetailVo> list = answerMapper.selectInvitateAnswerUserBySearch(form.getUserName());
		PageInfo<InvitateAnswerDetailVo> result = new PageInfo<InvitateAnswerDetailVo>(list);
		return result;
	}
}
