package com.springbootmybatis.service.impl;

import com.springbootmybatis.dao.OrderDao;
import com.springbootmybatis.entity.Order;
import com.springbootmybatis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public int insert(Order order) {
        return orderDao.insert(order);
    }
}
