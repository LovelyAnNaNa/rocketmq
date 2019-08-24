package com.wang.rocketmq.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConsumerService implements ApplicationRunner {

    //生产者的组名
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGrup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    public void printMsg(String msg){
        System.out.println("Consumer接收到消息: " + msg);
    }

    public List<String> getMsg(String topic,String tags){
        final List<String> list = new ArrayList<>();
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGrup);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeMessageBatchMaxSize(2);//设置消息拉取的最大数
            consumer.subscribe(topic,tags);//设置要消费的主题和过滤规则
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
                            printMsg(result);
//                            System.out.println("consumer接收result = " + result);
//                            list.add("topic = " + topic + ",tags: " + tags + ",result: " + result);
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("consumerServer启动");
        return list;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getMsg("demo_controller_topic","*");
    }
}
