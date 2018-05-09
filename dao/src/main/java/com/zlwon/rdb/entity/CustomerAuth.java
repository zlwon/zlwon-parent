package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Data;

/**
 * 用户认证信息
 * @author yuand
 *
 */
@Data
public class CustomerAuth {
    private Integer id;

    private Integer uid;

    private Integer shortcompanyId;

    private Integer fullcompanyId;

    private String nickname;

    private String email;

    private String occupation;

    private String label;

    private String bcard;

    private String myinfo;

    private Byte status;

    private Date createTime;

    private Date auditTime;

    
}