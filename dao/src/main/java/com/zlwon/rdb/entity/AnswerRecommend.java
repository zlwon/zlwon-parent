package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 回答指定推荐用户表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AnswerRecommend {
    
	private Integer id;  //自增ID

    private Integer type;  //类型：1物性  2案例

    private Integer infoId;  //信息ID

    private Integer uid;  //指定用户ID
}