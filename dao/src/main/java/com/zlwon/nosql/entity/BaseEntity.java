package com.zlwon.nosql.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
public class BaseEntity implements Serializable {

    @Id
    private String id;


    protected BaseEntity() {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
    }
}
