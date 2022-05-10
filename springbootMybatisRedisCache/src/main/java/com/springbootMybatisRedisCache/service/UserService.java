package com.springbootMybatisRedisCache.service;

import com.springbootMybatisRedisCache.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String id);
    void delete(String id);
}