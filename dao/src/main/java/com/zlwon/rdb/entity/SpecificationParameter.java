package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpecificationParameter implements  Serializable{
    private Integer id;

    private String name;

    private Integer classType;//类别：商标1、基材2、填充物3、阻燃等级4、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9

}