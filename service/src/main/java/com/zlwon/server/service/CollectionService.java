package com.zlwon.server.service;

import java.util.List;

import com.zlwon.dto.collection.JudgeCollectionDto;
import com.zlwon.rdb.entity.Collection;

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
}
