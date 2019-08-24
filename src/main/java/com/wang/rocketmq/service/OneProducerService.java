package com.wang.rocketmq.service;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

@Service
public class OneProducerService {

    public String sendOneWayMsg(String msg) {

        DefaultMQProducer producer = new DefaultMQProducer("async_producer");
        System.out.println("开始发送单向消息");
        try {
            producer.start();
            Message message = new Message("demo_controller_topic", "tags", "keys_one", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));;
            producer.sendOneway(message);
            //避免线程提前关闭,异步消息发送失败
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
        System.out.println("单向消息发送成功");
        return null;
    }
}
