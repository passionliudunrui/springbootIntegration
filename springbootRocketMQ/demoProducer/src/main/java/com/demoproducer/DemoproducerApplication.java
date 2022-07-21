package com.demoproducer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class DemoproducerApplication {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        SpringApplication.run(DemoproducerApplication.class, args);

        DefaultMQProducer mqProducer = new DefaultMQProducer("producer-group");
        // 设置NameServer地址，如果是集群的话，使用分号;分隔开
        mqProducer.setNamesrvAddr("192.168.112.128:9876");
        // 消息最大长度 默认4M
        mqProducer.setMaxMessageSize(4096);
//        // 发送消息超时时间，默认3000
//        mqProducer.setSendMsgTimeout(3000);
        // 发送消息失败重试次数，默认2
        mqProducer.setRetryTimesWhenSendAsyncFailed(2);
        // 启动消息生产者
        mqProducer.start();
        System.out.println(mqProducer);

        // 循环十次，发送十条消息
        for (int i = 1; i <= 1; i++) {
            String msg = "hello, 这是第" + i + "条同步消息";
            // 创建消息，并指定Topic(主题)，Tag(标签)和消息内容
            Message message = new Message("SimpleTopic", "", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送同步消息到一个Broker，可以通过sendResult返回消息是否成功送达
            SendResult sendResult = mqProducer.send(message);
            System.out.println(sendResult);
        }

        // 如果不再发送消息，关闭Producer实例
        mqProducer.shutdown();
    }



}
