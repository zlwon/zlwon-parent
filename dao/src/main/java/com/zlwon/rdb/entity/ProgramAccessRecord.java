package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序用户访问浏览行为记录表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ProgramAccessRecord implements Serializable {

	private Integer id;  //自增ID
	
	private Integer uid;  //访问用户ID
	
	private String route;  //页面路由
	
	private String remark;  //备注
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //访问时间
}
