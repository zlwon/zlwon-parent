package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CaseEdit;
import com.zlwon.vo.applicationCaseEdit.ApplicationCaseEditListVo;

public interface CaseEditMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CaseEdit record);

    int insertSelective(CaseEdit record);

    CaseEdit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CaseEdit record);

    int updateByPrimaryKey(CaseEdit record);

    /**
     * 根据用户id和案例id，得到用户编辑的数据(未审核的)
     * @param uid 用户id
     * @param aid 案例id
     * @return
     */
	CaseEdit selectByUidAndAidExamine(@Param("uid")Integer uid, @Param("aid")Integer aid);

	/**
	 * 得到所有案例编辑信息
	 * @return
	 */
	List<ApplicationCaseEditListVo> selectAllApplicationCaseEdit();
}