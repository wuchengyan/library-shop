package com.example.library.service.impl;


import com.example.library.dao.OrderMapper;
import com.example.library.domain.Order;
import com.example.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
  /**
   * 订单service实现类
   */

  @Autowired
  OrderMapper orderMapper;


  /**
   * 增加
   *
   * @param order
   */
  @Override
  public boolean insert(Order order) {
    return orderMapper.insert(order)>0;
  }

  /**
   * 删除
   *
   * @param id
   */
  @Override
  public boolean delete(Integer id) {
    return orderMapper.delete(id)>0;
  }

  /**
   * 修改
   *
   * @param order
   */
  @Override
  public boolean update(Order order) {
    return orderMapper.update(order)>0;
  }

  /**
   * 修改订单状态
   *
   * @param id
   * @param status
   */
  @Override
  public boolean updateStatus(Integer id, Integer status) {
    return orderMapper.updateStatus(id,status)>0;
  }

  /**
   * 查找
   */
  @Override
  public List<Order> findall() {
    return orderMapper.findall();
  }

  /**
   * 根据id查找
   *
   * @param id
   */
  @Override
  public Order findbyid(Integer id) {
    return orderMapper.findbyid(id);
  }
}
