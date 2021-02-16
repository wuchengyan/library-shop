package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.Order;
import com.example.library.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
  /**
   * 订单控制类
   */

  @Autowired
  private OrderService orderService;


  //增加订单
  @RequestMapping(value = "/add",method = RequestMethod.POST)
  public Object addOrder(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String ordernumber = request.getParameter("ordernumber").trim();
    String account = request.getParameter("account").trim();
    String price = request.getParameter("price").trim();
    String address = request.getParameter("address").trim();
    String status = request.getParameter("status").trim();
    String createtime = request.getParameter("createtime").trim();

    //出版日期转换格式
    DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date publicationtimeDate = new Date();
    try {
      publicationtimeDate = dataFormat.parse(createtime);
    } catch (ParseException p){
      p.printStackTrace();
    }

    //保存
    Order order = new Order();
    order.setOrdernumber(ordernumber);
    order.setAccount(account);
    order.setPrice(price);
    order.setAddress(address);
    order.setStatus(Integer.parseInt(status));
    order.setCreatetime(publicationtimeDate);


    boolean flag = orderService.insert(order);
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

  //修改订单
  @RequestMapping(value = "/update",method = RequestMethod.POST)
  public Object updateOrder(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String ordernumber = request.getParameter("ordernumber").trim();
    String account = request.getParameter("account").trim();
    String price = request.getParameter("price").trim();
    String address = request.getParameter("address").trim();
    String status = request.getParameter("status").trim();
    String createtime = request.getParameter("createtime").trim();

    //出版日期转换格式
    DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date publicationtimeDate = new Date();
    try {
      publicationtimeDate = dataFormat.parse(createtime);
    } catch (ParseException p){
      p.printStackTrace();
    }

    //保存
    Order order = new Order();
    order.setId(Integer.parseInt(id));
    order.setOrdernumber(ordernumber);
    order.setAccount(account);
    order.setPrice(price);
    order.setAddress(address);
    order.setStatus(Integer.parseInt(status));
    order.setCreatetime(publicationtimeDate);

    boolean flag = orderService.update(order);
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

  //修改订单状态
  @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
  public Object updateOrderStatus(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String status = request.getParameter("status").trim();

    boolean flag = orderService.updateStatus(Integer.parseInt(id),Integer.parseInt(status));
    if(flag){
      jsonObject.put("code",1);
      jsonObject.put("msg","发货成功");
      return jsonObject;
    }
    else {
      jsonObject.put("code", 0);
      jsonObject.put("msg", "发货失败");
      return jsonObject;
    }
  }

  //删除订单
  @RequestMapping(value= "/delete",method = RequestMethod.GET)
  public Object deleteOrder(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = orderService.delete(Integer.parseInt(id));
    return flag;
  }

  //查找所有订单
  @RequestMapping(value = "/findall",method = RequestMethod.GET)
  public Object findAllOrder(HttpServletRequest request){
    return orderService.findall();
  }

  //根据id查找订单
  @RequestMapping(value = "findbyid",method = RequestMethod.GET)
  public Object findOrderById(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    return orderService.findbyid(Integer.parseInt(id));
  }



}
