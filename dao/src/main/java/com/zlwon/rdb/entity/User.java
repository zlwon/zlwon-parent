package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User  implements Serializable{

	private Integer id;
	
    private String username;

    private String password;

}
