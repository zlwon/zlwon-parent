package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;

/**
 * 应用案例Mapper
 * @author yangy
 *
 */

public interface ApplicationCaseMapper {

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
	List<ApplicationCase> selectAllApplicationCase();

	/**
	 * 根据案例id修改非空的字段
	 * @param applicationCase
	 * @return
	 */
	long updateByPrimaryKeySelective(ApplicationCase applicationCase);
	
	/**
	 * 添加案例
	 * @param applicationCase
	 * @return
	 */
	long insertSelective(ApplicationCase applicationCase);

	/**
	 * 分页查询特定展会的案例简单详情
	 * @param info
	 * @return
	 */
	List<ApplicationCaseSimpleVo> selectSpecifyExhibitionCase(SearchSpecifyExhibitionDto info);
	
	/**
	 * 根据案例标题查找案例
	 * @param Title
	 * @return
	 */
	List<ApplicationCase>  selectApplicationCaseByTitleMake(String  title);

	/**
	 * 根据展会id，得到展会下所有正常的案例
	 * @param id 展会id
	 * @return
	 */
	List<ApplicationCase> selectApplicationCaseByExhibitionIdMake(Integer id);

	/**
	 * 根据案例id，得到案例详情
	 * @param id
	 * @return
	 */
	ApplicationCaseDetailsVo selectApplicationCaseDetailsMake(Integer id);
}
