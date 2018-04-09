package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.dto.specification.SpecificationDto;
import com.zlwon.nosql.entity.SpecificationData;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.vo.specification.SpecificationDetailVo;

/**
 * 物性表Service
 * @author yangy
 *
 */

public interface SpecificationService {

	/**
	 * 根据id查询物性表
	 * @param id  自增ID
	 * @return
	 */
	Specification findSpecificationById(Integer id);
	
	/**
	 * 根据规格名称查询物性表
	 * @param name  规格名称
	 * @return
	 */
	Specification findSpecificationByName(String name);
	
	/**
	 * 根据商标ID查询物性表
	 * @param brand  商标名
	 * @return
	 */
	List<Specification> findSpecificationByBrand(Integer brandId);
	
	/**
	 * 根据生产商ID查询物性表
	 * @param manuf  生产商
	 * @return
	 */
	List<Specification> findSpecificationByManuf(Integer manufId);
	
	/**
	 * 根据id查询物性具体详情信息
	 * @param id
	 * @return
	 */
	SpecificationDetailVo findSpecDetailById(Integer id);
	
	/**
	 * 根据规格名称查询物性具体详情信息
	 * @param name
	 * @return
	 */
	SpecificationDetailVo findSpecDetailByName(String name);

	/**
	 * 添加物性，需要判断规格名称是否重复
	 * @param specification
	 * @return
	 */
	int saveSpecificationMake(SpecificationDto specification);

	/**
	 * 执行保存更新后的物性信息，需要先判断该物性是否存在，然后在判断要修改后的规格名称是否存在
	 * @param specification
	 * @return
	 */
	int alterSpecificationById(Specification specification);

	/**
	 * 根据物性id，删除指定的物性信息
	 * @param id
	 * @return
	 */
	int removeSpecificationById(Integer id);

	/**
	 * 得到所有物性（状态正常），分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param message 模糊搜索
	 * @return
	 */
	PageInfo<SpecificationDetailVo> findAllSpecificationMake(Integer pageIndex, Integer pageSize,String  message);
	
	/**
	 * pc端分页查询物性表信息
	 * @param form
	 * @return
	 */
	PageInfo<SpecificationDetailVo> findSpecifyByPcPage(PcSearchSpecPageDto form);
	
	/**
	 * mongodb
	 * 根据noSql ID查询物性表信息
	 * @param noSqlId
	 * @return
	 */
	SpecificationData findSpecificationDataById(String noSqlId);
}
