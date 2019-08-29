package com.wang.rocketmq.consumer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量消息生产者
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_broadcast_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("Topic_test","Tags","keys" + i ,("hello" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            messages.add(message);
        }
        SendResult result = producer.send(messages);//批量发送消息
        System.out.println("result = " + result);
        producer.shutdown();
    }
}