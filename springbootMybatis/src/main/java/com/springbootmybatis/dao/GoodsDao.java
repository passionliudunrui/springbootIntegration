package com.springbootmybatis.dao;

import com.springbootmybatis.entity.Goods;

public interface GoodsDao {
    int insert(Goods goods);
    int update(Goods goods);


}
