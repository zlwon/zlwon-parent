package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.SysadminMapper;
import com.zlwon.rdb.entity.Sysadmin;
import com.zlwon.server.service.ManagerService;
import com.zlwon.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private SysadminMapper sysadminMapper;
	
	@Override
	public Sysadmin login(String usernam, String password) {
		//根据账号和密码查找
		return sysadminMapper.selectSysadminToLogin(usernam,MD5Utils.encode(password));
	}

	/**
	 * 添加管理员
	 */
	@Override
	public int saveManager(Sysadmin sysadmin) {
		//一个账号只能存在一个(正常状态)
		Sysadmin record = sysadminMapper.selectByUsernameMake(sysadmin.getUsername());
		if(record != null){
			throw   new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		sysadmin.setPassword(MD5Utils.encode(sysadmin.getPassword()));
		sysadmin.setCreateTime(new  Date());
		return sysadminMapper.insertSelective(sysadmin);
	}

	/**
	 * 保存修改后的管理员信息
	 * 需要判断账号是否重复
	 */
	@Override
	public int alterManager(Sysadmin sysadmin) {
		//判断是否修改账号，如果要修改，检查账号唯一性
		if(sysadmin.getUsername() != null){
			if(sysadmin.getUsername().trim().equals("")){
				//username不能设置为空字符串
				throw  new  CommonException(StatusCode.INVALID_PARAM);
			}else {
				Sysadmin record = sysadminMapper.selectByUsernameMake(sysadmin.getUsername());
				if(record != null  && record.getId() != sysadmin.getId()){
					//username已存在
					throw  new  CommonException(StatusCode.DATA_IS_EXIST);
				}
			}
		}
		
		//判断是否修改密码，如果修改，需要加密
		if(sysadmin.getPassword() != null){
			if(sysadmin.getPassword().trim().equals("")){
				//password不能设置为空字符串
				throw  new  CommonException(StatusCode.INVALID_PARAM);
			}else {
				sysadmin.setPassword(MD5Utils.encode(sysadmin.getPassword()));
			}
		}
		return sysadminMapper.updateByPrimaryKeySelective(sysadmin);
	}

	/**
	 * 得到所有正常的管理员
	 */
	@Override
	public PageInfo<Sysadmin> findAllManager(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Sysadmin>  list = sysadminMapper.selectAllManagerMake();
		return new  PageInfo<Sysadmin>(list);
	}

	/**
	 * 根据管理员id，删除管理员
	 */
	@Override
	public int removeManagerById(Integer id) {
		Sysadmin record = new Sysadmin();
		record.setDel(-1);
		record.setId(id);
		return  sysadminMapper.updateByPrimaryKeySelective(record );
	}

	/**
	 * 根据管理员id，得到管理员详情
	 */
	public Sysadmin findManagerById(Integer id) {
		Sysadmin sysadmin = sysadminMapper.selectByPrimaryKey(id);
		if(sysadmin == null  ||  sysadmin.getDel() != 1){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		return sysadmin;
	}

	
	
}
