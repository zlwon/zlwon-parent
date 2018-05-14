package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.web.info.QueryInfoByPageDto;
import com.zlwon.rdb.entity.Info;
import com.zlwon.vo.pc.info.InfoDetailVo;

/**
 * 资讯mapper
 * @author yangy
 *
 */

public interface InfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Info record);

    int insertSelective(Info record);

    Info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Info record);

    int updateByPrimaryKeyWithBLOBs(Info record);

    int updateByPrimaryKey(Info record);
    
    /**
     * 分页查询资讯信息记录
     * @param form
     * @return
     */
    List<InfoDetailVo> selectInfoDetailByPage(QueryInfoByPageDto form);
}