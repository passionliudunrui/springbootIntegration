package com.springbootmybatis.service.impl;

import com.springbootmybatis.dao.SeckillgoodsDao;
import com.springbootmybatis.entity.Seckillgoods;
import com.springbootmybatis.service.SeckillgoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@SuppressWarnings("all")
public class SeckillgoodsServiceImpl implements SeckillgoodsService {
    @Autowired
    private SeckillgoodsDao seckillgoodsDao;

    @Override
    public int insert(Seckillgoods seckillgoods) {
        return seckillgoodsDao.insert(seckillgoods);
    }

    @Override
    public int update(Integer id) {
        return seckillgoodsDao.update(id);
    }

    @Override
    public int delete(Integer id) {
        return seckillgoodsDao.delete(id);
    }
}
