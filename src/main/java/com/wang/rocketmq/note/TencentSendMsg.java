package com.wang.rocketmq.note;

public class TencentSendMsg {
    // 短信应用 SDK AppID
    int appid = 1400253897; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    String appkey = "c7fe23d8202f5541c612b94428931fce";
    // 需要发送短信的手机号码
    String[] phoneNumbers = {"18637736725", "12345678902", "12345678903"};
    // 短信模板 ID，需要在短信应用中申请
    int templateId = 7839; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    String smsSign = "腾讯云"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

}
