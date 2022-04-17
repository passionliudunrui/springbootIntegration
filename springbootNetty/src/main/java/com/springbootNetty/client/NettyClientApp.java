package com.springbootNetty.client;

import com.springbootNetty.server.NettyServerApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication


public class NettyClientApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NettyClientApp.class, args);
        MyClient myClient=context.getBean(MyClient.class);
        myClient.run();
    }
}
