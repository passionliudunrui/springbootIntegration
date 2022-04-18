package com.springbootmybatis.dao;

import com.springbootmybatis.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
}
