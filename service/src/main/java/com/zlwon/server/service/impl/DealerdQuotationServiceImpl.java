package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerdQuotation.InsertDealerdQuotationDto;
import com.zlwon.dto.pc.dealerdQuotation.QueryMyDealerdQuotationPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.rdb.dao.SpecificationMapper;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.rdb.entity.Specification;
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
	
	@Autowired
	private SpecificationMapper specificationMapper;
	
	/**
	 * 新增材料报价单
	 * @param record
	 * @return
	 */
	@Override
	public int insertDealerdQuotation(DealerdQuotation record){
		
		//判断物性规格和色号是否已经存在
		DealerdQuotation validExist = dealerdQuotationMapper.selectDealerdQuotationBySpecAndColor(record.getSid(),record.getColor(),record.getUid());
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
	 * 批量新增材料报价单
	 * @param form
	 * @param userId
	 * @return
	 */
	@Transactional
	public int insertDealerdQuotationBatch(List<InsertDealerdQuotationDto> form,Integer userId){
		
		int count_num = 0;
		
		//循环
		if(form != null && form.size() > 0){
			for(InsertDealerdQuotationDto temp : form){
				//查询物性规格
				Specification validSpec = specificationMapper.findSpecificationByName(temp.getSpecName());
				if(validSpec == null){
					throw new CommonException(StatusCode.SPECIFICATION_NOT_EXIST);
				}
				
				//判断物性规格和色号是否已经存在
				DealerdQuotation validExist = dealerdQuotationMapper.selectDealerdQuotationBySpecAndColor(validSpec.getId(),temp.getColor(),userId);
				if(validExist != null){
					throw new CommonException(StatusCode.DEALERDQUOTATION_IS_EXIST);
				}
				
				//新增材料报价单
				DealerdQuotation record = new DealerdQuotation();
				record.setUid(userId);
				record.setSid(validSpec.getId());
				record.setColor(temp.getColor());
				record.setPrice(temp.getPrice());
				record.setValidityDate(temp.getValidityDate());
				record.setOrderQuantity(temp.getOrderQuantity());
				record.setDeliveryDate(temp.getDeliveryDate());
				record.setDeliveryPlace(temp.getDeliveryPlace());
				record.setPayMethod(temp.getPayMethod());
				record.setExamine(0);
				record.setCreateTime(new Date());
				
				//新增材料报价单
				int count = dealerdQuotationMapper.insertSelective(record);
				if(count == 0){
					throw new CommonException(StatusCode.SYS_ERROR);
				}
				
				count_num = count_num + 1;
			}
		}
		
		return count_num;
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
