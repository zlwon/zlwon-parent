package com.zlwon.vo.pc.integration;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 积分异动流水详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class IntegrationDetailMapVo {

	private Integer id;  //自增ID

    private Integer type;  //积分异动类型

    private String description;  //描述

    private Integer integrationNum;  //积分异动数量

    private Integer changeType;  //积分异动方式  0:减少  1：增加

    private Integer uid;  //用户ID

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;  //创建时间
}
