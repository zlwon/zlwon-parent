package com.zlwon.rdb.dao;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Company;

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
}