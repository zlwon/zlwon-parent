package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.web.dealerProductMap.InsertDealerProductMapDto;
import com.zlwon.dto.web.dealerProductMap.QueryDealerProductMapByUidPageDto;
import com.zlwon.rdb.entity.DealerProductMap;
import com.zlwon.vo.web.dealerdQuotation.DealerProductMapSimpleVo;

/**
 * 经销商可供产品Service
 * @author yangy
 *
 */

public interface DealerProductMapService {

	/**
	 * 批量新增经销商可供产品记录(系统管理员)
	 * @param record
	 * @return
	 */
	int insertBatchDealerProductMap(List<InsertDealerProductMapDto> formList,Integer userId);
	
	/**
	 * 编辑经销商可供商品记录
	 * @param record
	 * @return
	 */
	int updateDealerProductMap(DealerProductMap record);
	
	/**
	 * 根据ID删除经销商可供商品记录
	 * @param id
	 * @return
	 */
	int deleteDealerProductMapById(Integer id);
	
	/**
	 * 根据ID查询经销商可供商品记录
	 * @param id
	 * @return
	 */
	DealerProductMap findDealerProductMapById(Integer id);
	
	/**
	 * web端分页查询经销商可供产品
	 * @param form
	 * @return
	 */
	PageInfo<DealerProductMapSimpleVo> findDealerProductMapByUidPage(QueryDealerProductMapByUidPageDto form);
}
