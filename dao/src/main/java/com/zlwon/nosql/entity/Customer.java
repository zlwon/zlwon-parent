package com.zlwon.nosql.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="customer")
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
}