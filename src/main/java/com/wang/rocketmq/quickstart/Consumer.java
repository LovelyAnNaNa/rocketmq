package com.wang.rocketmq.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {
        System.out.println("consumer start!");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_consumer_group");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeMessageBatchMaxSize(2);//设置消息拉取的最大数
        consumer.subscribe("Topic_Demo","Tags");//设置要消费的主题和过滤规则

        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //迭代消息信息
                for (MessageExt msg : msgs) {
                    //获取主体
                    String topic = msg.getTopic();
                    //获取标签
                    String tags = msg.getTags();
                    //获取信息
                    try {
                        String result = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        System.out.println("topic = " + topic + ",tags: " + tags + ",result: " + result);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        //消息重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();//开启消费监控

        System.out.println("consumer end!");

    }

}
