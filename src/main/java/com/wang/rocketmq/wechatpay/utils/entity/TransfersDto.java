package com.wang.rocketmq.wechatpay.utils.entity;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

public class TransfersDto {
    private String mch_appid;
    private String mchid;
    private String mch_name;
    private String partner_trade_no;
    private String openid;
    private String check_name = "NO_CHECK";
    private int amount;
    private String nonce_str;
    private String desc;
    private String appkey;
    private String spbill_create_ip = "127.0.0.1";
    public String getMch_appid()
    {
        return mch_appid;
    }
    public void setMch_appid(String mch_appid)
    {
        this.mch_appid = mch_appid;
    }
    public String getMchid()
    {
        return mchid;
    }
    public void setMchid(String mchid)
    {
        this.mchid = mchid;
    }
    public String getPartner_trade_no()
    {
        return partner_trade_no;
    }
    public void setPartner_trade_no(String partner_trade_no)
    {
        this.partner_trade_no = partner_trade_no;
    }
    public String getOpenid()
    {
        return openid;
    }
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    public String getCheck_name()
    {
        return check_name;
    }
    public int getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = (int)(amount * 100);
    }
    public String getNonce_str()
    {
        return nonce_str;
    }
    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }
    public String getDesc()
    {
        return desc;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    public String getAppkey()
    {
        return appkey;
    }
    public void setAppkey(String appkey)
    {
        this.appkey = appkey;
    }
    public String getSpbill_create_ip()
    {
        return spbill_create_ip;
    }
    public void setSpbill_create_ip(String spbill_create_ip)
    {
        this.spbill_create_ip = spbill_create_ip;
    }
    public String getMch_name()
    {
        return mch_name;
    }
    public void setMch_name(String mch_name)
    {
        this.mch_name = mch_name;
    }
    @Transient
    public Map<String, String> map()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_appid", this.mch_appid);
        map.put("mchid", this.mchid);
        map.put("mch_name", this.mch_name);
        map.put("openid", this.openid);
        map.put("amount", String.valueOf(this.amount));
        map.put("desc", this.desc);
        map.put("appkey", this.appkey);
        map.put("nonce_str", this.nonce_str);
        map.put("partner_trade_no", this.partner_trade_no);
        map.put("spbill_create_ip", this.spbill_create_ip);
        return map;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[mch_appid]" + this.mch_appid);
        sb.append(",[mchid]" + this.mchid);
        sb.append(",[openid]" + this.openid);
        sb.append(",[amount]" + this.amount);
        sb.append(",[desc]" + this.desc);
        sb.append(",[partner_trade_no]" + this.partner_trade_no);
        sb.append(",[nonce_str]" + this.nonce_str);
        sb.append(",[spbill_create_ip]" + this.spbill_create_ip);
        sb.append(",[check_name]" + this.check_name);
        return sb.toString();
    }
}
