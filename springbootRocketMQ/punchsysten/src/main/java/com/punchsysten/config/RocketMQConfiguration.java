package com.punchsysten.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RocketMQConfiguration {
    /**
     * 创建支持消息事务发送的实例
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQProducer transactionProducer()throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer("user_group");
        producer.setInstanceName("user_producer_instance");
        producer.setNamesrvAddr("localhost:9876");
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("支持事务消息的实例创建完成....");
        return producer;
    }
}
