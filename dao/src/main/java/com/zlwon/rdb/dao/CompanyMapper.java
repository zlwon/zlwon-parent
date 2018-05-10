package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Company;
import com.zlwon.vo.pc.customer.ApplyCompanyCustomerVo;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    /**
     * 根据企业全称和父id(企业简称)，和所属表标识，得到企业全称数据(只得到审核通过的)
     * @param companyFullName 企业全称名称
     * @param parentId 父id(企业简称id)
     * @param status 0父id是customer的生产商1company的企业简称
     * @return
     */
	Company selectCompanyByFullNameExamine(@Param("companyFullName")String companyFullName, @Param("parentId")Integer parentId, @Param("status")Byte status);

	/**
	 * 根据企业简称关键字，得到所有企业简称名称(只得到审核通过的)
	 * @param companyShortName
	 * @return
	 */
	List<String> selectCompanyByShortNameExamine(@Param("companyShortName")String companyShortName);

	
	/**
	 * 根据企业简称id和所属状态，得到该企业简称下所有的企业全称信息
	 * @param parentIid 父id(企业简称id)
	 * @param status 0父id是customer的生产商1company的企业简称
	 * @return
	 */
	List<ApplyCompanyCustomerVo> selectAllCompanyByShortNameExamine(@Param("parentIid")Integer parentIid, @Param("status")Byte status);

	/**
	 * 根据企业全称关键字，父id，所属状态，得到所有企业全称信息(只得到审核通过的)
	 * @param companyFullName
	 * @param id
	 * @param status
	 * @return
	 */
	List<ApplyCompanyCustomerVo> selectCompanyByParentIdAndFullNameExamine(@Param("companyFullName")String companyFullName, @Param("parentIid")Integer parentIid,
			@Param("status")Byte status);

	
	/**
	 * 根据企业简称名称，得到已审核的企业简称
	 * @param name 企业简称名称
	 * @return
	 */
	Company selectApplySuccessShortCompanyByShortName(@Param("name")String name);

	/**
	 * 根据企业全称id和企业全称父类所属标识，得到企业简称名称
	 * 
	 * @param fullcompanyId 企业全称id
	 * @param status 企业全称父类所属表0:用户表zl_customer1:企业表zl_company
	 * @return
	 */
	String selectShortCompanyNameByIdStatus(@Param("fullcompanyId")Integer fullcompanyId,@Param("status")Byte  status);

	
}