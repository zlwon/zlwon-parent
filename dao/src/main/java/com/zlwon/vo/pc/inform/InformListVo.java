package com.zlwon.vo.pc.inform;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * pc用户消息列表
 * @author yuand
 *
 */
@Data
public class InformListVo {

	private Integer  id;//消息id
	
	private Byte type;//1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	
	private Byte status;//状态1通过0驳回。type为1,2,3,4,5,6才有状态
	
	private String content;//信息说明(驳回)

    private Byte readStatus;//是否已读0未读1已读
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//创建时间
    
    private String qTitle;//type为1才有，问题标题
	
    private String qInfoTiTle;//type为1才有，问题信息(案例或者物性)标题
    
    private String aqTitle;//type为2才有，回答问题的标题
    
    private String aqInfoTitle;//type为2才有，回答问题的标题的信息(案例或者物性)标题
    
    private String ceAppTitle;//type为3才有，编辑案例的标题
    
    private String chSpeName;//type为4才有，标签物性的规格名称
    
    private String dqSpeName;//type为5才有，报价单物性的规格名称
    
    private Byte caType;//type为6才有，用户认证的类型1:个人认证6企业认证
    
    private Byte infoType;//type为1,2才有,标识该问题(回答的问题)是针对案例还是物性(1物性2案例)
    
    private Integer infoId;//type为1,2,3,4案例或物性id，1,2需要根据infoType判断,3,4不需要
}
