package com.wang.rocketmq.transaction;

import com.wang.rocketmq.util.DateUtil;
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

    public static int a  = 0;
    public synchronized static int getNum(){
        return ++a;
    }

    public static void main(String[] args) throws Exception {
//        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_order_group");
        TransactionMQProducer producer = new TransactionMQProducer("test_producer_transaction");

        producer.setNamesrvAddr("127.0.0.1:9876");

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
        producer.start();
//        for (int i = 0; i < 5; i++) {
            Message message = new Message("Topic_test", "tags", "keys_", (1 + "").getBytes(RemotingHelper.DEFAULT_CHARSET));
        System.out.println("new String(message.getBody(),\"UTF-8\") = " + new String(message.getBody(), "UTF-8"));
        TransactionSendResult result = producer.sendMessageInTransaction(message, 1+"");
            Thread.sleep(100);
            System.out.println("消息发送成功: " + result);
//        }

        System.out.println("生产者执行完毕");
        producer.shutdown();
    }

}