package com.example.library.service;

import com.example.library.domain.Order;

import java.util.List;

public interface OrderService {
  /**
   * 订单service接口
   */

  /**
   * 增加
   */
  public boolean insert(Order order);

  /**
   * 删除
   */
  public boolean delete(Integer id);

  /**
   * 修改
   */
  public boolean update(Order order);

  /**
   * 修改订单状态
   */
  public boolean updateStatus(Integer id,Integer status);

  /**
   * 查找
   */
  public List<Order> findall();

  /**
   * 根据id查找
   */
  public Order findbyid(Integer id);

}
