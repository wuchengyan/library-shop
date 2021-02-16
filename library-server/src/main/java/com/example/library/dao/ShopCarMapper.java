package com.example.library.dao;

import com.example.library.domain.ShopCar;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCarMapper {
  /**
   * 购物车dao
   */

  /**
   * 增加
   */
  public int insert(ShopCar shopCar);

  /**
   * 删除
   */
  public int delete(Integer id);

  /**
   * 根据账号删除购物车
   */
  public int deletebyaccount(String account);

  /**
   * 修改
   */
  public int update(ShopCar shopCar);


  /**
   * 根据账号查找
   */
  public List<ShopCar> findbyaccount(String account);


}
