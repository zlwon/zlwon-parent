package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 回答点赞记录（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AnswerRecord {
    
	private Integer id;  //自增ID

    private Integer uid;  //用户ID

    private Integer answerId;  //回答ID

    private Date createTime;  //创建时间
}