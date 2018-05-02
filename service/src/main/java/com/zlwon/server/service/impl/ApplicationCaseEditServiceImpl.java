package com.zlwon.server.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CaseEditMapper;
import com.zlwon.rdb.entity.ApplicationCase;
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
					applicationCaseEditListVo.setSetting("<th>"+applicationCaseEditListVo.getSetting()+"</th>");
				}
				//2匹配选材要求(6项)
				String[] voArray = applicationCaseEditListVo.getSelectRequirements().split("/r/n");//编辑案例选材要求分割数据
				int voLength = voArray.length;//得到编辑案例选材要求项数
				String[] reArray = re.getSelectRequirements().split("/r/n");//案例选材要求分割数据
				int reLength = reArray.length;//得到案例选材要求项数
				if(reLength >= voLength){//案例选材要求项目多余编辑案例选材要求项目
					for (int i = 0; i < reArray.length; i++) {
						if(StringUtils.isNotBlank(voArray[i])){//编辑案例选材要求项数没有案例选材要求项目 多,就不做判断
							if(!reArray[i].equals(voArray[i])){
								voArray[i] = "<th>"+voArray[i]+"</th>";
							}
						}
					}
				}else {//编辑案例选材要求项目多余案例选材要求项目
					for (int i = 0; i < voArray.length; i++) {
						if(StringUtils.isNotBlank(reArray[i])){
							if(!reArray[i].equals(voArray[i])){
								voArray[i] = "<th>"+voArray[i]+"</th>";
							}
						}else {
							voArray[i] = "<th>"+voArray[i]+"</th>";
						}
					}
				}
			}
		}
		return new  PageInfo<>(list);
	}


}
