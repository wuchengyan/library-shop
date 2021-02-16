package com.example.library.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
  /**
   * 订单
   */
  private Integer id; //主键
  private String ordernumber; //订单号
  private String account; //下单账号
  private String price; //价格
  private String address; //发货地址
  private Integer status; //发货状态
  private Date createtime; //下单时间

  public String getAccount() {
    return account;
  }

  public String getAddress() {
    return address;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public Integer getId() {
    return id;
  }

  public String getOrdernumber() {
    return ordernumber;
  }

  public String getPrice() {
    return price;
  }

  public Integer getStatus() {
    return status;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setOrdernumber(String ordernumber) {
    this.ordernumber = ordernumber;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
