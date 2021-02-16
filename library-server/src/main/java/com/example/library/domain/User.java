package com.example.library.domain;

import java.io.Serializable;

public class User implements Serializable {
  /**
   * 用户
   */
  private Integer id; //用户id
  private String account; //用户账号
  private String password; //用户密码
  private String name; //用户姓名
  private String phone; //用户电话
  private String mail; //用户邮箱
  private String address; //用户地址
  private Integer type; //用户类型
  private String pic; //用户头像

  public String getAccount() {
    return account;
  }

  public String getAddress() {
    return address;
  }


  public Integer getId() {
    return id;
  }

  public String getMail() {
    return mail;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getPic() {
    return pic;
  }

  public Integer getType() {
    return type;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
