package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Specification  implements Serializable{
	
	private Integer id;

	private String nsid;  //NoSqlId
	
	private String name;  //规格名称
	
	private Integer mid;  //生产商ID
	
	private Integer tid;  //商标ID
	
	private Integer msid;  //基材ID
	
	private Integer fid;  //填充物ID
	
	private Integer flid;  //阻燃等级ID
	
	private Integer scid;  //安规认证
	
	private String label;  //规格名称模糊搜索用标签
	
	private String content;  //描述
	
	private String pdf;  //PDF文件
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer del;  //标记删除状态，1正常，-1已删除
}
