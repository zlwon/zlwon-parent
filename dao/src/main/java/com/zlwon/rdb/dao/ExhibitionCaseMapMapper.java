package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.ExhibitionCaseMap;
import com.zlwon.vo.customer.EngineerVo;

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

	
	/**
	 * 得到所有工程师(而且通过展会id和案例id，标识已关联的工程师)，后端查看展会案例关联的工程师
	 * @param aid 案例id
	 * @param eid 展会id
	 * @return
	 */
	List<EngineerVo> selectAllEngineer(@Param("aid")Integer aid, @Param("eid")Integer eid);

	/**
	 * 根据展会id和案例id，得到展会案例已关联的工程师(如果未关联工程师exhibition_case_id会返回null)
	 * @param aid
	 * @param eid
	 * @return
	 */
	ExhibitionCaseMap selectExhibitionCaseMapByAidAndEidMake(@Param("aid")Integer aid, @Param("eid")Integer eid);
}