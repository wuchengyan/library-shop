package com.example.library.service;

import com.example.library.domain.User;

import java.util.List;

public interface UserService {
  /**
   * 用户service接口
   */

  /**
   * 增加
   */
  public boolean insert(User user);

  /**
   * 删除
   */
  public boolean delete(Integer id);

  /**
   * 修改
   */
  public boolean update(User user);

  /**
   * 查找
   */
  public List<User> findall();

  /**
   * 根据id查找
   */
  public User findbyid(Integer id);

  /**
   * 根据账号查找
   */
  public User findbyaccount(String account);

  /**
   * 验证登录
   */
  public boolean verify(String account,String password,Integer type);
}
