package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CaseEditMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.CaseEdit;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.server.service.ApplicationCaseEditService;
import com.zlwon.vo.applicationCaseEdit.ApplicationCaseEditListVo;

/**
 * 案例编辑service
 * @author yuand
 *
 */
@Service
public class ApplicationCaseEditServiceImpl implements ApplicationCaseEditService{
	
	@Autowired
	private  CaseEditMapper  caseEditMapper;
	@Autowired
	private  ApplicationCaseMapper  applicationCaseMapper;
	@Autowired
	private  InformMapper  informMapper;

	/**
	 * 得到所有编辑案例信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo findAllApplicationCaseEdit(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<ApplicationCaseEditListVo>  list = caseEditMapper.selectAllApplicationCaseEdit();
		//高亮显示修改的数据
		ApplicationCase  re = null;
		if(list != null  &&  list.size() > 0){
			for (ApplicationCaseEditListVo applicationCaseEditListVo : list) {
				//得到正常的案例信息
				re = applicationCaseMapper.selectByPrimaryKey(applicationCaseEditListVo.getAid());
				//1匹配案例背景
				if(StringUtils.isNotBlank(re.getSetting()) && !re.getSetting().equals(applicationCaseEditListVo.getSetting())){
					applicationCaseEditListVo.setSetting("<strong>"+applicationCaseEditListVo.getSetting()+"</strong>");
				}
				//2匹配选材要求(6项)
				applicationCaseEditListVo.setSelectRequirements(parseSelectRequirements(re,applicationCaseEditListVo));
				//3匹配选材原因(6项)
				applicationCaseEditListVo.setSelectCause(parseSelectCause(re,applicationCaseEditListVo));
			}
		}
		return new  PageInfo<>(list);
	}
	
	/**
	 * 根据用户ID查询编辑(新增)案例数量
	 * @param userId
	 * @return
	 */
	@Override
	public int countApplicationCaseEditByUserId(Integer userId){
		return applicationCaseMapper.countApplicationCaseEditByUid(userId);
	}

	
	/**
	 * 设置编辑案例通过(需要添加到通知表),并且同步到案例表中(3项)
	 * @param id
	 * @return
	 */
	@Transactional
	public int alterApplicationCaseEditSuccess(Integer id) {
		CaseEdit caseEdit = caseEditMapper.selectByPrimaryKey(id);
		if(caseEdit == null || caseEdit.getExamine() == 1 || caseEdit.getExamine() == 2){
			throw  new  CommonException(caseEdit == null?StatusCode.DATA_NOT_EXIST:caseEdit.getExamine() == 1?StatusCode.DATE_EXAMINE_SUCCESS:StatusCode.EDITCASE_IS_FAILED);
		}
		//设置通过
		caseEdit.setAuditTime(new  Date());
		caseEdit.setExamine(1);
		caseEditMapper.updateByPrimaryKeySelective(caseEdit);
		//同步到案例表中
		ApplicationCase applicationCase = applicationCaseMapper.selectByPrimaryKey(caseEdit.getAid());
		applicationCase.setSelectCause(caseEdit.getSelectCause());
		applicationCase.setSelectRequirements(caseEdit.getSelectRequirements());
		applicationCase.setSetting(caseEdit.getSetting());
		applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
		
		//添加到通知表
		Inform record = new Inform();
		record.setCreateTime(new Date());
		record.setIid(caseEdit.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 1);
		record.setType((byte) 3);
		record.setUid(caseEdit.getUid());
		return  informMapper.insertSelective(record);
	}


	/**
	 * 设置编辑案例驳回(需要添加到通知表)
	 * @param id
	 * @return
	 */
	@Override
	public int alterApplicationCaseEditFailed(Integer id,String  content) {
		CaseEdit caseEdit = caseEditMapper.selectByPrimaryKey(id);
		if(caseEdit == null || caseEdit.getExamine() == 1 || caseEdit.getExamine() == 2){
			throw  new  CommonException(caseEdit == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_NOT_EXAMINE_FAILED);
		}
		//设置驳回
		caseEdit.setExamine(2);
		caseEdit.setAuditTime(new  Date());
		caseEditMapper.updateByPrimaryKeySelective(caseEdit);
		//添加到通知表
		Inform record = new Inform();
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setIid(caseEdit.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 0);
		record.setType((byte) 3);
		record.setUid(caseEdit.getUid());
		return  informMapper.insertSelective(record);
	}


