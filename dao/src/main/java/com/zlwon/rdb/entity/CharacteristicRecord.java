package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性标签点赞记录表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CharacteristicRecord {
    
	private Integer id;  //自增ID

    private Integer characterId;  //标签ID

    private Integer uid;  //点赞用户ID

    private Date createTime;  //创建时间
}