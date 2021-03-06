package com.zlwon.server.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.collection.JudgeCollectionDto;
import com.zlwon.dto.pc.collection.QueryMyCollectionPageDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.vo.pc.collection.MyCollectionInfoVo;

/**
 * 用户收藏Service
 * @author yangy
 *
 */

public interface CollectionService {

	/**
	 * 新增用户收藏
	 * @param insertInfo
	 * @return
	 */
	Collection insertCollection(Collection insertInfo);
	
	/**
	 * 根据用户ID和信息ID，信息类型查询用户收藏
	 * @param selInfo
	 * @return
	 */
	Collection selectCollectionByUserAndInfo(JudgeCollectionDto selInfo);
	
	/**
	 * 根据用户ID和信息ID，信息类型查询用户收藏
	 * @param type
	 * @param iid
	 * @param userId
	 * @return
	 */
	Collection findCollectionInfoByUser(Integer type,Integer iid,Integer userId);
	
	/**
	 * 根据id查询用户收藏
	 * @param id
	 * @return
	 */
	Collection selectCollectionById(Integer id);
	
	/**
	 * 根据id删除用户收藏
	 * @param id
	 * @return
	 */
	int deleteCollectionById(Integer id);
	
	/**
	 * 测试方法
	 * @return
	 */
	List<Collection> getCollectionTestList();
	
	/**
	 * 分页查询我的收藏信息（可指定类型）
	 * @param form
	 * @return
	 */
	PageInfo<MyCollectionInfoVo> findMyCollectionPage(QueryMyCollectionPageDto form);
	
	/**
	 * 查询我的收藏总数
	 * @param userId
	 * @return
	 */
	int findMyCollectionCount(Integer userId);
}
