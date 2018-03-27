package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.Exhibition;

public interface ExhibitionMapper {
	/**
	 * 根据展会id，删除展会
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加展会
     * @param record
     * @return
     */
    int insert(Exhibition record);

    /**
     * 添加展会，非空字段不设置
     * @param record
     * @return
     */
    int insertSelective(Exhibition record);

    /**
     * 根据展会id，得到展会所有信息(不判断标记状态)
     * @param id
     * @return
     */
    Exhibition selectByPrimaryKey(Integer id);

    /**
     * 根据展会id，更新非空字段(文本字段非空也会更新)
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Exhibition record);

    /**
     * 根据展会id，更新字段(文本字段会更新)，不会判断非空
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBs(Exhibition record);

    /**
     * 根据展会id，更新字段(文本字段不会更新)
     * @param record
     * @return
     */
    int updateByPrimaryKey(Exhibition record);

    /**
     * 得到所有展会
     * @return
     */
	List<Exhibition> selectAllExhibition();
}