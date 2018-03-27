package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 展会案例表
 * @author yuand
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class ExhibitionCase  implements Serializable{

	private Integer id;
	
	private  Integer  cid;//案例ID
	
	private  Integer  uid;//工程师注册ID
	
	private  Integer  del;//标记删除状态，1正常，-1已删除
}
