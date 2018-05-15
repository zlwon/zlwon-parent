package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.AccessPageMapper;
import com.zlwon.rdb.entity.AccessPage;
import com.zlwon.server.service.AccessPageService;

/**
 * 访问记录统计serviceImpl
 * @author yangy
 *
 */

@Service
public class AccessPageServiceImpl implements AccessPageService {

	@Autowired
	private AccessPageMapper accessPageMapper;
	
	/**
	 * 编辑访问记录统计
	 * @param record
	 * @return
	 */
	@Override
	public int updateAccessPage(AccessPage record){
		
		int count = accessPageMapper.updateByPrimaryKeySelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
     * 根据类型更新数量
     * @param type
     * @return
     */
	@Override
    public int updateCountByType(Integer type){
    	
    	int count = accessPageMapper.updateCountByType(type);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
    }
	
	/**
     * 查询所有访问记录
     * @return
     */
	@Override
    public List<AccessPage> findAllAccessPage(){
    	List<AccessPage> list = accessPageMapper.selectAllAccessPage();
    	return list;
    }
}
