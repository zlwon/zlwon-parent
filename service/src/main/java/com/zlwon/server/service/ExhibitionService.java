package com.zlwon.server.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Exhibition;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rdb.entity.ExhibitionCaseMap;

/**
 * 展会Service
 * @author yangy
 *
 */

public interface ExhibitionService {
	
	/**
	 * 得到所有展会，分页显示
	 * @param pageIndex 页码
	 * @param pageSize 页个数
	 * @return
	 */
	PageInfo<Exhibition> findAllExhibitionPage(Integer pageIndex, Integer pageSize);

	/**
	 * 添加展会
	 * @param exhibition
	 * @return
	 */
	int saveExhibition(Exhibition exhibition);
	
	
	/**
	 * 根据展会id，得到展会所有信息(不判断标记状态)
	 * @param id
	 * @return
	 */
	Exhibition findExhibitionInfoById(Integer id);


	/**
	 * 保存编辑后的展会信息（只操作普通信息）
	 * @param exhibition
	 * @return
	 */
	int alterExhibitionSelective(Exhibition exhibition);


	/**
	 * 根据展会id，删除展会
	 * @param id
	 * @return
	 */
	int removeExhibitionById(Integer id);


	/**
	 * 展会案例关联工程师,查看该案例是否已属于该展会，属于就添加
	 * @param exhibitionCase
	 * @return
	 */
	int saveExhibitionApplicationCase(ExhibitionCase exhibitionCase,Integer  exhibitionId);
	
	/**
	 * 根据展会案例ID查询展会案例信息
	 * @param id
	 * @return
	 */
	ExhibitionCase selectExhibitionCaseById(Integer id);

	/**
	 * 根据展会id，得到展会所有案例(只显示已关联的)
	 * @param pageIndex
	 * @param pageSize
	 * @param id
	 * @return
	 */
	PageInfo<ApplicationCase> findAllExhibitionAppByIdMake(Integer pageIndex, Integer pageSize, Integer id);

	/**
	 * 展会添加案例
	 * 由于该表(zl_exhibition_case_map)删除的时候是delete操作，所以不用判断是否是虚拟的删除，但是要判断展会id和案例id是否存在，防止表单重复提交
	 * @param exhibitionCaseMap
	 * @return
	 */
	int saveExhibitionApp(ExhibitionCaseMap exhibitionCaseMap,HttpServletRequest  request);

	/**
	 * 删除展会案例，根据案例id和展会id
	 * @param exhibitionCaseMap
	 * @return
	 */
	int removeExhibitionAppByCaseIdAndEid(ExhibitionCaseMap exhibitionCaseMap);

	/**
	 * 取消展会案例关联工程师
	 * @param exhibitionCase
	 * @param exhibitionId
	 * @return
	 */
	int cancelExhibitionApplicationCase(ExhibitionCase exhibitionCase, Integer exhibitionId);

	/**
	 * 展会案例修改工程师，如果展会案例已关联工程师，需要把之前的标记为删除，如果之前关联的工程师id和该id一样，也是执行删除
	 * @param aid 案例id
	 * @param eid 展会id
	 * @param cid 工程师(知料师)id
	 * @return
	 */
	int alterExhibitionApplicationCase(Integer  aid,Integer  eid,Integer  cid);

	/**
	 * 根据展会id，得到展会下所有案例(案例都显示，已关联的有标记字段)
	 * @param pageIndex
	 * @param pageSize
	 * @param id 展会id
	 * @return
	 */
	PageInfo findAllExhibitionAppDetailsByIdMake(Integer pageIndex, Integer pageSize, Integer id);

	
	/**
	 * 得到所有工程师(而且通过展会id和案例id，标识已关联的工程师)，后端查看展会案例关联的工程师
	 * @param pageIndex 
	 * @param pageSize 
	 * @param aid 案例id
	 * @param eid 展会id
	 * @return
	 */
	PageInfo findAllEngineer(Integer pageIndex,Integer pageSize,Integer aid, Integer eid);
}
