package com.wang.rocketmq.transaction;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消息生产者
 */
public class TransactionProducer {

    public static void main(String[] args) throws Exception {
//        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_order_group");
        TransactionMQProducer producer = new TransactionMQProducer("test_producer_transaction");

        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        //指定消息监听对象,用于执行本地事务和消息回查
        TransactionListenerImpl transactionListener = new TransactionListenerImpl();

        //线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        }
        );
        producer.setExecutorService(pool);
        producer.setTransactionListener(transactionListener);
        //参数 1,要发送的请求信息 2,选中指定的消息队列对象(会将所有消息对列传递进来)  3,指定对应队列的下标
        for (int i = 0; i < 5; i++) {
            Message message = new Message("Topic_test", "tags", "keys_t", ("test_dome").getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionSendResult result = producer.sendMessageInTransaction(message, "hello_transaction");
            Thread.sleep(100);
            System.out.println("消息发送成功: " + result);
        }

        Thread.sleep(30000);

        producer.shutdown();
    }

}