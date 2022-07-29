package com.punchsystem1.controller;



import com.punchsystem1.eneity.User;
import com.punchsystem1.pool.MyThreadPool;
import com.punchsystem1.thread.MyThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RestController
@SuppressWarnings("all")
public class ProducerController {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;

    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<>();


    private static AtomicInteger count=new AtomicInteger(0);


    private static ArrayList<String> queue1 = new ArrayList<>(100);

    /**
     * 发送普通消息
     */
    @GetMapping("/sendMessage")
    public void sendMsg() {

        User user = new User();
        for (int i = 0; i < 12; i++) {
            user.setId(String.valueOf(i));
            user.setUserName("passion " + i);
            Message msg = new Message("user-topic22", "white", user.toString().getBytes());
            try {
                //同步发送
                SendResult result = defaultProducer.send(msg);
                log.info("发送次数{}:消息id={}:发送状态={}", i, result.getMsgId(), result.getSendStatus());
                //异步发送
//                defaultProducer.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 当前可用版本
     * @throws MQClientException
     */
    @RequestMapping("/getMessage")
    public void test() throws MQClientException {

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("liudunrui22_group_test");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.start();

        while(true){
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("user-topic22");
            for (MessageQueue mq : mqs) {
                System.out.printf("Consume from the queue: %s%n", mq);
//            SINGLE_MQ:
                try {
                    PullResult pullResult =
                            // getMessageQueueOffset指的是消息消费的偏移量，用来记录上一次消费到的地址，下一次继续向后消费，这样可以防止重复消费
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 1);
                    log.info("获得的消息 %s%n", pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            log.info("具体的消息是 : "+pullResult.getMsgFoundList().toString());
                            count.getAndIncrement();
                            log.info("收到消息的数量 : "+count.toString());
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
//                            break SINGLE_MQ;
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


//        consumer.shutdown();

    }




    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }





    /**
     * 测试消费者拉取消息,本样例中只拉取了几条消息，并将拉取到的消息日志写出来。
     */
    @GetMapping("/test/getmessage")
    public void testConsumer() throws Exception {


        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("user_consumer_group2");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("user_consumer_instance");
        consumer.start();
        System.out.println("消费者创建完成");
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("user-topic14");
        while(true){


            for (MessageQueue queue : messageQueues){

//                //拿到下标
                long offset = consumer.fetchConsumeOffset(queue, true);
                log.info("consumer from the queue:" + queue + ":" + offset);

                PullResult pullResult = consumer.pull(queue, null, consumer.fetchConsumeOffset(queue, false), 1);
                consumer.updateConsumeOffset(queue, pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt m : messageExtList) {
                            String ans = new String(m.getBody());
                            System.out.println("拉取数据" + ans);
                            count.getAndIncrement();
                            log.info("收到了消息 :"+count.toString());
//                            queue1.add(ans);
//                            if (queue1.size() >= 4) {
//                                break;
//                            }
                        }
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        Thread.sleep(2000);
                        System.out.println("休息2s再次工作");
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }


        }
        }




    }
