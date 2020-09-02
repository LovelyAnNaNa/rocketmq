package com.wang.rocketmq.wechatpay;

import com.wang.rocketmq.wechatpay.utils.WechatpayUtil;
import com.wang.rocketmq.wechatpay.utils.entity.ResultEntity;
import com.wang.rocketmq.wechatpay.utils.entity.TransfersDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * QQ:2172931891 有任何关于微信开发的问题可以相互交流
 */
public class TestApp
{
    private static final Log LOG = LogFactory.getLog(TestApp.class);
    
    public static void main(String[] args)
    {
        String appkey = "e9ca23d68d884d4ebb19d07889727dae";
        String certPath = "D:\\workspace\\idea\\github\\rocketmq\\src\\main\\resources\\apiclient_cert.p12";
        TransfersDto model = new TransfersDto();
        model.setMch_appid("wx8fc2275c6ba17934");
        model.setMchid("1601648649");
        model.setMch_name("小黄狗商户号");
        model.setOpenid("oFGCY5HxK2q_UFfXihdDNY5BVQ3Y");
        model.setAmount(0.01);
        model.setDesc("测试企业付款到零钱");
        ResultEntity iResult = WechatpayUtil.doTransfers(appkey, certPath, model);
        LOG.info(iResult);
    }
}
