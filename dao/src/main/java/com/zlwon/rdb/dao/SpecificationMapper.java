package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.vo.specification.SpecificationDetailVo;
import com.zlwon.vo.specification.SpecificationVo;

/**
 * 物性表Mapper
 * @author yangy
 *
 */

public interface SpecificationMapper {

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

	
	
	
	
	//-------------自动生成------------
	int deleteByPrimaryKey(Integer id);

    int insert(Specification record);

    int insertSelective(Specification record);

    Specification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Specification record);

    int updateByPrimaryKey(Specification record);
    
    
    /**
	 * 根据规格名称得到物性信息,标记状态为正常的规格名称只能存在一个
	 * @param name
	 * @return
	 */
	Specification selectSpecificationByNameMake(String name);

	/**
	 * 得到所有物性(正常状态)
	 * @return
	 */
	List<SpecificationDetailVo> selectAllSpecificationMake(@Param("message") String  message);
	
	/**
	 * pc端分页查询物性表信息
	 * @param form
	 * @return
	 */
	List<SpecificationDetailVo> selectSpecifyByPcPage(PcSearchSpecPageDto form);

	/**
	 * 根据生产商id，得到所有物性，可模糊查询物性规格名称
	 * @param id 生产商id
	 * @param key 关键字，可模糊查询物性规格名称
	 * @return
	 */
	List<Specification> selectSpecificationByMidMake(@Param("id")Integer id,@Param("key")String  key);

	/**
	 * 根据物性id，得到物性详情
	 * @param id
	 * @return
	 */
	SpecificationVo selectSpecificationDetailsById(Integer id);
}
