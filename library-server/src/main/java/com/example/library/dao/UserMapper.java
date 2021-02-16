package com.example.library.dao;

import com.example.library.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
  /**
   * 用户dao
   */

  /**
   * 增加
   */
  public int insert(User user);

  /**
   * 删除
   */
  public int delete(Integer id);

  /**
   * 修改
   */
  public int update(User user);

  /**
   * 查找所有用户
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
  public int verify(String account,String password,Integer type);

}
