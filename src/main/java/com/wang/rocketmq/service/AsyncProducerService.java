package com.wang.rocketmq.service;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AsyncProducerService {

    public String asyncSendMsg(String msg) {
        DefaultMQProducer producer = new DefaultMQProducer("async_producer");
        System.out.println("开始异步发送消息");
        try {
            producer.start();
//            producer.setRetryTimesWhenSendFailed(2);
            Message message = new Message("demo_controller_topic", "tags", "keys_async", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));;
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("异步发送消息成功result: " + sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("异步发送消息失败e: " +e.getMessage());
                }
            });

            //避免线程提前关闭,异步消息发送失败
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
        return null;
    }
}
