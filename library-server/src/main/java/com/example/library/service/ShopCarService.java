package com.example.library.service;

import com.example.library.domain.ShopCar;

import java.util.List;

public interface ShopCarService {
  /**
   * 购物车service接口
   */

  /**
   * 增加
   */
  public boolean insert(ShopCar shopCar);

  /**
   * 删除
   */
  public boolean delete(Integer id);

  /**
   * 根据账号删除购物车
   */
  public boolean deletebyaccount(String account);

  /**
   * 修改
   */
  public boolean update(ShopCar shopCar);

  /**
   * 根据账号查找
   */
  public List<ShopCar> findbyaccount(String account);

}
