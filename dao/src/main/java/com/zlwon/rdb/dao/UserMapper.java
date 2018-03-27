package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.User;

public interface UserMapper {

    List<User> listUsers();

    User findByUserName(String username);
}
