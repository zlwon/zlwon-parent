package com.zlwon.nosql.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.zlwon.nosql.entity.TestUser;

@Repository
public class TestRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/*@Autowired  
	private MongoOperations mongoOperations; */ 
	
	/**
	 * 新增记录
	 * @param user
	 */
	public void add(TestUser user){
		this.mongoTemplate.insert(user);
	}
	
	/**
	 * 修改记录
	 * @param user
	 */
	public void update(TestUser user){
		this.mongoTemplate.save(user);
	}
	
	/**
	 * 根据ID删除对象
	 * @param id
	 */
	public void delete(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		this.mongoTemplate.remove(query, TestUser.class);
	}
	
	/**
	 * 根据ID查询对象记录
	 * @param id
	 * @return
	 */
	public TestUser findTestUserById(String id){
		return this.mongoTemplate.findById(id, TestUser.class);
	}
	
	/**
	 * 根据昵称查询所有符合条件的对象记录
	 * @param nickName
	 * @return
	 */
	public List<TestUser> findTestUserByNickName(String nickName){
		Query query = new Query();
		query.addCriteria(Criteria.where("nickName").is(nickName));
		return this.mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 根据名称查询单个对象记录
	 * @param name
	 * @return
	 */
	public TestUser findTestUserByName(String name){
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return this.mongoTemplate.findOne(query, TestUser.class);
	}
	
	/**
	 * 统计该昵称的数量
	 * @param nickName
	 * @return
	 */
	public long countTestUserByNickName(String nickName){
		Query query = new Query();
		query.addCriteria(Criteria.where("nickName").is(nickName));
		return this.mongoTemplate.count(query, TestUser.class);
	}
	
	/**
	 * 根据昵称查询所有符合条件的对象记录
	 * 模糊查询
	 * @param nickName
	 * @return
	 */
	public List<TestUser> findTestUserByNickNameLike(String nickName){
		Query query = new Query();
		query.addCriteria(Criteria.where("nickName").regex(nickName));
		return this.mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 测试or条件
	 * @param nickName
	 * @return
	 */
	public List<TestUser> findTestUserByNickNameOr(String nickName){
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("nickName").regex(nickName),Criteria.where("name").is("Tom"));
		Query query = new Query(criteria);
		return this.mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 测试and条件
	 * @param nickName
	 * @return
	 */
	public List<TestUser> findTestUserByNickNameAnd(String nickName){
		Criteria criteria = new Criteria();
		criteria.and("nickName").regex(nickName);
		criteria.and("name").is("Tom");
		Query query = new Query(criteria);
		return this.mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 调用公共方法
	 * @param nickName
	 * @return
	 */
	public List<TestUser> findTestUserPublicQuery(String nickName){
		Query query = getQueryTest("111","222","1212");
		return this.mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 分页，需要匹配对应的总记录数
	 * @param pageSize
	 * @param pageFrom
	 * @return
	 */
	public List<TestUser> findtestUserPage(int pageSize, int pageFrom){
		Query query = getQueryTest("111","222","1212");
		if(pageSize > 0){
			query.skip(pageFrom).limit(pageSize);
		}
		return mongoTemplate.find(query, TestUser.class);
	}
	
	/**
	 * 共用query
	 * @param nickName
	 * @param name
	 * @param createTime
	 * @return
	 */
	public Query getQueryTest(String nickName,String name,String createTime){
		Query query = new Query();
		
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = sdf.format(new Date());
			String endTime = sdf.format(new Date(sdf.parse(nowTime).getTime()+24*60*60*1000));
			
			if(StringUtils.isNotBlank(nickName)){
				Criteria criteria = Criteria.where("nickName").regex(nickName);
				query.addCriteria(criteria);
			}
			
			if(StringUtils.isNotBlank(nickName)){
				Criteria criteria = Criteria.where("name").regex(name);
				query.addCriteria(criteria);
			}
			
			//比较时间
			if(StringUtils.isNotBlank(createTime)){
				Criteria criteria = Criteria.where("createTime").gte(nowTime).lt(endTime);;
				query.addCriteria(criteria);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		query.with(new Sort(Direction.DESC,"createTime"));
		return query;
	}
}
