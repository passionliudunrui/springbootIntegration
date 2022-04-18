package com.springbootmybatis.dao;

import com.springbootmybatis.entity.Seckillgoods;

public interface SeckillgoodsDao {
    int insert(Seckillgoods seckillgoods);
    int update(Integer id);
    int delete(Integer id);
}
