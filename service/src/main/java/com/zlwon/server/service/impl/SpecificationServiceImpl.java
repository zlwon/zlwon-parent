package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.specification.QueryWCSpecByPageDto;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.dto.specification.SpecificationDto;
import com.zlwon.exception.CommonException;
import com.zlwon.nosql.dao.SpecificationDataRepository;
import com.zlwon.nosql.dao.SpecificationRepository;
import com.zlwon.nosql.entity.SpecificationData;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.AttributeMapper;
import com.zlwon.rdb.dao.ProcessingAdviceMapper;
import com.zlwon.rdb.dao.QuestionsMapper;
import com.zlwon.rdb.dao.SpecificationMapper;
import com.zlwon.rdb.dao.SpecificationParameterMapper;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.specification.SpecificationDetailVo;
import com.zlwon.vo.specification.SpecificationVo;

/**
 * 物性表ServiceImpl
 * @author yangy
 *
 */

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;
	
	@Autowired
	private SpecificationRepository specificationRepository;
	
	@Autowired
	private ApplicationCaseMapper applicationCaseMapper;
	
	@Autowired
	private QuestionsMapper questionsMapper;
	
	@Autowired
	private ProcessingAdviceMapper   processingAdviceMapper;
	
	@Autowired
	private  AttributeMapper   attributeMapper;
	
	@Autowired
	private  SpecificationDataRepository   specificationDataRepository;
	
	
	@Autowired
	private  SpecificationParameterMapper  specificationParameterMapper;
	
	/**
	 * 根据id查询物性表
	 * @param id  自增ID
	 * @return
	 */
	@Override
	public Specification findSpecificationById(Integer id){
		Specification temp = specificationMapper.findSpecificationById(id);
		return temp;
	}
	
	/**
	 * 根据规格名称查询物性表
	 * @param name  规格名称
	 * @return
	 */
	@Override
	public Specification findSpecificationByName(String name){
		Specification temp = specificationMapper.findSpecificationByName(name);
		return temp;
	}
	
	/**
	 * 根据规格名称模糊查询物性表
	 * @param specName
	 * @return
	 */
	@Override
	public List<Specification> findSpecificationByLikeName(String specName){
		List<Specification> list = specificationMapper.selectSpecificationByLikeName(specName);
		return list;
	}
	
	/**
	 * 根据规格名称模糊搜索标签和生产商ID查询物性表
	 * @param specName
	 * @param mid
	 * @return
	 */
	@Override
	public List<Specification> findSpecificationByLikeNameMid(String specName,Integer mid){
		List<Specification> list = specificationMapper.selectSpecificationByLikeNameMid(specName, mid);
		return list;
	}
	
	/**
	 * 根据商标ID查询物性表
	 * @param brandId  商标id
	 * @return
	 */
	@Override
	public List<Specification> findSpecificationByBrand(Integer brandId){
		List<Specification> list = specificationMapper.findSpecificationByBrand(brandId);
		return list;
	}
	
	/**
	 * 根据生产商ID查询物性表
	 * @param manufId  生产商id
	 * @return
	 */
	@Override
	public List<Specification> findSpecificationByManuf(Integer manufId){
		List<Specification> list = specificationMapper.findSpecificationByManuf(manufId);
		return list;
	}
	
	/**
	 * 根据id查询物性具体详情信息
	 * @param id
	 * @return
	 */
	@Override
	public SpecificationDetailVo findSpecDetailById(Integer id){
		SpecificationDetailVo temp = specificationMapper.findSpecDetailById(id);
		return temp;
	}
	
	/**
	 * 根据规格名称查询物性具体详情信息
	 * @param name
	 * @return
	 */
	@Override
	public SpecificationDetailVo findSpecDetailByName(String name){
		SpecificationDetailVo temp = specificationMapper.findSpecDetailByName(name);
		return temp;
	}

	/**
	 * 添加物性，需要判断规格名称是否重复
	 */
	@Transactional
	public int saveSpecificationMake(SpecificationDto specification) {
		//判断规格名称是否已存在，规格名称唯一
		Specification  record = specificationMapper.selectSpecificationByNameMake(specification.getName());
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		record = new Specification();
		try {
			BeanUtils.copyProperties(record, specification);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//执行添加物性操作
		record.setDel(1);
		record.setCreateTime(new  Date());
		return specificationMapper.insert(record);
	}

	/**
	 * 执行保存更新后的物性信息，需要先判断该物性是否存在，然后在判断要修改后的规格名称是否存在
	 */
	@Override
	public int alterSpecificationById(Specification specification) {
		//查看该物性是否存在
		Specification record  = specificationMapper.selectByPrimaryKey(specification.getId());
		if(record == null  ||  record.getDel() != 1){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		if(StringUtils.isNotBlank(specification.getName())){
			//查看规格名称是否存在(标记状态正常的)
			record = specificationMapper.selectSpecificationByNameMake(specification.getName());
			if(record != null && !record.getId().equals(specification.getId())){
				throw  new  CommonException(StatusCode.DATA_IS_EXIST);
			}
		}
		
		return specificationMapper.updateByPrimaryKeySelective(specification);
	}

	/**
	 * 根据物性id，删除指定的物性信息
	 */
	@Override
	public int removeSpecificationById(Integer id) {
		Specification record = new Specification();
		record.setId(id);
		record.setDel(-1);
		return specificationMapper.updateByPrimaryKeySelective(record );
	}

	/**
	 *  得到所有物性(正常状态)
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<SpecificationDetailVo> findAllSpecificationMake(Integer pageIndex, Integer pageSize,String  message) {
		PageHelper.startPage(pageIndex, pageSize);
		List<SpecificationDetailVo>  list = specificationMapper.selectAllSpecificationMake(message);
		return new  PageInfo<SpecificationDetailVo>(list);
	}
	
	/**
	 * pc端分页查询物性表信息
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<SpecificationDetailVo> findSpecifyByPcPage(PcSearchSpecPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<SpecificationDetailVo> list = specificationMapper.selectSpecifyByPcPage(form);
		
		/*//循环统计
		for(SpecificationDetailVo temp : list){
			
			//统计关联案例数量
			int caseCount = applicationCaseMapper.countSpecCaseBySpecId(temp.getId());
			temp.setCaseCount(caseCount);
			
			//统计问答数量
			int questionCount = questionsMapper.countQuestionsByInfoId(temp.getId(), 1);
			temp.setQuestionCount(questionCount);
		}
		*/
		PageInfo<SpecificationDetailVo> result = new PageInfo<SpecificationDetailVo>(list);
		return result;
	}
	
	/**
	 * 小程序端分页查询物性信息
	 * @param form
	 * @return
	 */
	@Override
	public PageInfo<SpecificationDetailVo> findWCSpecByPage(QueryWCSpecByPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<SpecificationDetailVo> list = specificationMapper.selectWCSpecByPage(form);
		PageInfo<SpecificationDetailVo> result = new PageInfo<SpecificationDetailVo>(list);
		return result;
	}
	
	/**
	 * mongodb
	 * 根据noSql ID查询物性表信息
	 * @param noSqlId
	 * @return
	 */
	@Override
	public SpecificationData findSpecificationDataById(String noSqlId){
		SpecificationData result = specificationRepository.findSpecificationDataById(noSqlId);
		return result;
	}

	
	/**
	 * 根据生产商id，得到所有物性，不分页，可模糊查询物性规格名称
	 * @param id 生产商id
	 * @param key 关键字，可模糊查询物性规格名称
	 * @return
	 */
	public List<Specification> findSpecificationByMid(Integer id,String  key) {
		return specificationMapper.selectSpecificationByMidMake(id,key);
	}

	/**
	 * 根据物性id，得到物性详情
	 */
	public SpecificationVo findSpecificationDetailsById(Integer id) {
		SpecificationVo  vo = specificationMapper.selectSpecificationDetailsById(id);
		if(vo != null && StringUtils.isNotBlank(vo.getScidStr())){
			//根据安规认证标签详情id，得到安规认证标签id,目前是安规认证标签详情id肯定都是指定同一个安规认证标签id
			String[] split = vo.getScidStr().split(",");
			if(split != null  &&  split.length > 0){
				SpecificationParameter param = specificationParameterMapper.selectByPrimaryKey(Integer.valueOf(split[0]));
				if(param != null){
					vo.setScid(param.getParentId());
				}
			}
			
		}
		return vo;
	}
	
	
}
