package com.punchsysten.controller;


import com.punchsysten.eneity.User;
import com.punchsysten.pool.MyThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.store.OffsetStore;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.punchsysten.thread.MyThread;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
@RestController
@SuppressWarnings("all")
public class ProducerController {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;


    private static ArrayList<String> queue1 = new ArrayList<>(100);

    /**
     * 发送普通消息
     */
    @GetMapping("/sendMessage")
    public void sendMsg() {

        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setId(String.valueOf(i));
            user.setUserName("passion " + i);
//            String json = JSON.toJSONString(user);
            String message = "ttttttttttttttttt" + i;



            Message msg = new Message("user-topic44", "white", user.toString().getBytes());
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
     * 测试消费者拉取消息,本样例中只拉取了几条消息，并将拉取到的消息日志写出来。
     */
    @GetMapping("/test/getmessage")
    public void testConsumer() throws Exception {


        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("user_consumer_group2");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("user_consumer_instance");
        consumer.start();
        System.out.println("消费者创建完成");

        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("user-topic10");


        while(true){

            for (MessageQueue queue : messageQueues){
                if (queue1.size() >= 4) {

                    ArrayList<String> arrayList = new ArrayList<>(queue1);
                    MyThread myThread=new MyThread(arrayList);
                    MyThreadPool.executorService.submit(myThread);
                    queue1.clear();

                }


//                //拿到下标
//                long offset = consumer.fetchConsumeOffset(queue, true);
//                System.out.println("consumer from the queue:" + queue + ":" + offset);

                PullResult pullResult = consumer.pull(queue, null, consumer.fetchConsumeOffset(queue, false), 1);
                long maxOffset = pullResult.getMaxOffset();
                log.info(maxOffset+"位置");


                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
//                        System.out.println("----------------------------------");
//                        System.out.println(messageExtList.size());
//                        if(messageExtList.size()==0){
//                            Thread.sleep(1000);
//                            System.out.println("当前没有数据 休息一下");
//                        }
                        for (MessageExt m : messageExtList) {
                            String ans = new String(m.getBody());
                            System.out.println("拉取数据" + ans);
                            queue1.add(ans);
                            if (queue1.size() >= 4) {
                                break;
                            }
                        }

                        consumer.updateConsumeOffset(queue, pullResult.getNextBeginOffset());

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





            /**
             * 把for换成了while
             */


//            for(int i = 0; i < 1;i++){
//                // 在队列中拉取不到消息会一直阻塞等待着，直到能拉取到消息
////                PullResult pullResult = consumer.pullBlockIfNotFound(queue, null, consumer.fetchConsumeOffset(queue, false), 1);
//
//                // 在队列中拉取不到消息就结束
//                PullResult pullResult = consumer.pull(queue, null, consumer.fetchConsumeOffset(queue, false), 1000);
//
//                consumer.updateConsumeOffset(queue,pullResult.getNextBeginOffset());
//                switch (pullResult.getPullStatus()){
//                    case FOUND:
//                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
//                        for (MessageExt m : messageExtList) {
////                            System.out.println("拉取到数据===="+JSONObject.toJSONString(m));
////                            System.out.println("拉取到数据"+JSON.toJSONString(m));
//                            String ans=new String(m.getBody());
//                            System.out.println("拉取数据"+ans);
//                        }
//                        break;
//                    case NO_MATCHED_MSG:
//                        break;
//                    case NO_NEW_MSG:
//                        break;
//                    case OFFSET_ILLEGAL:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//
//        System.out.println("关闭消费者");
//        consumer.shutdown();
        }

    }
