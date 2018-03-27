package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户收藏表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Collection  implements Serializable{

	private Integer id;  //自增ID
	
	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private Integer uid;  //用户ID
	
	private Integer iid;  //信息ID
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //收藏日期
}
