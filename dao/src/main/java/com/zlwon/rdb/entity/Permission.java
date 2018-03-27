package com.zlwon.rdb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Permission implements Serializable{

	private Integer id;
	
    /**
     * 菜单编码
     */
    private String permCode;

    /**
     * URL
     */
    private String url;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    private List<Role> roles;
}