	/**
	 * 得到编辑案例驳回信息
	 * @param id
	 * @return
	 */
	@Override
	public String findApplicationCaseEditFailedContent(Integer id) {
		CaseEdit caseEdit = caseEditMapper.selectByPrimaryKey(id);
		if(caseEdit == null  || caseEdit.getExamine() != 2){
			throw  new  CommonException(caseEdit == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_NOT_EXAMINE_FAILED);
		}
		Inform  inform = informMapper.selectApplicationCaseEditFailedByIid(caseEdit.getId());
		return inform.getContent();
	}


	
	
	
	
	//匹配选材要求
	private   String    parseSelectRequirements(ApplicationCase re,ApplicationCaseEditListVo  applicationCaseEditListVo){
		StringBuilder  selectRequirementsStr = null;
		String[] voArray = applicationCaseEditListVo.getSelectRequirements().split("\n");//编辑案例选材要求分割数据
		int voLength = voArray.length;//得到编辑案例选材要求项数
		String[] reArray = re.getSelectRequirements().split("\n");//案例选材要求分割数据
		int reLength = reArray.length;//得到案例选材要求项数
		if(reLength >= voLength){//案例选材要求项目多余编辑案例选材要求项目
			selectRequirementsStr = new StringBuilder();
			for (int i = 0; i < reArray.length; i++) {
				if(i < voLength){//编辑案例选材要求项数没有案例选材要求项目 多,就不做判断
					if(!reArray[i].equals(voArray[i])){
						selectRequirementsStr.append("<strong>"+voArray[i]+"</strong>\n");
					}else {
						selectRequirementsStr.append(voArray[i]+"\n");
					}
				}
			}
		}else {//编辑案例选材要求项目多余案例选材要求项目
			selectRequirementsStr = new StringBuilder();
			for (int i = 0; i < voArray.length; i++) {
				if(i < reLength){
					if(!reArray[i].equals(voArray[i])){
						selectRequirementsStr.append("<strong>"+voArray[i]+"</strong>\n");
					}else{
						selectRequirementsStr.append(voArray[i]+"\n");
					}
					
				}else {
					selectRequirementsStr.append("<strong>"+voArray[i]+"</strong>\n");
				}
			}
		}
		String str = selectRequirementsStr.toString().substring(0, selectRequirementsStr.toString().length()-1);
		return   str;
	}
	//匹配选材原因
	private   String    parseSelectCause(ApplicationCase re,ApplicationCaseEditListVo  applicationCaseEditListVo){
		StringBuilder  selectCauseStr = null;
		String[] voArray = applicationCaseEditListVo.getSelectCause().split("\n");//编辑案例选材原因分割数据
		int voLength = voArray.length;//得到编辑案例选材原因项数
		String[] reArray = re.getSelectCause().split("\n");//案例选材原因分割数据
		int reLength = reArray.length;//得到案例选材原因项数
		if(reLength >= voLength){//案例选材原因项目多余编辑案例选材原因项目
			selectCauseStr = new StringBuilder();
			for (int i = 0; i < reArray.length; i++) {
				if(i < voLength){//编辑案例选材原因项数没有案例选材原因项目 多,就不做判断
					if(!reArray[i].equals(voArray[i])){
						selectCauseStr.append("<strong>"+voArray[i]+"</strong>\n");
					}else {
						selectCauseStr.append(voArray[i]+"\n");
					}
				}
			}
		}else {//编辑案例选材原因项目多余案例选材原因项目
			selectCauseStr = new StringBuilder();
			for (int i = 0; i < voArray.length; i++) {
				if(i < reLength){
					if(!reArray[i].equals(voArray[i])){
						selectCauseStr.append("<strong>"+voArray[i]+"</strong>\n");
					}else{
						selectCauseStr.append(voArray[i]+"\n");
					}
					
				}else {
					selectCauseStr.append("<strong>"+voArray[i]+"</strong>\n");
				}
			}
		}
		String str = selectCauseStr.toString().substring(0, selectCauseStr.toString().length()-1);
		return   str;
	}

}
