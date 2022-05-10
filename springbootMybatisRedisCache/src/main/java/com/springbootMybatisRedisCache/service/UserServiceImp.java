package com.springbootMybatisRedisCache.service;

import com.springbootMybatisRedisCache.dao.UserDao;
import com.springbootMybatisRedisCache.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@SuppressWarnings("all")
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }
}