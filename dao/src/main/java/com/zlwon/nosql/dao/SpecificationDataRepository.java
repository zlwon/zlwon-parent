package com.zlwon.nosql.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.zlwon.nosql.entity.SpecificationData;

/**
 * 物性表加工建议、属性数据api
 * @author yuand
 *
 */
@Repository
public class SpecificationDataRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 新增记录
	 * @param user
	 */
	public void add(SpecificationData specificationData){
		this.mongoTemplate.insert(specificationData);
	}
}
