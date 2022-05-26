package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.demo")
@MapperScan("com.example.demo.mapper")
public class SpringbootCookieAndSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCookieAndSessionApplication.class, args);
    }

}
