package com.zlwon.server.service;

import com.zlwon.dto.customer.CustomerForm;
import com.zlwon.nosql.entity.Customer;

import java.util.List;

public interface HelloService {

    String hello(String world);

    Customer findByFirstName(String name);

    Customer save(CustomerForm customerForm);

    List<Customer> findAllCustomers();

}
