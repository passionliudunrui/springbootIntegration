package com.springbootmybatis.dao;

import com.springbootmybatis.entity.Account;

public interface AccountDao {
    int insert(Integer id);
    int update(Account account);

}
