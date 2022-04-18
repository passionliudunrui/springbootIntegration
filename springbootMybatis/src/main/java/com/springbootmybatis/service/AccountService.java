package com.springbootmybatis.service;

import com.springbootmybatis.entity.Account;

public interface AccountService {
    int insert(Integer id);
    int update(Account account);
}
