package com.zlwon.server.service.impl;

import com.zlwon.rdb.dao.UserMapper;
import com.zlwon.rdb.entity.User;
import com.zlwon.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
        return userMapper.findByUserName(name);
    }

    @Override
    @Cacheable("users")
    public List<User> findAllUsers() {
        return userMapper.listUsers();
    }
}
