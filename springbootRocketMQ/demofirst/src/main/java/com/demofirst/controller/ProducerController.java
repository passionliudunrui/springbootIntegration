package com.demofirst.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.demofirst.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@Slf4j
@RestController
public class ProducerController {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;


    /**
     * 发送普通消息
     */
    @GetMapping("/sendMessage")
    public void sendMsg() {
        log.info("进入发送普通消息方法，发送5个用户==={}",defaultProducer.toString());
        User user = new User();
        for(int i=0;i<5;i++){
            user.setId(String.valueOf(i));
            user.setUserName("yangshilei"+i);
            String json = JSON.toJSONString(user);
            Message msg = new Message("user-topic","white",json.getBytes());
            try {
                SendResult result = defaultProducer.send(msg);
                log.info("发送次数{}:消息id={}:发送状态={}",i,result.getMsgId(),result.getSendStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 发送普通消息
     */
    @GetMapping("/sendMessage1")
    public void sendMsg1() {
        log.info("进入发送普通消息方法，发送5个用户==={}",defaultProducer.toString());
        User user = new User();
        for(int i=0;i<5;i++){
//            user.setId(String.valueOf(i));
//            user.setUserName("yangshilei"+i);
//            String json = JSON.toJSONString(user);
            Integer x=i;
            Message msg = new Message("user-topic","white",("Hello world"+x.toString()).getBytes());
            try {
                SendResult result = defaultProducer.send(msg);
                log.info("发送次数{}:消息id={}:发送状态={}",i,result.getMsgId(),result.getSendStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 测试消费者拉取消息,本样例中只拉取了几条消息，并将拉取到的消息日志写出来。
     */
    @GetMapping("/test/getmessage")
    public void testConsumer()  throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("user_consumer_group");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("user_consumer_instance");
        consumer.start();
        System.out.println("消费者创建完成");

        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("user-topic");
        System.out.println("拉取到的队列数量"+messageQueues.size());

        for(MessageQueue queue : messageQueues){
            System.out.println("遍历队列queue"+queue);

            long offset  = consumer.fetchConsumeOffset(queue, true);
            System.out.println("consumer from the queue:" + queue + ":" + offset);

            for(int i = 0; i < 10;i++){
                // 在队列中拉取不到消息会一直阻塞等待着，直到能拉取到消息
//                PullResult pullResult = consumer.pullBlockIfNotFound(queue, null, consumer.fetchConsumeOffset(queue, false), 1);

                // 在队列中拉取不到消息就结束
                PullResult pullResult = consumer.pull(queue, null, consumer.fetchConsumeOffset(queue, false), 1);

                consumer.updateConsumeOffset(queue,pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()){
                    case FOUND:
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt m : messageExtList) {
//                            System.out.println("拉取到数据===="+m.toString());
//                            System.out.println("数据"+m.getBody().toString());
                            System.out.println("数据"+String.valueOf(m.getBody()));
                        }
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println("关闭消费者");
        consumer.shutdown();
    }

}
