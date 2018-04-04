package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
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
	 * 得到所有案例
	 * @return
	 */
	PageInfo<ApplicationCase>  findAllApplicationCase(Integer  pageIndex,Integer  pageSize);

	/**
	 * 根据案例id，保存修改后的案例信息
	 * @param applicationCase
	 * @return
	 */
	long alterApplicateCaseById(ApplicationCase applicationCase);

	/**
	 * 根据案例id，删除案例
	 * @param id
	 * @return
	 */
	long removeApplicateCaseById(Integer id);

	/**
	 * 添加案例
	 * @param applicationCase
	 * @return
	 */
	long saveApplicateCase(ApplicationCase applicationCase);

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
	ApplicationCaseDetailsVo findApplicationCaseDetailsMake(Integer id);

	/**
	 * 用户收藏案例
	 * @param id
	 * @return
	 */
	int saveApplicationCaseCollection(Integer id);
	
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
}
