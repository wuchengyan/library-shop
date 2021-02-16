package com.example.library.service;

import com.example.library.domain.Book;

import java.util.List;

public interface BookService {
  /**
   * 图书service接口
   */

  /**
   * 增加
   */
  public boolean insert(Book book);

  /**
   * 删除
   */
  public boolean delete(Integer id);

  /**
   * 修改
   */
  public boolean update(Book book);

  /**
   * 根据id修改库存
   */
  public boolean updateStock(Integer id, Integer stock);

  /**
   * 查找
   */
  public List<Book> findall();

  /**
   * 根据id查找
   */
  public Book findbyid(Integer id);

}
