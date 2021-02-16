package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.User;
import com.example.library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
  /**
   * 用户控制类
   */

  @Autowired
  private UserService userService;


  //增加用户
  @RequestMapping(value = "/add",method = RequestMethod.POST)
  public Object addUser(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String account = request.getParameter("account").trim();
    String password = request.getParameter("password").trim();
    String name = request.getParameter("name").trim();
    String phone = request.getParameter("phone").trim();
    String mail = request.getParameter("mail").trim();
    String address = request.getParameter("address").trim();
    String type = request.getParameter("type").trim();
    String pic = request.getParameter("pic").trim();

    //保存
    User user = new User();
    user.setAccount(account);
    user.setPassword(password);
    user.setName(name);
    user.setPhone(phone);
    user.setMail(mail);
    user.setAddress(address);
    user.setType(Integer.parseInt(type));
    user.setPic(pic);

    boolean flag = userService.insert(user);
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


  //修改用户
  @RequestMapping(value = "/update",method = RequestMethod.POST)
  public Object updateUser(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String account = request.getParameter("account").trim();
    String password = request.getParameter("password").trim();
    String name = request.getParameter("name").trim();
    String phone = request.getParameter("phone").trim();
    String mail = request.getParameter("mail").trim();
    String address = request.getParameter("address").trim();
    String type = request.getParameter("type").trim();

    //保存
    User user = new User();
    user.setId(Integer.parseInt(id));
    user.setAccount(account);
    user.setPassword(password);
    user.setName(name);
    user.setPhone(phone);
    user.setMail(mail);
    user.setAddress(address);
    user.setType(Integer.parseInt(type));

    boolean flag = userService.update(user);
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

  //删除用户
  @RequestMapping(value= "/delete",method = RequestMethod.GET)
  public Object deleteUser(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = userService.delete(Integer.parseInt(id));
    return flag;
  }

  //查找所有用户
  @RequestMapping(value = "/findall",method = RequestMethod.GET)
  public Object findAllUser(HttpServletRequest request){
    return userService.findall();
  }

  //根据id查找用户
//  @RequestMapping(value = "findbyid",method = RequestMethod.GET)
//  public Object findUserById(HttpServletRequest request){
//    String id = request.getParameter("id").trim();
//    return userService.findbyid(Integer.parseInt(id));
//  }

  //根据账号查找用户
  @RequestMapping(value = "findbyaccount",method = RequestMethod.GET)
  public Object findUserByAccount(HttpServletRequest request){
    String account = request.getParameter("account").trim();
    return userService.findbyaccount(account);
  }

  //验证登录
  @RequestMapping(value = "/verify",method = RequestMethod.POST)
  public Object loginStatus(HttpServletRequest request, HttpSession session){
    JSONObject jsonObject = new JSONObject();
    String account = request.getParameter("account");
    String password = request.getParameter("password");
    String type = request.getParameter("type");

    boolean flag = userService.verify(account,password,Integer.parseInt(type));
    if(flag){
      jsonObject.put("code",1);
      jsonObject.put("msg","登录成功");
      return jsonObject;
    }
    jsonObject.put("code",0);
    jsonObject.put("msg","登录失败");
    return jsonObject;
  }


  /**
   * 更新客户头像
   */
  @RequestMapping(value ="/updateUserPic",method = RequestMethod.POST)
  public Object updateClientPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
    JSONObject jsonObject = new JSONObject();
    if(avatorFile.isEmpty()){
      jsonObject.put("code",0);
      jsonObject.put("msg","头像上传失败");
      return jsonObject;
    }
    //文件名=当前时间+原来文件名
    String filename = System.currentTimeMillis()+avatorFile.getOriginalFilename();
    //文件路径
    String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
        +System.getProperty("file.separator")+"userPic";
    //不存在就创建
    File file = new File(filePath);
    if(!file.exists()){
      file.mkdir();
    }
    //实际地址
    File file1 = new File(filePath+System.getProperty("file.separator")+filename);
    //存到数据库的相对地址
    String storePath = "/img/userPic/"+filename;
    try {
      avatorFile.transferTo(file1);
      User user = new User();
      user.setId(id);
      user.setPic(storePath);
      boolean flag =userService.update(user);
      if(flag){
        jsonObject.put("code",1);
        jsonObject.put("msg","头像更新成功");
        jsonObject.put("pic",storePath);
        return jsonObject;
      }
      jsonObject.put("code",0);
      jsonObject.put("msg","头像更新失败");
      return jsonObject;
    } catch (IOException e) {
      jsonObject.put("code",0);
      jsonObject.put("msg","头像更新失败"+e.getMessage());
    }finally {
      return jsonObject;
    }
  }

}
