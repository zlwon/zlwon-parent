package com.zlwon.vo.processingAdvice;

import java.io.Serializable;

import lombok.Data;

/**
 * 加工建议出参,后端通过审核加工建议时使用
 * @author yuand
 *
 */
@Data
public class ProcessingAdviceVo implements Serializable{

	private Integer id; //ProcessingAdvice.id加工建议id

    private Integer uid; //ProcessingAdvice.uid用户ID,如果为0表示官方数据
    
    private  String  nickname;//用户昵称，官方数据就是null
    
    private Integer  specId;//ProcessingAdvice.specId物性id
    
    private String   name;//物性规格名称

    private String value; //ProcessingAdvice.value值

    private String unit; //ProcessingAdvice.unit单位

    private Integer examine; //ProcessingAdvice.examine用户创建数据审核结果，0未审核，1审核通过
	
    private String title; //ProcessingAdviceClass.title加工建议主题
    
    private Integer cid; //加工建议主题id
}
