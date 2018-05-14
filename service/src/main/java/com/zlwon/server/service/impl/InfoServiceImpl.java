package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.InfoMapper;
import com.zlwon.rdb.entity.Info;
import com.zlwon.server.service.InfoService;

/**
 * 资讯serviceImpl
 * @author yangy
 *
 */

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;
	
	/**
	 * 新增资讯记录
	 * @param record
	 * @return
	 */
	@Override
	public int insertInfo(Info record){
		int count = infoMapper.insertSelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据资讯id删除资讯记录
	 * @param id
	 * @return
	 */
	@Override
	public int deleteInfoById(Integer id){
		int count = infoMapper.deleteByPrimaryKey(id);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据资讯id更新资讯热门状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	public int updateInfoHotStatusById(Integer id){
		
		//根据资讯id查询资讯信息
		Info validExist = infoMapper.selectByPrimaryKey(id);
		if(validExist == null){
			throw new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//获取要改编的热门状态
		Integer status = 1;
		if(validExist.getIsHot() == 1){
			status = 0;
		}
		
		//更新资讯状态
		Info record = new Info();
		record.setId(id);
		record.setIsHot(status);
		
		int count = infoMapper.updateByPrimaryKeySelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return status;  //返回1：置顶热门成功 0：取消置顶成功
	}
	
	/**
	 * 根据资讯id查询资讯详情
	 * @param id
	 * @return
	 */
	@Override
	public Info findInfoDetailById(Integer id){
		Info result = infoMapper.selectByPrimaryKey(id);
		return result;
	}
	
	/**
	 * 编辑资讯记录
	 * @param record
	 * @return
	 */
	@Override
	public int updateInfo(Info record){
		int count = infoMapper.updateByPrimaryKeySelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
}
