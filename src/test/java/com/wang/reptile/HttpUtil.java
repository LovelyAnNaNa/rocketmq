package com.wang.reptile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Auther: wbh
 */
public class HttpUtil {

    private String referer = "http://m.changdusk.com";

    @Test
    public void testaa() {
        String str = "indexaaa";
        str.replaceAll("aaa", "");
        System.out.println("str = " + str);
    }

    public String getHtmlInfoFromUrl2(String url, String encoding) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网页内容
     */
    public String getHtmlInfoFromUrl(String url, String encoding) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        try {
            URL urlObj = new URL(url);
            URLConnection uc = urlObj.openConnection();
            uc.setRequestProperty("User-Agent", "PostmanRuntime/7.28.0");
//            uc.setRequestProperty("Connection","keep-alive");
            uc.setRequestProperty("Accept", "*/*");
            uc.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//            uc.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
//            uc.setRequestProperty("Referer",referer);
//            uc.setRequestProperty("Host","m.changdusk.com");
//            uc.setRequestProperty("Upgrade-Insecure-Requests","1");
//            uc.setRequestProperty("Cookie","DJab_pc=1; clickbids=64892%2525252525252525252525252C96696%25252525252525252525252C96696%252525252525252525252C62633%2525252525252525252C62633%25252525252525252C62633%252525252525252C96696%2525252525252C96696%25252525252C64882%252525252C62633%2525252C62633%25252C62633%252C62633%2C62633");
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            return null;
        } finally {
            referer = url;
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        return str;
    }


    public String getHtmlInfoFromUrl2(String url) {
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            response = client.newCall(request).execute();
            return response.body().string().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
