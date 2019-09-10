package com.wang.reptile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @Auther: wbh
 */
public class HttpUtil {

    /**
     * 获取网页内容
     */
    public static String getHtmlInfoFromUrl(String url, String encoding) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        try{
            URL urlObj = new  URL(url);
            URLConnection uc = urlObj.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        }catch (Exception e) {
            return null;
        }finally {
            try {
                if(isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        return str;
    }


}
