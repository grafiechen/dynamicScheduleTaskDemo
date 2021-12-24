package com.example.dynamicscheduledemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.dynamicscheduledemo.dao")
@SpringBootApplication
public class DynamicScheduleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicScheduleDemoApplication.class, args);
    }

}
