package com.wang.rocketmq.util;


import com.wang.rocketmq.util.excel.ImportExcelUtil;

import java.io.FileInputStream;
import java.util.List;

public class TestDemo {

    //读取excel
    public static void main(String[] args) throws Exception {
        List<List<Object>> lists = ImportExcelUtil.importExcel("Book1.xlsx", new FileInputStream("E:\\wang\\腾讯\\文件\\微信\\WeChat Files\\wxid_ksp8irer6g8122\\FileStorage\\File\\2019-09\\Book1.xlsx"));
        System.out.println("lists.size() = " + lists.size());
        for (List<Object> list : lists) {
            System.out.println("list.size() = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println("list.get(" + i +") = " + list.get(i));
            }
            System.out.println("--------------------------------------------------------");
        }
    }


//    public static void main(String[] args) throws Exception{
//        String str = Base64.encode("{'domain': 'mobile.yangkeduo.com', 'name' : 'PDDAccessToken', 'value' : 'XQ5KFUIGNQYCJN5FNGXQKPOGOUFQ3RKQVFOBD6NYQKZTSUI6AKJQ100551e'}".getBytes("UTF-8"));
//        System.out.println("str = " + str);
//        System.out.println("Base64.decode(str) = " + new String(Base64.decode(str),"UTF-8"));
//    }
}
