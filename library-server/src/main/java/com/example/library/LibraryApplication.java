package com.example.library;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.library.dao")//使用MapperScan批量扫描所有的Mapper接口；
public class LibraryApplication {
  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }
}

