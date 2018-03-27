package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.consultation.CaseConsultationPageDto;
import com.zlwon.dto.consultation.CaseUidPageDto;
import com.zlwon.rdb.entity.Consultation;
import com.zlwon.vo.consultation.ConsultationDetailVo;

/**
 * 用户咨询Mapper
 * @author yangy
 *
 */

public interface ConsultationMapper {

	/**
	 * 根据用户咨询ID查询用户咨询信息
	 * @param id
	 * @return
	 */
	Consultation findConsultationById(Integer id);
	
	/**
	 * 根据用户咨询ID查询用户咨询信息详情
	 * @param id
	 * @return
	 */
	ConsultationDetailVo findConsultationDetailById(Integer id);
	
	/**
	 * 根据工程师ID查询用户对他的所有咨询
	 * @param caseUid
	 * @return
	 */
	List<ConsultationDetailVo> findAllConsultationByCaseUid(CaseUidPageDto caseUid);
	
	/**
	 * 根据工程师ID查询该工程师尚未回答过的咨询
	 * @param caseUid
	 * @return
	 */
	List<ConsultationDetailVo> findNoAnswerConsultationByCaseUid(CaseUidPageDto caseUid);
	
	/**
	 * 根据工程师ID查询该工程师回答过的咨询
	 * @param caseUid
	 * @return
	 */
	List<ConsultationDetailVo> findAnswerConsultationByCaseUid(CaseUidPageDto caseUid);
	
	/**
	 * 根据案例ID和openId查询该案例相关的咨询
	 * @param caseInfo
	 * @return
	 */
	List<ConsultationDetailVo> findConsultationByCaseIdList(CaseConsultationPageDto caseInfo);
	
	/**
	 * 新增用户咨询
	 * @param addInfo
	 * @return
	 */
	int insertConsultation(Consultation addInfo);
	
	/**
	 * 回复用户咨询
	 * @param updateInfo
	 * @return
	 */
	int updateConsultationReply(Consultation updateInfo);
}
