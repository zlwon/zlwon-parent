package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.ExhibitionCaseMap;

public interface ExhibitionCaseMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExhibitionCaseMap record);

    int insertSelective(ExhibitionCaseMap record);

    ExhibitionCaseMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExhibitionCaseMap record);

    int updateByPrimaryKey(ExhibitionCaseMap record);

    /**
     * 根据案例id和展会id，得到信息
     * @param exhibitionCaseMap
     * @return
     */
	ExhibitionCaseMap selectByCaseIdAndEid(ExhibitionCaseMap exhibitionCaseMap);

	
	/**
	 * 删除展会案例，根据案例id和展会id
	 * @param exhibitionCaseMap
	 * @return
	 */
	int deleteByCaseIdAndExhibitionId(ExhibitionCaseMap exhibitionCaseMap);
}