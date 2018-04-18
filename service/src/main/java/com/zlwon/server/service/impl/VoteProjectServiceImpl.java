package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.voteActivity.VoteProjectPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.VoteProjectMapper;
import com.zlwon.rdb.dao.VoteProjectMessageMapper;
import com.zlwon.rdb.entity.VoteProject;
import com.zlwon.server.service.VoteProjectService;
import com.zlwon.vo.voteActivity.VoteProjectDetailListVo;
import com.zlwon.vo.voteActivity.VoteProjectDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投票活动参与项目信息ServiceImpl
 * @author yangy
 *
 */

@Service
public class VoteProjectServiceImpl implements VoteProjectService {

	@Autowired
	private VoteProjectMapper voteProjectMapper;
	
	@Autowired
	private VoteProjectMessageMapper voteProjectMessageMapper;
	
	/**
	 * 根据id查询投票项目
	 * @param id
	 * @return
	 */
	@Override
	public VoteProject selectVoteProjectById(Integer id){
		VoteProject temp = voteProjectMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 根据活动ID分页查询该活动所有参与项目
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<VoteProjectDetailListVo> selectVoteProjectByActivityId(VoteProjectPageDto form){
		//PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		Integer currentPage = form.getCurrentPage();
		Integer pageSize = form.getPageSize();
		Integer startNum = (currentPage-1) * pageSize;
		Integer endNum = pageSize;
		form.setCurrentPage(startNum);
		form.setPageSize(endNum);
		List<VoteProjectDetailListVo> list = voteProjectMapper.selectVoteProjectByActivityId(form);
		
		//查询数量
		int count = voteProjectMapper.selectVoteProjectCountByActivityId(form);
		
		PageInfo<VoteProjectDetailListVo> info = new PageInfo<>(list);
		info.setPageNum(currentPage);
		info.setPageSize(pageSize);
		info.setTotal(count);
		if(count%pageSize == 0){
			info.setPages(count/pageSize);
		}else{
			info.setPages(count/pageSize+1);
		}
		
		return info;
	}
	
	/**
     * 根据活动ID查询该活动所有参与项目数量
     * @param form
     * @return
     */
	@Override
	public int selectVoteProjectCountByActivityId(VoteProjectPageDto form){
		int count = voteProjectMapper.selectVoteProjectCountByActivityId(form);
		return count;
	}
	
	/**
     * 根据活动项目ID查询活动项目详情
     * @param id
     * @return
     */
	@Override
	public VoteProjectDetailVo selectVoteProjectDetailById(Integer id){
		VoteProjectDetailVo temp = voteProjectMapper.selectVoteProjectDetailById(id);
		return temp;
	}

	/**
	 * 根据活动id，得到所有参与的活动项目信息，分页查找
	 */
	@Override
	public PageInfo<VoteProjectDetailVo> findAllVoteProjectByAidMake(Integer pageIndex, Integer pageSize, Integer aid) {
		PageHelper.startPage(pageIndex, pageSize);
		//根据活动id，得到所有活动项目信息(需要创建用户标记状态为正常)
		List<VoteProjectDetailVo>  list = voteProjectMapper.selectByActivityIdMake(aid);
		return new PageInfo<VoteProjectDetailVo>(list);
	}

	/**
	 * 根据参与项目id，修改审核状态，需要先判断参与项目是否存在
	 */
	@Override
	public int alterVoteProjectExamineById(VoteProject voteProject) {
		//根据参与项目id，得到参与项目信息
		VoteProject record = voteProjectMapper.selectByPrimaryKey(voteProject.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		return  voteProjectMapper.updateByPrimaryKeySelective(voteProject);
	}

	/**
	 * 根据参与项目id，删除参与项目信息
	 */
	@Override
	public int removeVoteProjectById(Integer id) {
		return voteProjectMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 新增投票项目
	 * @param record
	 * @return
	 */
	@Override
	public int insertVoteProject(VoteProject record){
		int count = voteProjectMapper.insert(record);
		return count;
	}
	
	/**
	 * 更新投票项目
	 * @param record
	 * @return
	 */
	@Override
	public int updateByPrimaryKeySelective(VoteProject record){
		int count = voteProjectMapper.updateByPrimaryKeySelective(record);
		return count;
	}
	
	/**
	 * 根据活动ID查询该活动投票项目数量
	 * @param activityId
	 * @return
	 */
	@Override
	public int countVoteProjectByActivityId(Integer activityId){
		int count = voteProjectMapper.countVoteProjectByActivityId(activityId);
		return count;
	}
}
