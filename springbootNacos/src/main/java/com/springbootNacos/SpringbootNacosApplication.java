package com.springbootNacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@NacosPropertySource(dataId = "example", autoRefreshed = true)

public class SpringbootNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNacosApplication.class, args);
    }


}
