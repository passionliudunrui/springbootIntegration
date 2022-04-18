package com.springbootmybatis.service.impl;

import com.springbootmybatis.dao.AccountDao;
import com.springbootmybatis.entity.Account;
import com.springbootmybatis.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@SuppressWarnings("all")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;

    @Override
    public int insert(Integer id) {
        int insert = accountDao.insert(id);
        return insert;
    }

    @Override
    public int update(Account account) {
        int update = accountDao.update(account);
        return update;
    }
}
