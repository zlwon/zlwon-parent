package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.pc.info.QueryPcInfoByPageDto;
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
    
    /**
     * 查询热门资讯数量
     * @return
     */
    int countHotInfo();
    
    /**
     * 查询所有热门资讯
     * @return
     */
    List<InfoDetailVo> selectHotInfoList();
    
    /**
     * pc端根据资讯ID查询资讯信息详情
     * @param id
     * @return
     */
    InfoDetailVo selectPcInfoById(Integer id);
    
    /**
     * pc端分页查询资讯列表
     * @param form
     * @return
     */
    List<InfoDetailVo> selectPcInfoByPageList(QueryPcInfoByPageDto form);
}