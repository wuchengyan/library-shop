package com.example.library.service.impl;

import com.example.library.dao.UserMapper;
import com.example.library.domain.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  /**
   * 用户service实现类
   */

  @Autowired
  UserMapper userMapper;

  /**
   * 增加
   *
   * @param user
   */
  @Override
  public boolean insert(User user) {
    return userMapper.insert(user)>0;
  }

  /**
   * 删除
   *
   * @param id
   */
  @Override
  public boolean delete(Integer id) {
    return userMapper.delete(id)>0;
  }

  /**
   * 修改
   *
   * @param user
   */
  @Override
  public boolean update(User user) {
    return userMapper.update(user)>0;
  }

  /**
   * 查找
   */
  @Override
  public List<User> findall() {
    return userMapper.findall();
  }

  /**
   * 根据id查找
   *
   * @param id
   */
  @Override
  public User findbyid(Integer id) {
    return userMapper.findbyid(id);
  }

  /**
   * 根据账号查找
   *
   * @param account
   */
  @Override
  public User findbyaccount(String account) {
    return userMapper.findbyaccount(account);
  }


  /**
   * 验证登录
   *
   * @param account
   * @param password
   * @param type
   */
  @Override
  public boolean verify(String account, String password, Integer type) {
    return userMapper.verify(account,password,type)>0;
  }
}
