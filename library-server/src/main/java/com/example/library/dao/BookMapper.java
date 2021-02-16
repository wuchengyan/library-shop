package com.example.library.dao;

import com.example.library.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
  /**
   * 图书dao
   */

  /**
   * 增加
   */
  public int insert(Book book);

  /**
   * 删除
   */
  public int delete(Integer id);

  /**
   * 修改
   */
  public int update(Book book);

  /**
   * 根据id修改库存
   */
  public int updateStock(Integer id,Integer stock);

  /**
   * 查找所有图书
   */
  public List<Book> findall();

  /**
   * 根据id查找
   */
  public Book findbyid(Integer id);


}
