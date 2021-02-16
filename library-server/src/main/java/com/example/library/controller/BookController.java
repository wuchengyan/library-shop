package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {
  /**
   * 图书控制类
   */

  @Autowired
  private BookService bookService;


  //增加图书
  @RequestMapping(value = "/add",method = RequestMethod.POST)
  public Object addBook(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String bookname = request.getParameter("bookname").trim();
    String author = request.getParameter("author").trim();
    String des = request.getParameter("des").trim();
    String price = request.getParameter("price").trim();
    String sort = request.getParameter("sort").trim();
    String createtime = request.getParameter("createtime").trim();
    String stock = request.getParameter("stock").trim();
    String pic = request.getParameter("pic").trim();

    //出版日期转换格式
    DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date publicationtimeDate = new Date();
    try {
      publicationtimeDate = dataFormat.parse(createtime);
    } catch (ParseException p){
      p.printStackTrace();
    }

    //保存
    Book book = new Book();
    book.setBookname(bookname);
    book.setAuthor(author);
    book.setDes(des);
    book.setPrice(price);
    book.setSort(sort);
    book.setCreatetime(publicationtimeDate);
    book.setStock(Integer.parseInt(stock));
    book.setPic(pic);

    boolean flag = bookService.insert(book);
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

  //根据id修改图书库存
  @RequestMapping(value = "/updateStock",method = RequestMethod.POST)
  public Object updateBookStockById(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String stock = request.getParameter("stock").trim();

    boolean flag = bookService.updateStock(Integer.parseInt(id),Integer.parseInt(stock));
    if(flag){
      jsonObject.put("code",1);
      jsonObject.put("msg","修改库存成功");
      return jsonObject;
    }
    else {
      jsonObject.put("code", 0);
      jsonObject.put("msg", "修改库存失败");
      return jsonObject;
    }

  }

  //修改图书
  @RequestMapping(value = "/update",method = RequestMethod.POST)
  public Object updateBook(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String bookname = request.getParameter("bookname").trim();
    String author = request.getParameter("author").trim();
    String des = request.getParameter("des").trim();
    String price = request.getParameter("price").trim();
    String sort = request.getParameter("sort").trim();
    String createtime = request.getParameter("createtime").trim();
    String stock = request.getParameter("stock").trim();

    //出版日期转换格式
    DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date publicationtimeDate = new Date();
    try {
      publicationtimeDate = dataFormat.parse(createtime);
    } catch (ParseException p){
      p.printStackTrace();
    }

    //保存
    Book book = new Book();
    book.setId(Integer.parseInt(id));
    book.setBookname(bookname);
    book.setAuthor(author);
    book.setDes(des);
    book.setPrice(price);
    book.setSort(sort);
    book.setCreatetime(publicationtimeDate);
    book.setStock(Integer.parseInt(stock));

    boolean flag = bookService.update(book);
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

  //删除图书
  @RequestMapping(value= "/delete",method = RequestMethod.GET)
  public Object deleteBook(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = bookService.delete(Integer.parseInt(id));
    return flag;
  }

  //查找所有图书
  @RequestMapping(value = "/findall",method = RequestMethod.GET)
  public Object findAllUser(HttpServletRequest request){
    return bookService.findall();
  }

  //根据id查找图书
  @RequestMapping(value = "findbyid",method = RequestMethod.GET)
  public Object findBookById(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    return bookService.findbyid(Integer.parseInt(id));
  }

  /**
   * 更新图书封面
   */
  @RequestMapping(value ="/updateBookPic",method = RequestMethod.POST)
  public Object updateBookPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
    JSONObject jsonObject = new JSONObject();
    if(avatorFile.isEmpty()){
      jsonObject.put("code",0);
      jsonObject.put("msg","封面上传失败");
      return jsonObject;
    }
    //文件名=当前时间+原来文件名
    String filename = System.currentTimeMillis()+avatorFile.getOriginalFilename();
    //文件路径
    String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
        +System.getProperty("file.separator")+"bookPic";
    //不存在就创建
    File file = new File(filePath);
    if(!file.exists()){
      file.mkdir();
    }
    //实际地址
    File file1 = new File(filePath+System.getProperty("file.separator")+filename);
    //存到数据库的相对地址
    String storePath = "/img/bookPic/"+filename;
    try {
      avatorFile.transferTo(file1);
      Book book = new Book();
      book.setId(id);
      book.setPic(storePath);
      boolean flag = bookService.update(book);
      if(flag){
        jsonObject.put("code",1);
        jsonObject.put("msg","封面更新成功");
        jsonObject.put("pic",storePath);
        return jsonObject;
      }
      jsonObject.put("code",0);
      jsonObject.put("msg","封面更新失败");
      return jsonObject;
    } catch (IOException e) {
      jsonObject.put("code",0);
      jsonObject.put("msg","封面更新失败"+e.getMessage());
    }finally {
      return jsonObject;
    }
  }

}
