package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.applicationCase.QueryApplicationCaseListDto;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.exhibitionCaseMap.ExhibitionCaseMapVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseQuestionAndAnswerVo;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.QueryApplicationCaseListVo;

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
	
	/**
	 * 根据物性ID分页查询关联应用案例
	 * @param specId
	 * @return
	 */
	List<PcApplicationCaseSimpleVo> selectSpecCaseBySpecIdPage(Integer specId);
	
	/**
	 * 根据物性ID统计物性表关联案例数量
	 * @param specId
	 * @return
	 */
	int countSpecCaseBySpecId(Integer specId);

	
	/**
	 * 得到所有案例，包含案例id，案例名称，物性规格，应用行业，应用市场，生产商，基材
	 */
	List<ApplicationCase> selectAllApplicationCaseDetails();

	/**
	 * 根据案例id，包含生产商id，把生产商id映射到ApplicationCase的用户id上
	 * @param id
	 * @return
	 */
	ApplicationCase selectAppCaseDetailsById(Integer id);

	/**
	 * 根据展会id，得到展会下所有案例(案例都显示，已关联的有标记字段)
	 * @param id 展会id
	 * @return
	 */
	List<ExhibitionCaseMapVo> selectApplicationCaseDetailsByExhibitionIdMake(Integer id);

	/**
	 * 得到所有案例，条件查询
	 * @param listDto
	 * @return
	 */
	List<QueryApplicationCaseListVo> selectAllApplicationCaseSelective(QueryApplicationCaseListDto listDto);

	/**
	 * 首页热门案例查询
	 * @return
	 */
	List<IndexHotApplicationCaseVo> selectHotApplicationCase();

	
	/**
	 * 根据案例id，得到提问信息
	 * @param id 案例id
	 * @return
	 */
	List<IndexHotApplicationCaseQuestionAndAnswerVo> selectHotApplicationCaseQuestionByAid(Integer id);

	/**
	 * 通过提问id，得到回答信息
	 * @param id 提问id
	 * @return
	 */
	String selectselectHotApplicationCaseAnswerByQid(Integer id);
}
