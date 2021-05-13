package com.wang.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GetData {
    private String serverUrl = "http://testelectron.eicp.net:10600/ST004472/DataManageRequest";

    @Test
    public void test() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://testelectron.eicp.net:10600/ST004472/DataManageRequest?action=GetDataManageMonitorTreeData&monitorID=0")
                .method("GET", null)
                .addHeader("Cookie", "JSESSIONID=5DD69645948A706D0583AA92FDF12467; prjID=1; prjName=%E9%9D%92%E7%A5%9E%E5%8E%BF%E5%B2%B7%E6%B1%9F%E5%A4%A7%E6%A1%A5%E6%A1%A5%E6%A2%81%E9%95%BF%E6%9C%9F%E6%80%A7%E8%83%BD%E7%9B%91%E6%B5%8B")
                .build();
        Response response = client.newCall(request).execute();
        JSONArray parentJson = JSON.parseArray(response.body().string());
        Map<String,String> headers = new HashMap<>();
        headers.put("Cookie", "JSESSIONID=5DD69645948A706D0583AA92FDF12467; prjID=1; prjName=%E9%9D%92%E7%A5%9E%E5%8E%BF%E5%B2%B7%E6%B1%9F%E5%A4%A7%E6%A1%A5%E6%A1%A5%E6%A2%81%E9%95%BF%E6%9C%9F%E6%80%A7%E8%83%BD%E7%9B%91%E6%B5%8B");
        for (Object o : parentJson) {
            Map<String,String> params = new HashMap<>();
            JSONObject jsonParent = (JSONObject) o;
            params.put("action","GetDataManageMonitorTreeData");
            params.put("monitorID","1");
            params.put("monitorName",jsonParent.getJSONObject("attributes").getString("monitorName"));
            params.put("id",jsonParent.getString("id"));
            Response res = sendRequest(serverUrl, params, headers);
            jsonParent.put("child",JSON.parseArray(res.body().string()));
        }
        System.out.println(parentJson.toString());
    }


    public Response sendRequest(String url, Map<String,String> params,Map<String,String> headers) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        StringBuilder sb = new StringBuilder(url).append("?");
        Set<String> keys = params.keySet();
        Iterator<String> iter = keys.iterator();
        int index = 0;
        while (iter.hasNext()){
            if(index != 0){
                sb.append("&");
            }
            String key = iter.next();
            sb.append(key).append("=").append(params.get(key));
            index++;
        }

        Request.Builder builder = new Request.Builder()
                .url(sb.toString())
                .method("GET", null);

        headers.forEach((k,v) -> builder.addHeader(k,v));
        Request request = builder.build();
        return client.newCall(request).execute();

    }
}
