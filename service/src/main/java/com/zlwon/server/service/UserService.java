package com.zlwon.server.service;

import com.zlwon.rdb.entity.User;

import java.util.List;

public interface UserService {
    User findUserByName(String name);

    List<User> findAllUsers();
}
