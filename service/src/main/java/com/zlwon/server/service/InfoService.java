package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.info.QueryPcInfoByPageDto;
import com.zlwon.dto.web.info.QueryInfoByPageDto;
import com.zlwon.rdb.entity.Info;
import com.zlwon.vo.pc.info.InfoDetailVo;

/**
 * 资讯service
 * @author yangy
 *
 */

public interface InfoService {

	/**
	 * 新增资讯记录
	 * @param record
	 * @return
	 */
	int insertInfo(Info record);
	
	/**
	 * 根据资讯id删除资讯记录
	 * @param id
	 * @return
	 */
	int deleteInfoById(Integer id);
	
	/**
	 * 根据资讯id更新资讯热门状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updateInfoHotStatusById(Integer id);
	
	/**
	 * 根据资讯id查询资讯详情
	 * @param id
	 * @return
	 */
	Info findInfoDetailById(Integer id);
	
	/**
	 * 编辑资讯记录
	 * @param record
	 * @return
	 */
	int updateInfo(Info record);
	
	/**
     * 分页查询资讯信息记录
     * @param form
     * @return
     */
	PageInfo<InfoDetailVo> findInfoDetailByPage(QueryInfoByPageDto form);
	
	/**
     * pc端分页查询资讯列表
     * @param form
     * @return
     */
	PageInfo<InfoDetailVo> findPcInfoByPageList(QueryPcInfoByPageDto form);
	
	/**
     * pc端根据资讯ID查询资讯信息详情
     * @param id
     * @return
     */
    InfoDetailVo findPcInfoById(Integer id);
}
