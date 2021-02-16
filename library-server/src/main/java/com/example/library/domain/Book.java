package com.example.library.domain;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
  /**
   * 图书
   */
  private Integer id; //主键
  private String bookname; //图书名
  private String author; //作者
  private String des; //描述
  private String price; //价格
  private String sort; //分类
  private Date createtime; //出版时间
  private Integer stock; //库存
  private String pic; //封面

  public String getAuthor() {
    return author;
  }

  public String getBookname() {
    return bookname;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public String getDes() {
    return des;
  }

  public Integer getId() {
    return id;
  }

  public String getPic() {
    return pic;
  }

  public String getPrice() {
    return price;
  }

  public String getSort() {
    return sort;
  }

  public Integer getStock() {
    return stock;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setBookname(String bookname) {
    this.bookname = bookname;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public void setDes(String des) {
    this.des = des;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
}
