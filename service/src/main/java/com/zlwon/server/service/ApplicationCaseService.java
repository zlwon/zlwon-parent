package com.zlwon.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.applicationCase.ApplicationCaseDto;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.applicationCase.QueryApplicationCaseListDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseListVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.applicationCase.ApplicationCaseVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;

/**
 * 应用案例Service
 * @author yangy
 *
 */

public interface ApplicationCaseService {

	/**
	 * 根据id查询应用案例
	 * @param id  自增ID
	 * @return
	 */
	ApplicationCase findAppCaseById(Integer id);

	/**
	 * 根据标题查询应用案例
	 * @param title  标题
	 * @return
	 */
	ApplicationCase findAppCaseByTitle(String title);

	/**
	 * 根据物性表ID查询使用该规格的应用案例
	 * @param specId  物性表ID
	 * @return
	 */
	List<ApplicationCase> findAppCaseBySpecId(Integer specId);

	/**
	 * 根据用户ID查询该用户所有的应用案例
	 * @param uid  用户ID
	 * @return
	 */
	List<ApplicationCase> findAppCaseByUid(Integer uid);

	/**
	 * 根据id查询应用案例具体详情
	 * @param id
	 * @return
	 */
	ApplicationCaseDetailVo findAppCaseDetailById(Integer id);

	/**
	 * 得到所有案例,案例名称模糊查询
	 * @param key 关键字，案例名称模糊查询 
	 * @return
	 */
	PageInfo<ApplicationCaseListVo>  findAllApplicationCase(Integer  pageIndex,Integer  pageSize,String  key);

	/**
	 * 根据案例id，保存修改后的案例信息
	 * @param applicationCase
	 * @return
	 */
	long alterApplicateCaseById(ApplicationCaseDto applicationCase);
	
	/**
	 * 用户修改案例信息
	 * @param request
	 * @param applicationCase
	 * @return
	 */
	int alterApplicationCaseByUser(HttpServletRequest request, ApplicationCase applicationCase);

	/**
	 * 根据案例id，删除案例
	 * @param id
	 * @return
	 */
	long removeApplicateCaseById(Integer id);

	/**
	 * 添加案例
	 * @param applicationCase
	 * @param type 1管理员，审核状态默认为通过0用户，审核状态为审核中
	 * @return
	 */
	long saveApplicateCase(HttpServletRequest  request,ApplicationCaseDto applicationCase,Integer  type);

	/**
	 * 分页查询特定展会的案例简单详情
	 * @param info
	 * @return
	 */
	PageInfo<ApplicationCaseSimpleVo> selectSpecifyExhibitionCase(SearchSpecifyExhibitionDto info);

	
	/**
	 * 根据案例id，得到案例详情
	 * @param id
	 * @return
	 */
	ApplicationCaseDetailsVo findApplicationCaseDetailsMake(Integer id,HttpServletRequest  request);

	
	/**
	 * 根据物性ID分页查询关联应用案例
	 * @param info
	 * @return
	 */
	PageInfo<PcApplicationCaseSimpleVo> findSpecCaseBySpecIdPage(PcSearchSpecCasePageDto info);
	
	/**
	 * 根据物性ID统计物性表关联案例数量
	 * @param specId
	 * @return
	 */
	int countSpecCaseBySpecId(Integer specId);

	/**
	 * 根据案例id，包含生产商id，把生产商id映射到ApplicationCase的用户id上
	 * @param id
	 * @return
	 */
	ApplicationCaseVo findAppCaseDetailsById(Integer id);

	/**
	 * 得到所有案例，条件查询，分页
	 * @param pageIndex
	 * @param pageSize
	 * @param listDto 条件查询
	 * @return
	 */
	PageInfo findAllApplicationCaseSelective(HttpServletRequest  request,Integer pageIndex, Integer pageSize, QueryApplicationCaseListDto listDto);

	/**
	 * 首页热门案例查询
	 * @return
	 */
	List<IndexHotApplicationCaseVo> findHotApplicationCase();

	
	/**
	 * 设置案例为热门，需要判断热门个数是否少于5个，并且当前案例不是热门
	 * 注意：要清除缓存
	 * @param id
	 * @return
	 */
	int alterApplicationCaseHot(Integer id);

	/**
	 * 取消热门案例
	 * 注意：要清除缓存
	 * @param id
	 * @return
	 */
	int removeHotApplicationCase(Integer id);

	
}
