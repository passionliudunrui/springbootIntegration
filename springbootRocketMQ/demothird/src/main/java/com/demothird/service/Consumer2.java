package com.demothird.service;

import org.apache.rocketmq.client.impl.consumer.PullMessageService;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "order-topic-consumer", topic = "order-topic",consumeMode = ConsumeMode.ORDERLY,consumeThreadMax = 1)
public class Consumer2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("consumer group order message:"+s);
    }


}
