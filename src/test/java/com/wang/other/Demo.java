package com.wang.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;

public class Demo {

    @Test
    public void test() throws Exception {
        String cookie = " api_uid=CiGFNF2LICVs/wA3CMSzAg==; _nano_fp=XpdjlpXbl0Uon5PYno_8DeZydZCDpE11NEASIJP6; pdd_user_id=3585012418736; pdd_user_uin=S6X5DUKSVSY2AIHC5OKEWDMJ6Q_GEXDA; PDDAccessToken=KHAZAWT2T2IKDUFSN4NNKAALIILO7V4KG54NX65ZLZ4CXLC6LU7A110b076; msec=1800000; chat_config={\"host_whitelist\":[\".yangkeduo.com\",\".pinduoduo.com\",\".10010.com/queen/tencent/pinduoduo-fill.html\",\".ha.10086.cn/pay/card-sale!toforward.action\",\"wap.ha.10086.cn\",\"m.10010.com\"]}; chat_list_rec_list=chat_list_rec_list_4khii4; rec_list_goods_likes=rec_list_goods_likes_9wzAxx; rec_list_footprint=rec_list_footprint_4s6okq; rec_list_orders=rec_list_orders_CGTVzZ; rec_list_index=rec_list_index_C8x4sQ; rec_list_personal=rec_list_personal_114uca; JSESSIONID=1747910C6F979CFAC87A9312FD03B705; ua=Mozilla%2F5.0%20(Linux%3B%20Android%206.0%3B%20Nexus%205%20Build%2FMRA58N)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F77.0.3865.90%20Mobile%20Safari%2F537.36; webp=1";
        System.out.println(phone2("KHAZAWT2T2IKDUFSN4NNKAALIILO7V4KG54NX65ZLZ4CXLC6LU7A110b076"));
    }

    public Integer phone2(String token) throws Exception {
        Integer result = 0;

        try {
            RequestBody body = RequestBody.create(null, new byte[0]);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://mobile.yangkeduo.com/proxy/api/api/galen/v2/regions/1?pdduid=3125033187456")
                    .get()
                    .addHeader("AccessToken", token)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            if (jsonObj != null) {
                if (jsonObj.getString("error_code") == null) {
                    result = 1;
                }
            }
        } finally {
            return result;
        }
    }

    public Integer phone(String cookie) throws Exception {
        Integer result = 0;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://mobile.yangkeduo.com/personal.html")
                    .get()
                    .addHeader("Cookie", cookie)
                    .build();
            //发送请求获取返回json字符串
            Response response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            //获取rawData字符串开始的下标
            int rawDataIndex = jsonStr.indexOf("window.rawData");
            //截取rawData字符串
            jsonStr = jsonStr.substring(rawDataIndex);
            //截取JSON开始
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            //截取JSON结束
            jsonStr = jsonStr.substring(0, jsonStr.indexOf("</script>"));
            jsonStr = jsonStr.substring(0, jsonStr.lastIndexOf(";"));
            System.out.println(jsonStr);
            //判断返回数据是否可用
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            jsonObj.getJSONObject("store").get("personalService");
            if (jsonObj != null) result = 1;
        } finally {
            return result;
        }
    }
}
