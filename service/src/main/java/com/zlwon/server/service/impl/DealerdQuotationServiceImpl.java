package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerdQuotation.InsertDealerdQuotationDto;
import com.zlwon.dto.pc.dealerdQuotation.QueryMyDealerdQuotationPageDto;
import com.zlwon.dto.web.dealerdQuotation.ExamineDealerdQuotationDto;
import com.zlwon.dto.web.dealerdQuotation.QueryAllDealerdQuotationPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.DealerdQuotationMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.dao.SpecificationMapper;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
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
	
	@Autowired
	private InformMapper informMapper;
	
	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
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
				
				count_num = count_num + 1;
				
				//查询物性规格
				Specification validSpec = specificationMapper.findSpecificationByName(temp.getSpecName());
				if(validSpec == null){
					//throw new CommonException(StatusCode.SPECIFICATION_NOT_EXIST);
					throw new CommonException("000038","第"+String.valueOf(count_num)+"条记录物性规格不存在");
				}
				
				//判断物性规格和色号是否已经存在
				DealerdQuotation validExist = dealerdQuotationMapper.selectDealerdQuotationBySpecAndColor(validSpec.getId(),temp.getColor(),userId);
				if(validExist != null){
					//throw new CommonException(StatusCode.DEALERDQUOTATION_IS_EXIST);
					throw new CommonException("000037","第"+String.valueOf(count_num)+"条记录规格色号材料报价单已存在，请勿重复添加");
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
	
	/**
     * 查询全部材料报价单
     * @param form
     * @return
     */
	@Override
	public PageInfo<DealerdQuotationDetailVo> findAllDealerdQuotationPage(QueryAllDealerdQuotationPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectAllDealerdQuotation();
		PageInfo<DealerdQuotationDetailVo> result = new PageInfo<DealerdQuotationDetailVo>(list);
		return result;
    }
	
	/**
     * 审核材料报价单
     * @param form
     * @return
     */
	@Transactional
    public int examineDealerdQuotation(ExamineDealerdQuotationDto form){
		DealerdQuotation mydealer = dealerdQuotationMapper.selectByPrimaryKey(form.getId());
		if(mydealer == null){
			throw new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//审核操作与原状态相同
		if(mydealer.getExamine() == form.getExamine()){
			throw new CommonException(StatusCode.EXAMINE_STATUS_ERROR);
		}
		
		//审核
		mydealer.setExamine(form.getExamine());
		int count = dealerdQuotationMapper.updateByPrimaryKeySelective(mydealer);
		
		//添加通知消息
		Inform record = new Inform();
		record.setContent(form.getReason());
		record.setCreateTime(new  Date());
		record.setIid(form.getId());
		record.setReadStatus((byte) 0);
		if(form.getExamine() == 1){
			record.setStatus((byte) 1);
		}else{
			record.setStatus((byte) 0);
		}
		record.setType((byte) 5);
		record.setUid(mydealer.getUid());
		
		int countInform = informMapper.insertSelective(record);
		
		//通过，增加积分
		if(form.getExamine() == 1){
			
			//增加积分
			int upCount = customerMapper.updateIntegrationByUid(mydealer.getUid(), IntegrationDeatilCode.INSERT_QUOTATION.getNum());
			if(upCount == 0){
				throw new CommonException(StatusCode.SYS_ERROR);
			}
			
			//新增积分异动明细
			IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
			interDeatil.setType(IntegrationDeatilCode.INSERT_QUOTATION.getCode());
			interDeatil.setDescription(IntegrationDeatilCode.INSERT_QUOTATION.getMessage());
			interDeatil.setIntegrationNum(IntegrationDeatilCode.INSERT_QUOTATION.getNum());
			interDeatil.setChangeType(1);
			interDeatil.setUid(mydealer.getUid());
			interDeatil.setCreateTime(new Date());
			
			int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
			if(igCount == 0){
				throw new CommonException(StatusCode.SYS_ERROR);
			}
		}
		return count;
    }

	/**
	 * web端首页查看未审核的报价单，不分页
	 * @return
	 */
	public List<DealerdQuotationDetailVo> findNotExamineDealerdQuotation(Integer pageSize) {
		List<DealerdQuotationDetailVo> list = dealerdQuotationMapper.selectNotExamineDealerdQuotation(pageSize);
		return list;
	}
	
	
	
}
