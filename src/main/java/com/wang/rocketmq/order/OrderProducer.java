package com.wang.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * 顺序消息生产者
 */
public class OrderProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_order_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 5; i++) {
            Message message = new Message("Topic_Order_Demo", "Tags", "keys_1", ("hello" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //参数 1,要发送的请求信息 2,选中指定的消息队列对象(会将所有消息对列传递进来)  3,指定对应队列的下标
            SendResult result = producer.send(message,
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            //获取队列的下标
                            Integer index = (Integer) arg;
                            //获取对应下标的队列
                            return mqs.get(index);
                        }
                    },
                    0);
            System.out.println(result);
        }
        producer.shutdown();
    }
}