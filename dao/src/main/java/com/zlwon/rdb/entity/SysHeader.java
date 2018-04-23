package com.zlwon.rdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统官网头部表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor 
public class SysHeader {
	
    private Integer id;  //自增ID

    private Integer moduleType;  //模块类别 1：首页 2：物性表模块 3：应用案例模块

    private String descTitle;  //描述标题

    private String descContent;  //描述内容

    private String carouselPic;  //轮播图
}