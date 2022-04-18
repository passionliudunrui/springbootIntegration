package com.springbootmybatis.service;

import com.springbootmybatis.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
