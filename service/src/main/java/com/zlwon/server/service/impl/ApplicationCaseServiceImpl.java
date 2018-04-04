package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 应用案例ServiceImpl
 * @author yangy
 *
 */

@Service
public class ApplicationCaseServiceImpl implements ApplicationCaseService {

	@Autowired
	private ApplicationCaseMapper applicationCaseMapper;
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 根据id查询应用案例
	 * @param id  自增ID
	 * @return
	 */
	@Override
	public ApplicationCase findAppCaseById(Integer id){
		ApplicationCase temp = applicationCaseMapper.findAppCaseById(id);
		return temp;
	}

	/**
	 * 根据标题查询应用案例
	 * @param title  标题
	 * @return
	 */
	@Override
	public ApplicationCase findAppCaseByTitle(String title){
		ApplicationCase temp = applicationCaseMapper.findAppCaseByTitle(title);
		return temp;
	}

	/**
	 * 根据物性表ID查询使用该规格的应用案例
	 * @param specId  物性表ID
	 * @return
	 */
	@Override
	public List<ApplicationCase> findAppCaseBySpecId(Integer specId){
		List<ApplicationCase> list = applicationCaseMapper.findAppCaseBySpecId(specId);
		return list;
	}

	/**
	 * 根据用户ID查询该用户所有的应用案例
	 * @param uid  用户ID
	 * @return
	 */
	@Override
	public List<ApplicationCase> findAppCaseByUid(Integer uid){
		List<ApplicationCase> list = applicationCaseMapper.findAppCaseByUid(uid);
		return list;
	}

	/**
	 * 根据id查询应用案例具体详情
	 * @param id
	 * @return
	 */
	@Override
	public ApplicationCaseDetailVo findAppCaseDetailById(Integer id){
		ApplicationCaseDetailVo temp = applicationCaseMapper.findAppCaseDetailById(id);
		return temp;
	}



	/**
	 * 得到所有案例
	 * @param resultPage  包含页码和页个数
	 */
	@Override
	public PageInfo<ApplicationCase> findAllApplicationCase(Integer  pageIndex,Integer  pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<ApplicationCase> list = applicationCaseMapper.selectAllApplicationCase();
		PageInfo<ApplicationCase>  info = new  PageInfo<>(list);
		return info;
	}

	/**
	 * 根据案例id，保存修改后的案例信息
	 */
	@Override
	public long alterApplicateCaseById(ApplicationCase applicationCase) {
		ApplicationCase app = applicationCaseMapper.findAppCaseById(applicationCase.getId());
		if(app == null || app.getDel() != 1)
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);

		//判断标题是否重复
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCase.getTitle());
		if(list!=null &&  list.size()>0){
			if(list.size()==1){
				//判断id是否一样
				if(applicationCase.getId()!=list.get(0).getId()){
					throw new  CommonException(StatusCode.DATA_IS_EXIST);
				}
			}else {
				throw new  CommonException(StatusCode.DATA_IS_EXIST);
			}
		}

		//执行更新操作
		return   applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
	}

	/**
	 * 根据案例id，删除案例
	 */
	@Override
	public long removeApplicateCaseById(Integer id) {
		ApplicationCase app = applicationCaseMapper.findAppCaseById(id);
		if(app==null || -1 == app.getDel())
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);

		//执行更新操作
		ApplicationCase applicationCase = new ApplicationCase();
		applicationCase.setId(id);
		applicationCase.setDel(-1);
		return   applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
	}

	/**
	 * 添加案例
	 */
	@Override
	public long saveApplicateCase(ApplicationCase applicationCase) {
		//根据要添加的案例标题，得到案例
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCase.getTitle());
		if(list!=null  && list.size()>0){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		applicationCase.setCreateTime(new  Date());
		applicationCase.setExamine(1);//默认为审核通过
		return  applicationCaseMapper.insertSelective(applicationCase);
	}

	/**
	 * 分页查询特定展会的案例简单详情
	 * @param info
	 * @return
	 */
	@Override
	public PageInfo<ApplicationCaseSimpleVo> selectSpecifyExhibitionCase(SearchSpecifyExhibitionDto info){
		PageHelper.startPage(info.getCurrentPage(), info.getPageSize());
		List<ApplicationCaseSimpleVo> list = applicationCaseMapper.selectSpecifyExhibitionCase(info);
		PageInfo<ApplicationCaseSimpleVo> result = new PageInfo<ApplicationCaseSimpleVo>(list);
		return result;
	}

	
	/**
	 * 根据案例id，得到案例详情
	 * @param id
	 * @return
	 */
	public ApplicationCaseDetailsVo findApplicationCaseDetailsMake(Integer id) {
		ApplicationCaseDetailsVo  record = applicationCaseMapper.selectApplicationCaseDetailsMake(id);
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//判断创建用户是否是管理员，如果不是查找用户基本信息
		if(record.getUid() > 0){
			Customer customer = customerService.selectCustomerById(id);
			//用户不存在，也显示案例了，因为没这个需求
			if(customer != null){
				record.setNickname(customer.getNickname());
				record.setHeaderimg(customer.getHeaderimg());
			}
		}
		return record;
	}

	/**
	 * 用户收藏案例
	 */
	public int saveApplicationCaseCollection(Integer id) {
		
		return 0;
	}
	
	/**
	 * 根据物性ID分页查询关联应用案例
	 * @param info
	 * @return
	 */
	@Override
	public PageInfo<PcApplicationCaseSimpleVo> findSpecCaseBySpecIdPage(PcSearchSpecCasePageDto info){
		PageHelper.startPage(info.getCurrentPage(), info.getPageSize());
		List<PcApplicationCaseSimpleVo> list = applicationCaseMapper.selectSpecCaseBySpecIdPage(info.getSpecId());
		PageInfo<PcApplicationCaseSimpleVo> result = new PageInfo<PcApplicationCaseSimpleVo>(list);
		return result;
	}
}
