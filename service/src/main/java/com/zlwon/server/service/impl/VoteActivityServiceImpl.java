package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.voteActivity.VoteActivityPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.VoteActivityMapper;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.server.service.VoteActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 投票活动ServiceImpl
 * @author yangy
 *
 */

@Service
public class VoteActivityServiceImpl implements VoteActivityService {

	@Autowired
	private VoteActivityMapper voteActivityMapper;
	
	/**
	 * 根据id查询投票活动
	 * @param id
	 * @return
	 */
	@Override
	public VoteActivity selectVoteActivityById(Integer id){
		VoteActivity temp = voteActivityMapper.selectByPrimaryKey(id);
		return temp;
	}

	
	/**
	 * 得到所有活动，分页查找
	 */
	@Override
	public PageInfo<VoteActivity> findAllVoteActivityMake(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		//得到所有标记正常的活动
		List<VoteActivity>  list = voteActivityMapper.selectAllVoteActivityMake();
		return new  PageInfo<VoteActivity>(list);
	}


	/**
	 * 根据id，得到正常的活动
	 */
	public VoteActivity findVoteActivityByIdMake(Integer id) {
		VoteActivity record = voteActivityMapper.selectByPrimaryKey(id);
		if(record == null || record.getDel() != 1){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		return record;
	}


	/**
	 * 添加活动，需要判断标题是否存在，标题唯一
	 */
	@Override
	public int saveVoteActivity(VoteActivity voteActivity) {
		//根据标题得到活动
		VoteActivity  record = voteActivityMapper.selectByTitleMake(voteActivity.getTitle());
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		voteActivity.setDel(1);
		voteActivity.setCreateTime(new  Date());
		return  voteActivityMapper.insertSelective(voteActivity);
	}


	//更新活动，需要判断活动是否正常存在，然后在判断标题是否存在，标题唯一
	@Override
	public int alterVoteActivityByIdMake(VoteActivity voteActivity) {
		//查看活动是否存在
		VoteActivity record = voteActivityMapper.selectByPrimaryKey(voteActivity.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//判断标题是否存在
		if(StringUtils.isNotBlank(voteActivity.getTitle())){
			record = voteActivityMapper.selectByTitleMake(voteActivity.getTitle());
			if(record != null  && record.getId() != voteActivity.getId()){
				throw  new  CommonException(StatusCode.DATA_IS_EXIST);
			}
		}
		return voteActivityMapper.updateByPrimaryKeySelective(voteActivity);
	}


	/**
	 * 更新标记为删除
	 */
	@Override
	public int removeVoteActivityById(Integer id) {
		VoteActivity record = new VoteActivity();
		record.setId(id);
		record.setDel(-1);
		return voteActivityMapper.updateByPrimaryKeySelective(record );
	}
	
	/**
	 * 查询在活动期限全部投票活动
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<VoteActivity> selectAllVoteActivityDate(VoteActivityPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<VoteActivity> list = voteActivityMapper.selectAllVoteActivityDate(form);
		PageInfo<VoteActivity> info = new PageInfo<>(list);
		
		return info;
	}
}
