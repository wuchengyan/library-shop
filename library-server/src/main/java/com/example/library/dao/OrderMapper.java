package com.example.library.dao;

import com.example.library.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
  /**
   * 订单dao
   */

  /**
   * 增加
   */
  public int insert(Order order);

  /**
   * 删除
   */
  public int delete(Integer id);

  /**
   * 修改
   */
  public int update(Order order);

  /**
   * 修改订单状态
   */
  public int updateStatus(Integer id,Integer status);

  /**
   * 查找所有订单
   */
  public List<Order> findall();

  /**
   * 根据id查找
   */
  public Order findbyid(Integer id);



}
