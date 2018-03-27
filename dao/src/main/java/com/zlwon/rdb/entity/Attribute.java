package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 物性属性
 * @author yuand
 *
 */
@Data
public class Attribute implements  Serializable{
    private Integer id;

    private String classname; //分类：物理特性Physical、机械强度Mechanical、冲击强度Impact、热学性能Thermal、阻燃特性Flammability、电气特性Electrical、光学参数Optical

    private String name; //属性名称

    private String status; //干湿态
 
    private String unit; //单位

    private String testConditions; //测试条件

    private String testMethod; //测试方法

}