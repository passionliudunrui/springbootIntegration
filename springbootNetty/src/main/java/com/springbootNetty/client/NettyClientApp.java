package com.springbootNetty.client;

import com.springbootNetty.server.NettyServerApp;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class NettyClientApp implements CommandLineRunner {

    @Autowired
    private MyClient myClient;
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NettyClientApp.class, args);
//        MyClient myClient=context.getBean(MyClient.class);
//        myClient.run();
    }

    @Override
    public void run(String... args) throws Exception {
        myClient.run();

        Runtime.getRuntime().addShutdownHook(
                new Thread(()->myClient.destory())
        );
    }
}
