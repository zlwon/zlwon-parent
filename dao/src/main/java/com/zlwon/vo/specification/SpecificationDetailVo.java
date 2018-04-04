package com.zlwon.vo.specification;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性详情出参
 * @author yangy
 * 该类yuand也使用了，如修改，请先通知
 */

@Setter
@Getter
@NoArgsConstructor
public class SpecificationDetailVo implements Serializable {

	private Integer id;
	
	private String name;  //规格名称
	
	private String manufacturer;  //生产商
	
	private String brandName;  //商标
	
	private String baseMaterial;  //基材
	
	private String filler;  //填充物
	
	private String fillerRatio;  //填充比例
	
	private String flameRate;  //阻燃等级
	
	private String safetyCert;  //安规认证
	
	private String label;  //规格名称模糊搜索用标签
	
	private String content;  //描述
	
	private String pdf;  //PDF文件
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer isCollect;  //是否收藏 1：是，0：否
	
	private Integer collectId;  //收藏ID
	
	private List<CharacteristicDetailVo> characterTap;  //标签列表
	
	private Integer caseCount;  //相关联案例数量
	
	private Integer questionCount;  //问答数量
}
