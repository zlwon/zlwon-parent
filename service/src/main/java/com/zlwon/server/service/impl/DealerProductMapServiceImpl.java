package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.web.dealerProductMap.InsertDealerProductMapDto;
import com.zlwon.dto.web.dealerProductMap.QueryDealerProductMapByUidPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.DealerProductMapMapper;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.DealerProductMap;
import com.zlwon.server.service.DealerProductMapService;
import com.zlwon.vo.pc.dealerdQuotation.DealerProductMapDetailVo;
import com.zlwon.vo.web.dealerdQuotation.DealerProductMapSimpleVo;

/**
 * 经销商可供产品ServiceImpl
 * @author yangy
 *
 */

@Service
public class DealerProductMapServiceImpl implements DealerProductMapService {

	@Autowired
	private DealerProductMapMapper dealerProductMapMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	/**
	 * 批量新增经销商可供产品记录(系统管理员)
	 * @param record
	 * @return
	 */
	@Transactional
	public int insertBatchDealerProductMap(List<InsertDealerProductMapDto> formList,Integer userId){
		
		//验证当前用户是否是经销商
		Customer currentUser = customerMapper.selectCustomerById(userId);
		if(currentUser.getRole() != 4){  //判断当前用户是否是经销商
			throw new CommonException(StatusCode.PERMIT_USER_LIMIT);
		}
		
		int count_num = 0;
		
		//循环
		if(formList != null && formList.size() > 0){
			for(InsertDealerProductMapDto temp : formList){
				count_num = count_num + 1;
				
				//验证当前用户是否已经拥有该产品商标（产品）是否已经存在
				DealerProductMap validProduct = dealerProductMapMapper.selectDealerProductMapByUserAndBrand(temp.getManufacturerId(), temp.getBrandId(), userId);
				if(validProduct != null){
					throw new CommonException("000016","第"+String.valueOf(count_num)+"条记录可供产品已存在存在，请勿重复添加");
				}
				
				//新增经销商可供产品记录
				DealerProductMap record = new DealerProductMap();
				record.setManufacturerId(temp.getManufacturerId());
				record.setBrandId(temp.getBrandId());
				record.setAvailableArea(temp.getAvailableArea());
				record.setAvailableIndustry(temp.getAvailableIndustry());
				record.setUid(userId);
				record.setExamine(1);
				record.setCreateTime(new Date());
				
				int count = dealerProductMapMapper.insertSelective(record);
				if(count == 0){
					throw new CommonException(StatusCode.SYS_ERROR);
				}
			}
		}
		
		return count_num;
	}
	
	/**
	 * 编辑经销商可供商品记录
	 * @param record
	 * @return
	 */
	@Override
	public int updateDealerProductMap(DealerProductMap record){
		
		int count = dealerProductMapMapper.updateByPrimaryKeySelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据ID删除经销商可供商品记录
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDealerProductMapById(Integer id){
		
		int count = dealerProductMapMapper.deleteByPrimaryKey(id);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据ID查询经销商可供商品记录
	 * @param id
	 * @return
	 */
	@Override
	public DealerProductMap findDealerProductMapById(Integer id){
		
		DealerProductMap result = dealerProductMapMapper.selectByPrimaryKey(id);
		return result;
	}
	
	/**
	 * web端分页查询经销商可供产品
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<DealerProductMapSimpleVo> findDealerProductMapByUidPage(QueryDealerProductMapByUidPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<DealerProductMapSimpleVo> list = dealerProductMapMapper.selectDealerProductMapByUserId(form.getUserId());
		PageInfo<DealerProductMapSimpleVo> result = new PageInfo<DealerProductMapSimpleVo>(list);
		return result;
	}
	
	/**
	 * 根据物性ID查询经销商可供产品
	 * @param specId
	 * @return
	 */
	@Override
	public List<DealerProductMapDetailVo> findDealerProductMapBySpecId(Integer specId){
		List<DealerProductMapDetailVo> list = dealerProductMapMapper.selectDealerProductMapBySpecId(specId);
		return list;
	}
}
