package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.consultation.CaseConsultationPageDto;
import com.zlwon.dto.consultation.CaseUidPageDto;
import com.zlwon.rdb.dao.ConsultationMapper;
import com.zlwon.rdb.entity.Consultation;
import com.zlwon.server.service.ConsultationService;
import com.zlwon.vo.consultation.ConsultationDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户咨询ServiceImpl
 * @author yangy
 *
 */

@Service
public class ConsultationServiceImpl implements ConsultationService {

	@Autowired
	private ConsultationMapper consultationMapper;
	
	/**
	 * 根据用户咨询ID查询用户咨询信息
	 * @param id
	 * @return
	 */
	@Override
	public Consultation findConsultationById(Integer id){
		Consultation temp = consultationMapper.findConsultationById(id);
		return temp;
	}
	
	/**
	 * 根据用户咨询ID查询用户咨询信息详情
	 * @param id
	 * @return
	 */
	@Override
	public ConsultationDetailVo findConsultationDetailById(Integer id){
		ConsultationDetailVo temp = consultationMapper.findConsultationDetailById(id);
		return temp;
	}
	
	/**
	 * 根据工程师ID查询用户对他的所有咨询
	 * @param caseUid
	 * @return
	 */
	@Override
	public PageInfo<ConsultationDetailVo> findAllConsultationByCaseUid(CaseUidPageDto caseUid){
		PageHelper.startPage(caseUid.getCurrentPage(), caseUid.getPageSize());
		List<ConsultationDetailVo> list = consultationMapper.findAllConsultationByCaseUid(caseUid);
		PageInfo<ConsultationDetailVo> info = new PageInfo<>(list);
		
		return info;
	}
	
	/**
	 * 根据工程师ID查询该工程师尚未回答过的咨询
	 * @param caseUid
	 * @return
	 */
	@Override
	public PageInfo<ConsultationDetailVo> findNoAnswerConsultationByCaseUid(CaseUidPageDto caseUid){
		PageHelper.startPage(caseUid.getCurrentPage(), caseUid.getPageSize());
		List<ConsultationDetailVo> list = consultationMapper.findNoAnswerConsultationByCaseUid(caseUid);
		PageInfo<ConsultationDetailVo> info = new PageInfo<>(list);
		
		return info;
	}
	
	/**
	 * 根据工程师ID查询该工程师回答过的咨询
	 * @param caseUid
	 * @return
	 */
	@Override
	public PageInfo<ConsultationDetailVo> findAnswerConsultationByCaseUid(CaseUidPageDto caseUid){
		PageHelper.startPage(caseUid.getCurrentPage(), caseUid.getPageSize());
		List<ConsultationDetailVo> list = consultationMapper.findAnswerConsultationByCaseUid(caseUid);
		PageInfo<ConsultationDetailVo> info = new PageInfo<>(list);
		
		return info;
	}
	
	/**
	 * 根据案例ID和openId查询该案例相关的咨询
	 * @param caseInfo
	 * @return
	 */
	@Override
	public PageInfo<ConsultationDetailVo> findConsultationByCaseIdList(CaseConsultationPageDto caseInfo){
		PageHelper.startPage(caseInfo.getCurrentPage(), caseInfo.getPageSize());
		List<ConsultationDetailVo> list = consultationMapper.findConsultationByCaseIdList(caseInfo);
		PageInfo<ConsultationDetailVo> info = new PageInfo<>(list);
		
		return info;
	}
	
	/**
	 * 新增用户咨询
	 * @param addInfo
	 * @return
	 */
	@Override
	public Consultation insertConsultation(Consultation addInfo){
		int count = consultationMapper.insertConsultation(addInfo);
		return addInfo;
	}
	
	/**
	 * 回复用户咨询
	 * @param updateInfo
	 * @return
	 */
	@Override
	public int updateConsultationReply(Consultation updateInfo){
		int count = consultationMapper.updateConsultationReply(updateInfo);
		return count;
	}
}
