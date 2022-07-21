package com.demothird.controller;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private String springTopic="first-topic";


    /**
     * 单向消息
     */
    public void onewaySend() {
        String json = "单向消息";
        rocketMQTemplate.sendOneWay("sendMessage", json);
    }


    /*
     * 普通消息  没有返回值   只负责发送消息
     * 不等待服务器 没有回调函数触发
     */
    @GetMapping("send")
    public void send(){
//        System.out.println("12344");
        for(int i=0;i<100;i++){
            Integer x=i;
            rocketMQTemplate.convertAndSend(springTopic,"你好,Java旅途"+x.toString());
        }

    }

    /**
     * 发送同步消息
     * 有返回值SendResult，等到消息发送成功后才算结束。
     */
    @GetMapping("sync")
    public void SimpleSyncProducer() throws Exception {
        for(int i=0;i<100;i++){
            Integer x=i;
            SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "Hello, World!"+x.toString());
            System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
        }


    }

    /**
     * 异步消息
     * 需要传入回调类，无需等待消息是否发送成功
     */
    @GetMapping("async")
    public void sendMessage3(){
        rocketMQTemplate.asyncSend(springTopic,
                "this is a template async message", new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("send success:"+sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println("send fail:"+throwable.getMessage());
                    }
                });
    }


    /**
     * 实现顺序消费
     */
    @GetMapping("send/order")
    public void sendMessageOrderly(){
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                //可以自定义规则，取list的第几个，这里取第一个
                return list.get(1);
            }
        });
        for(int i=0;i<100;i++){
            Integer x=i;
            rocketMQTemplate.syncSendOrderly("order-topic","this is order message"+x.toString(),"hashKey001");
        }

    }












}
