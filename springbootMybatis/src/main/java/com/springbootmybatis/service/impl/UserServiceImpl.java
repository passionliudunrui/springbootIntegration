package com.springbootmybatis.service.impl;
import com.springbootmybatis.dao.UserDao;
import com.springbootmybatis.entity.User;
import com.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Transactional
@Service
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }


    @Override
    public User check(Long id, String password) {
        return userDao.check(id,password);
    }

    @Override
    public int insert(User user) {
        User user1=userDao.findById(user.getId());
        if(user1!=null){
            return 0;
        }
        return userDao.insert(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }
}
