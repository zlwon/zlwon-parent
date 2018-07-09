package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.question.QueryDefineClearQuestionsDto;
import com.zlwon.dto.api.question.QueryQuestionListByInfoIdDto;
import com.zlwon.dto.pc.questions.QueryAllSpecifyQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAttentionMeQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAttentionQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.exception.CommonException;
import com.zlwon.pojo.QuestionsMessage;
import com.zlwon.pojo.constant.MessageConstant;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.dao.SpecificationMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.utils.JsonUtils;
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
	@Autowired
	private CustomerMapper   customerMapper;
	@Autowired
	private SpecificationMapper   specificationMapper;
	@Autowired
	private ApplicationCaseMapper   applicationCaseMapper;
	@Autowired
	private MailService mailService;
	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	@Autowired
	private  KafkaTemplate<String, String>  kafkaTemplate;
	@Value("${kafka.topic.add.questions}")
	private  String    addQuestions;
	
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
	@Transactional
	public int insertQuestions(Questions record){
		
		int count = questionsMapper.insertSelective(record);
		
		//增加积分
		int upCount = customerMapper.updateIntegrationByUid(record.getUid(), IntegrationDeatilCode.INSERT_QUESTION.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.INSERT_QUESTION.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.INSERT_QUESTION.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.INSERT_QUESTION.getNum());
		interDeatil.setChangeType(1);
		interDeatil.setUid(record.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		sendMessageByAddQuestions(record.getId());
		
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
	 * 分页查询特定类型的问题（可指定具体）
	 * 小程序端
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<QuestionsDetailVo> findWCSpecifyQuestions(QueryDefineClearQuestionsDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<QuestionsDetailVo> list = questionsMapper.selectWCSpecifyQuestions(form);
		PageInfo<QuestionsDetailVo> result = new PageInfo<QuestionsDetailVo>(list);
		return result;
	}
	
	/**
	 * 根据信息ID查询提问列表
	 * @param form
	 * @return
	 */
	@Override
	public List<QuestionsDetailVo> findQuestionsLsitByInfoId(QueryQuestionListByInfoIdDto form){
		List<QuestionsDetailVo> list = questionsMapper.selectQuestionsLsitByInfoId(form);
		return list;
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
		
		//取消积分
		customerMapper.updateIntegrationByUid(questions.getUid(), IntegrationDeatilCode.REJECT_QUESTION.getNum());
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.REJECT_QUESTION.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.REJECT_QUESTION.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.REJECT_QUESTION.getNum());
		interDeatil.setChangeType(0);
		interDeatil.setUid(questions.getUid());
		interDeatil.setCreateTime(new Date());
		integrationDeatilMapMapper.insertSelective(interDeatil);
		
		
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

	/**
	 * 管理员发送邀请问答邮件
	 * @param uids 被邀请的用户id，多个逗号隔开
	 * @param id 问题id
	 * @return
	 */
	public int sendAnInvitationEmail(String uids, Integer id) {
		//根据问题id，得到提问用户信息
		Questions questions = questionsMapper.selectByPrimaryKey(id);
		if(questions == null  || questions.getExamine() != 1){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//得到当前用户信息
		Customer re = customerMapper.selectCustomerById(questions.getUid());
		
		//根据用户ID拼接字符串查询用户信息
		List<Customer> userList = customerMapper.selectCustomerByidStr(uids);
		//查询信息来源
		String source = "";
		Integer infoClass = questions.getInfoClass();//问题针对的信息类别：1:物性、2:案例
		Integer iid = questions.getIid();//信息id
		if(questions.getInfoClass() == 1){  //物性
			Specification myspec = specificationMapper.findSpecificationById(iid);
			source = myspec.getName();
		}else{
			ApplicationCase myCase = applicationCaseMapper.findAppCaseById(iid);
			source = myCase.getTitle();
		}
		
		if(userList != null && userList.size() > 0){
			for(Customer temp : userList){
				Map<String, Object> model = new HashMap<String, Object>();
		        model.put("nickname", temp.getNickname());  //被邀请者昵称
		        model.put("headerimg", temp.getHeaderimg());  //被邀请者头像
		        model.put("quesNickName", re.getNickname());  //邀请者昵称
		        model.put("title", questions.getTitle());  //问题
		        model.put("source", source);  //来源
		        if(infoClass == 1){  //物性
		        	model.put("pageUrl", "http://www.zlwon.com/page/space/detail.html?id="+iid);
		        }else{
		        	model.put("pageUrl", "http://www.zlwon.com/page/case/detail.html?id="+iid);
		        }
				
				if(StringUtils.isNotBlank(temp.getEmail())){
					mailService.sendVelocityTemplateMail(temp.getEmail(), "邀请您回答", "invitateEmail.vm",model);
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * 新增提问，发送消息到mq，
	 * @param id
	 */
	private  void   sendMessageByAddQuestions(Integer  id){
		QuestionsMessage applicationCaseMessage = new QuestionsMessage(id, MessageConstant.ADD_QUESTIONS_TYPE);
		kafkaTemplate.send(addQuestions, JsonUtils.objectToJson(applicationCaseMessage));
	}
	
}
