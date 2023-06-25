package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling//注解开启定时任务功能
@EnableSwagger2//开启接口文档
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }
}
