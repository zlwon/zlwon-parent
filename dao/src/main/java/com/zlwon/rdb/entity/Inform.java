package com.zlwon.rdb.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 通知信息表
 * @author yuand
 *
 */
@Data
public class Inform {
    private Integer id;//通知id

    private Integer iid;//信息id 

    private Byte type;//1用户提问2用户回答3案例编辑4用户新增物性标签5材料报价单

    private Byte status;//状态 1通过 0驳回。type为1,2,3,4,5才有状态

    private String content;//信息说明(驳回)

    private Byte readStatus;//是否已读0未读1已读

    private Integer uid;//用户id

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//创建时间
}