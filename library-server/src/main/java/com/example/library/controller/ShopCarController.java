package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.ShopCar;
import com.example.library.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/shopcar")
public class ShopCarController {
  /**
   * 购物车控制类
   */

  @Autowired
  private ShopCarService shopCarService;


  //增加购物车
  @RequestMapping(value = "/add",method = RequestMethod.POST)
  public Object addShopCar(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id");
    String account = request.getParameter("account");
    String bookname = request.getParameter("bookname");
    String bookpic = request.getParameter("bookpic");
    String bookcount = request.getParameter("bookcount");
    String bookprice = request.getParameter("bookprice");

    //保存
    ShopCar shopCar = new ShopCar();
    shopCar.setId(Integer.parseInt(id));
    shopCar.setAccount(account);
    shopCar.setBookname(bookname);
    shopCar.setBookpic(bookpic);
    shopCar.setBookcount(Integer.parseInt(bookcount));
    shopCar.setBookprice(bookprice);

    boolean flag = shopCarService.insert(shopCar);
    if(flag){
      jsonObject.put("code",1);
      jsonObject.put("msg","添加成功");
      return jsonObject;
    }
    else {
      jsonObject.put("code", 0);
      jsonObject.put("msg", "添加失败");
      return jsonObject;
    }
  }

  //修改购物车
  @RequestMapping(value = "/update",method = RequestMethod.POST)
  public Object updateBook(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String account = request.getParameter("account");
    String bookname = request.getParameter("bookname");
    String bookpic = request.getParameter("bookpic");
    String bookcount = request.getParameter("bookcount");
    String bookprice = request.getParameter("bookprice");

    //保存
    ShopCar shopCar = new ShopCar();
    shopCar.setId(Integer.parseInt(id));
    shopCar.setAccount(account);
    shopCar.setBookname(bookname);
    shopCar.setBookpic(bookpic);
    shopCar.setBookcount(Integer.parseInt(bookcount));
    shopCar.setBookprice(bookprice);


    boolean flag = shopCarService.update(shopCar);
    if(flag){
      jsonObject.put("code",1);
      jsonObject.put("msg","修改成功");
      return jsonObject;
    }
    else {
      jsonObject.put("code", 0);
      jsonObject.put("msg", "修改失败");
      return jsonObject;
    }
  }

  //删除购物车
  @RequestMapping(value= "/delete",method = RequestMethod.GET)
  public Object deleteBook(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = shopCarService.delete(Integer.parseInt(id));
    return flag;
  }

  //根据账号删除购物车
  @RequestMapping(value= "/deletebyaccount",method = RequestMethod.GET)
  public Object deleteBookByAccount(HttpServletRequest request){
    String account = request.getParameter("account").trim();
    boolean flag = shopCarService.deletebyaccount(account);
    return flag;
  }

  //根据账号查找购物车
  @RequestMapping(value = "/findbyaccount",method = RequestMethod.GET)
  public Object findbyaccount(HttpServletRequest request){
    String account = request.getParameter("account").trim();
    return shopCarService.findbyaccount(account);
  }

}
