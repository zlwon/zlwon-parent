package com.zlwon.dto.customer;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerForm implements Serializable {

	private String firstName;
    private String lastName;
}
