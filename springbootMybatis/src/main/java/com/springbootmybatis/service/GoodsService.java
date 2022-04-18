package com.springbootmybatis.service;

import com.springbootmybatis.entity.Goods;

public interface GoodsService {
    int insert(Goods goods);
    int update(Goods goods);

}
