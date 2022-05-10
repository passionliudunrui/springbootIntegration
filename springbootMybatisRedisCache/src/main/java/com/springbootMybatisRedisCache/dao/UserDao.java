package com.springbootMybatisRedisCache.dao;

import com.springbootMybatisRedisCache.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(String id);
    void delete(String id);
}
