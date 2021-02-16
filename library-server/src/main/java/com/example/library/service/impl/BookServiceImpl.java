package com.example.library.service.impl;


import com.example.library.dao.BookMapper;
import com.example.library.domain.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
  /**
   * 图书service实现类
   */

  @Autowired
  BookMapper bookMapper;


  /**
   * 增加
   *
   * @param book
   */
  @Override
  public boolean insert(Book book) {
    return bookMapper.insert(book)>0;
  }

  /**
   * 删除
   *
   * @param id
   */
  @Override
  public boolean delete(Integer id) {
    return bookMapper.delete(id)>0;
  }

  /**
   * 修改
   *
   * @param book
   */
  @Override
  public boolean update(Book book) {
    return bookMapper.update(book)>0;
  }

  /**
   * 根据id修改库存
   *
   * @param id
   * @param stock
   */
  @Override
  public boolean updateStock(Integer id, Integer stock) {
    return bookMapper.updateStock(id,stock)>0;
  }

  /**
   * 查找
   */
  @Override
  public List<Book> findall() {
    return bookMapper.findall();
  }

  /**
   * 根据id查找
   *
   * @param id
   */
  @Override
  public Book findbyid(Integer id) {
    return bookMapper.findbyid(id);
  }
}
