package com.springboothippo4j;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableDynamicThreadPool
@SpringBootApplication
public class SpringbootHippo4JApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHippo4JApplication.class, args);
    }

}
