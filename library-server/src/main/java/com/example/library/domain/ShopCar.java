package com.example.library.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopCar implements Serializable {
  /**
   * 购物车
   */
  private Integer id; //主键
  private String account; //账号
  private String bookname; //书名
  private String bookpic; //封面
  private Integer bookcount; //数量
  private String bookprice; //单价

  public String getAccount() {
    return account;
  }

  public Integer getBookcount() {
    return bookcount;
  }

  public String getBookname() {
    return bookname;
  }

  public String getBookpic() {
    return bookpic;
  }

  public String getBookprice() {
    return bookprice;
  }

  public Integer getId() {
    return id;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setBookcount(Integer bookcount) {
    this.bookcount = bookcount;
  }

  public void setBookname(String bookname) {
    this.bookname = bookname;
  }

  public void setBookpic(String bookpic) {
    this.bookpic = bookpic;
  }

  public void setBookprice(String bookprice) {
    this.bookprice = bookprice;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
