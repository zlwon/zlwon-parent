package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Role  implements Serializable{

	private Integer id;
	
    /**
     * 角色名
     */
    private String name;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否删除， 0：否， 1：是
     */
    private Integer delFlag = 0;

    //角色 -- 权限关系：多对多关系;
    private List<Permission> permissions;

    private List<User> users;
}
