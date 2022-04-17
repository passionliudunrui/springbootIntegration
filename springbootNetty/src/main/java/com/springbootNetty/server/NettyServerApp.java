package com.springbootNetty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettyServerApp implements CommandLineRunner {

    @Autowired
    private MyServer myServer;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NettyServerApp.class, args);
//        MyServer myServer=context.getBean(MyServer.class);
//        System.out.println("1111111111");
//        myServer.run();

    }

    @Override
    public void run(String... args) throws Exception {
        myServer.start();
        Runtime.getRuntime().addShutdownHook(
                new Thread(()->myServer.destory())
        );

    }


}
