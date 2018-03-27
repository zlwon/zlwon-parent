package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 加工建议
 * @author yuand
 *
 */
@Data
public class ProcessingAdvice implements Serializable{
    private Integer id;

    private Integer uid;

    private Integer cid;

    private String mould;

    private String conditions;

    private Integer examine;
}