package com.zlwon.vo.pc.processAdvice;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端加工建议详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ProcessingAdviceDetailVo {

	private Integer id;  //自增ID

    private Integer uid;  //用户Id，如果为0，表示官方数据

    private Integer specId;  //物性ID
    
    private String specName;  //规格名称
    
    private Integer cid;  //主题，用户创建时必填
    
    private String title;  //主题名称

    private String value;  //值

    private String unit;  //单位

    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过
}
