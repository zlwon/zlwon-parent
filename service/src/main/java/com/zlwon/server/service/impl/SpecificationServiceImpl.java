package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.SpecificationMapper;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.specification.SpecificationDetailVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 物性表ServiceImpl
 * @author yangy
 *
 */

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;
	
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
	@Override
	public int saveSpecificationMake(Specification specification) {
		//判断规格名称是否已存在，规格名称唯一
		Specification  record = specificationMapper.selectSpecificationByNameMake(specification.getName());
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		// TODO 物性关联的加工建议，应用案例、属性数据还未存到mongodb，
		//执行添加操作
//		specification.setNsid(nsid);//设置nosqlID
		specification.setNsid(0+"");//后期需要改，
		specification.setDel(1);
		specification.setCreateTime(new  Date());
		return specificationMapper.insert(specification);
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
			if(record != null && record.getId() != specification.getId()){
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
		return specificationMapper.deleteByPrimaryKey(id);
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
		PageInfo<SpecificationDetailVo> result = new PageInfo<>(list);
		return result;
	}
}
