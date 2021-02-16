package com.example.library.service.impl;


import com.example.library.dao.ShopCarMapper;
import com.example.library.domain.ShopCar;
import com.example.library.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCarServiceImpl implements ShopCarService {
  /**
   * 购物车service实现类
   */

  @Autowired
  ShopCarMapper shopCarMapper;


  /**
   * 增加
   *
   * @param shopCar
   */
  @Override
  public boolean insert(ShopCar shopCar) {
    return shopCarMapper.insert(shopCar)>0;
  }

  /**
   * 删除
   *
   * @param id
   */
  @Override
  public boolean delete(Integer id) {
    return shopCarMapper.delete(id)>0;
  }

  /**
   * 根据账号删除购物车
   *
   * @param account
   */
  @Override
  public boolean deletebyaccount(String account) {
    return shopCarMapper.deletebyaccount(account)>0;
  }

  /**
   * 修改
   *
   * @param shopCar
   */
  @Override
  public boolean update(ShopCar shopCar) {
    return shopCarMapper.update(shopCar)>0;
  }

  /**
   * 根据账号查找
   *
   * @param account
   */
  @Override
  public List<ShopCar> findbyaccount(String account) {
    return shopCarMapper.findbyaccount(account);
  }
}
