package com.zlwon.nosql.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.zlwon.nosql.entity.SpecificationData;

/**
 * mongodb物性表信息
 * @author yangy
 *
 */

@Repository
public class SpecificationRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 根据noSql ID查询物性表信息
	 * @param noSqlId
	 * @return
	 */
	public SpecificationData findSpecificationDataById(String noSqlId){
		SpecificationData result = mongoTemplate.findById(noSqlId, SpecificationData.class);
		return result;
	};
}
