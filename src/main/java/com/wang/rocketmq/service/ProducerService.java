package com.wang.rocketmq.service;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    public String sendMsg(String topic,String tags,String keys,String msg){
        try {
            DefaultMQProducer producer = new DefaultMQProducer("demo_producer_controller_group");
            producer.setNamesrvAddr("localhost:9876");
            producer.start();
            Message message = new Message(topic, tags, keys, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(message);
            System.out.println(result);
            producer.shutdown();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }
}
