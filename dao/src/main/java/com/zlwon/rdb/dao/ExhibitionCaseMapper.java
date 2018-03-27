package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.ExhibitionCase;
/**
 * 展会案例表，案例关联工程师的
 * @author yuand
 *
 */
public interface ExhibitionCaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExhibitionCase record);

    int insertSelective(ExhibitionCase record);

    ExhibitionCase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExhibitionCase record);

    int updateByPrimaryKey(ExhibitionCase record);

    /**
     * 根据案例id，得到展会案例,需要状态正常
     * @param cid
     * @return
     */
	List<ExhibitionCase> selectByCid(Integer cid);
}