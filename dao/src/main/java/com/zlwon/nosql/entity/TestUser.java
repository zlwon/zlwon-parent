package com.zlwon.nosql.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 测试用例
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "testUser")
public class TestUser implements Serializable {

	@Id
	private String id;
	
	private String name;
	
	private String introduce;
	
	private String nickName;
	
	private String createTime;
}
