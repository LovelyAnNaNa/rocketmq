package com.wang.rocketmq.transaction;

import com.wang.rocketmq.util.DateUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 顺序消息消费者
 */
public class TransactionConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer_transaction");
        consumer.setNamesrvAddr("localhost:9876");

        consumer.subscribe("Topic_test", "*");//设置要消费的主题和过滤规则
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs){
                    //获取主体
                    String topic = msg.getTopic();
                    //获取标签
                    String tags = msg.getTags();
                    //获取信息
                    try {
                        String result = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        System.out.println("Consumer接收到消息---topic = " + topic + ",tags: " + tags + ",result: " + result);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        //消息重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
        System.out.println("OrderConsumer Starting!");
    }
}