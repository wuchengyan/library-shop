package com.example.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位图片地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //用户头像定位
    registry.addResourceHandler("/img/userPic/**")
        .addResourceLocations(
            "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "userPic" + System.getProperty("file.separator")
        );
    //图书封面定位
    registry.addResourceHandler("/img/bookPic/**")
        .addResourceLocations(
            "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                System.getProperty("file.separator") + "bookPic" + System.getProperty("file.separator")
        );
  }
}
