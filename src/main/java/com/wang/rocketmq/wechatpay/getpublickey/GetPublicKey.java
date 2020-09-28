package com.wang.rocketmq.wechatpay.getpublickey;

import com.wang.rocketmq.wechatpay.utils.MD5Util;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author wbh
 * @date 2020/9/28 10:28
 * @description
 */
public class GetPublicKey {


    /**
     * 获取微信商户支付公钥,
     * @throws Exception
     */
    @Test
    public void getPublicKey() throws Exception {
        //1.0 拼凑所需要传递的参数 map集合
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        String nonce_str = UUID.randomUUID().toString().replaceAll("-","").substring(0,28);
        //微信商户号
        parameters.put("mch_id", "1601648649");
        parameters.put("nonce_str", nonce_str);
        //加密方式,默认MD5
        parameters.put("sign_type", "MD5");
        //2.0 根据要传递的参数生成自己的签名~~注意creatSign是自己封装的一个类。大家可以在下面自主下载
        String sign = creatSign("UTF-8", parameters);
//        System.out.println(sign);
        //3.0 把签名放到map集合中【因为签名也要传递过去】
        parameters.put("sign", sign);
        //4.0将当前的map结合转化成xml格式

        String reuqestXml = WXPayUtil.getRequestXml(parameters);
        //5.0 发送请求到微信请求公钥Api。发送请求是一个方法来的~~注意需要带着证书哦
        String xml1 = HttpClientCustomSSL.httpClientResultGetPublicKey(reuqestXml);
        System.out.println(xml1);
        //6.0 解析返回的xml数据===》map集合
//        String publicKey = XMLUtils.Progress_resultParseXml(xml1);
//        System.out.println(publicKey);
    }

    public static String creatSign(String characterEncoding,
                                   SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        //微信支付秘钥
        sb.append("key=e9ca23d68d884d4ebb19d07889727dae");
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        System.out.println(sign);
        return sign;
    }
}
