package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.dto.pc.dealerProductMap.DealerProductMapDetailVo;
import com.zlwon.rdb.entity.DealerProductMap;
import com.zlwon.vo.web.dealerdQuotation.DealerProductMapSimpleVo;

/**
 * 经销商可供产品Mapper
 * @author yangy
 *
 */

public interface DealerProductMapMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(DealerProductMap record);

    int insertSelective(DealerProductMap record);

    DealerProductMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealerProductMap record);

    int updateByPrimaryKey(DealerProductMap record);
    
    /**
     * 根据用户ID，生产商ID，商标ID查询经销商可供产品记录
     * @param manufacturerId
     * @param brandId
     * @param userId
     * @return
     */
    DealerProductMap selectDealerProductMapByUserAndBrand(@Param("manufacturerId") Integer manufacturerId,@Param("brandId") Integer brandId,@Param("userId") Integer userId);
    
    /**
     * 根据用户ID查询经销商可供产品记录
     * @param userId
     * @return
     */
    List<DealerProductMapSimpleVo> selectDealerProductMapByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据物性ID查询经销商可供产品记录
     * @param specId
     * @return
     */
    List<DealerProductMapDetailVo> selectDealerProductMapBySpecId(@Param("specId") Integer specId);
}