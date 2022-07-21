package com.demothird.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group",topic = "first-topic")
@Slf4j
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("我收到消息了！消息内容为："+message);
    }

}
