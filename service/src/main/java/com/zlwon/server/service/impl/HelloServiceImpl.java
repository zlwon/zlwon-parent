package com.zlwon.server.service.impl;

import com.zlwon.dto.customer.CustomerForm;
import com.zlwon.nosql.dao.CustomerRepository;
import com.zlwon.nosql.entity.Customer;
import com.zlwon.server.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public String hello(String world) {
        System.err.println("Hello " + world);
        return "Hello " + world;
    }

    @Override
    public Customer findByFirstName(String name) {
        return customerRepository.findByFirstName(name);
    }

    @Override
    @Cacheable("customers")
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(CustomerForm customerForm) {
        Customer customer = new Customer();
        customer.setFirstName(customerForm.getFirstName());
        customer.setLastName(customerForm.getLastName());
        return customerRepository.save(customer);
    }

}
