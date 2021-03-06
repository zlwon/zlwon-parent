package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.collection.JudgeCollectionDto;
import com.zlwon.dto.pc.collection.QueryMyCollectionPageDto;
import com.zlwon.rdb.dao.CollectionMapper;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.server.service.CollectionService;
import com.zlwon.vo.pc.collection.MyCollectionInfoVo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户收藏ServiceImpl
 * @author yangy
 *
 */

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionMapper collectionMapper;
	
	/**
	 * 新增用户收藏
	 * @param insertInfo
	 * @return
	 */
	@Override
	public Collection insertCollection(Collection insertInfo){
		int count = collectionMapper.insertCollection(insertInfo);
		return insertInfo;
	}
	
	/**
	 * 根据用户ID和信息ID，信息类型查询用户收藏
	 * @param selInfo
	 * @return
	 */
	@Override
	public Collection selectCollectionByUserAndInfo(JudgeCollectionDto selInfo){
		Collection temp = collectionMapper.selectCollectionByUserAndInfo(selInfo);
		return temp;
	}
	
	/**
	 * 根据用户ID和信息ID，信息类型查询用户收藏
	 * @param type
	 * @param iid
	 * @param userId
	 * @return
	 */
	@Override
	public Collection findCollectionInfoByUser(Integer type,Integer iid,Integer userId){
		Collection temp = collectionMapper.selectCollectionInfoByUser(type, iid, userId);
		return temp;
	}
	
	/**
	 * 根据id查询用户收藏
	 * @param id
	 * @return
	 */
	@Override
	public Collection selectCollectionById(Integer id){
		Collection temp = collectionMapper.selectCollectionById(id);
		return temp;
	}
	
	/**
	 * 根据id删除用户收藏
	 * @param id
	 * @return
	 */
	@Override
	public int deleteCollectionById(Integer id){
		int count = collectionMapper.deleteCollectionById(id);
		return count;
	}
	
	/**
	 * 测试方法
	 * @return
	 */
	@Override
	public List<Collection> getCollectionTestList(){
		List<Collection> list = collectionMapper.getCollectionTestList();
		return list;
	}
	
	/**
	 * 分页查询我的收藏信息（可指定类型）
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<MyCollectionInfoVo> findMyCollectionPage(QueryMyCollectionPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<MyCollectionInfoVo> list = collectionMapper.selectMyCollectionPage(form);
		PageInfo<MyCollectionInfoVo> result = new PageInfo<>(list);
		return result;
	}
	
	/**
	 * 查询我的收藏总数
	 * @param userId
	 * @return
	 */
	@Override
	public int findMyCollectionCount(Integer userId){
		int count = collectionMapper.selectMyCollectionCount(userId);
		return count;
	}
}

