package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerdQuotation.QueryMyDealerdQuotationPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

/**
 * 物性表材料报价单ServiceImpl
 * @author yangy
 *
 */

@Service
public class DealerdQuotationServiceImpl implements DealerdQuotationService {

	@Autowired
	private DealerdQuotationMapper dealerdQuotationMapper;
	
	/**
	 * 新增材料报价单
	 * @param record
	 * @return
	 */
	@Override
	public int insertDealerdQuotation(DealerdQuotation record){
		
		//判断物性规格和色号是否已经存在
		DealerdQuotation validExist = dealerdQuotationMapper.selectDealerdQuotationBySpecAndColor(record.getSid(),record.getColor());
		if(validExist != null){
			throw new CommonException(StatusCode.DEALERDQUOTATION_IS_EXIST);
		}
		
		//新增材料报价单
		int count = dealerdQuotationMapper.insertSelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 编辑材料报价单
	 * @param record
	 * @return
	 */
	@Override
	public int updateDealerdQuotation(DealerdQuotation record){
		
		int count = dealerdQuotationMapper.updateByPrimaryKeySelective(record);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据id删除材料报价单
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDealerdQuotationById(Integer id){
		
		int count = dealerdQuotationMapper.deleteByPrimaryKey(id);
		if(count == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
	}
	
	/**
	 * 根据ID查询材料报价单信息
	 * @param id
	 * @return
	 */
	@Override
	public DealerdQuotation findDealerdQuotationById(Integer id){
		DealerdQuotation temp = dealerdQuotationMapper.selectByPrimaryKey(id);
		return temp;
	}
	
	/**
	 * 根据物性规格ID查询材料报价单
	 * @param specId
	 * @return
	 */
	@Override
	public List<DealerdQuotationDetailVo> findDealerdQuotationBySpecId(Integer specId){
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectDealerdQuotationBySpecId(specId);
		return list;
	}
	
	/**
	 * 根据用户ID分页查询材料报价单
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<DealerdQuotationDetailVo> findDealerdQuotationByUidPage(QueryMyDealerdQuotationPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectDealerdQuotationByUid(form.getUserId());
		PageInfo<DealerdQuotationDetailVo> result = new PageInfo<DealerdQuotationDetailVo>(list);
		return result;
	}
	
	/**
     * 根据ID查询材料报价单详情
     * @return
     */
	@Override
    public DealerdQuotationDetailVo findDealerdQuotationDetailById(Integer id){
		DealerdQuotationDetailVo temp = dealerdQuotationMapper.selectDealerdQuotationDetailById(id);
		return temp;
    }
}
