package com.springbootNacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos")
@NacosPropertySource(groupId = "NACOS-TEST-GROUP",dataId = "NACOS-TEST-DATA-ID",autoRefreshed = true)
public class NacosDemoController {

    @NacosValue(value = "${nacos.info:默认值}",autoRefreshed = true)
    private String info;

    @GetMapping("/project-info")
    public String projectInfo(){
        return info;
    }

}
