package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CaseEdit;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.vo.applicationCaseEdit.ApplicationCaseEditListVo;
import com.zlwon.vo.pc.applicationCase.EditApplicationCaseCustomerVo;

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

	/**
	 * 根据案例id，得到编辑过案例的用户信息，分类型获取
	 * @param id 案例id
	 * @param type 类型0：所有1：案例背景2：选材原因3：选材要求
	 * @return
	 */
	List<EditApplicationCaseCustomerVo> selectEditApplicationCaseCustomerById(@Param("id")Integer id,@Param("type")int  type);

	/**
	 * 统计编辑案例未审核的个数
	 * @return
	 */
	int selectNotExamineNumber();

	/**
	 * 得到未审核的案例编辑
	 * @param pageSize 要显示的个数
	 * @return
	 */
	List<ApplicationCaseEditListVo> selectNotExamineEditApp(Integer pageSize);

	/**
	 * 得到最近编辑案例的用户信息(审核通过的)
	 * @param aid 案例id
	 * @return
	 */
	Customer selectOneEditCaseCustomer(Integer  aid);
}