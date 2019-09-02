package com.wang.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionListenerImpl implements TransactionListener {

    int a  = 0;
    //存储对应事务状态信息 key:事务ID,value:当前事务执行的状态
    private ConcurrentHashMap<String,Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        a = TransactionProducer.getNum();
        String transactionId = msg.getTransactionId();//事务ID
        localTrans.put(transactionId,0);
        try {
            System.out.println(a + "条消息正在执行本地事务 transactionId = " + transactionId);
            Thread.sleep(61000);//每隔60s消息回查
            System.out.println("本地事务执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            localTrans.put(transactionId,2);
            return LocalTransactionState.ROLLBACK_MESSAGE;//返回事务状态信息
        }
        localTrans.put(transactionId,1);
        //业务执行,处理本地事务,service
        return LocalTransactionState.COMMIT_MESSAGE;//返回事务状态信息
    }

    /**
     * 消息回查
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String transactionId = msg.getTransactionId();
        //获取对应事务id所执行状态
        Integer status = localTrans.get(transactionId);
        System.out.println(a + " 消息回查-------transactionId: " + transactionId + ",status: " + status);
        switch (status){
            case 0:
                return LocalTransactionState.UNKNOW;
            case 1:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }
}
