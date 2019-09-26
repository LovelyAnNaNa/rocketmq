package com.wang.reptile;

import com.google.common.collect.ImmutableList;
import com.wang.rocketmq.note.test;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.*;

/**
 * @Auther: wbh
 * @Date: 2019/9/10 17:01
 */
@Slf4j
public class DownloadTest {

    @Test
    public void testGetEqualNum(){
    }
    
    String path = "E:\\wang\\other\\download\\";//文件保存路径
    private DownloadPicFromURL download = new DownloadPicFromURL();
    private HttpUtil httpUtil = new HttpUtil();
    static int pageNo = 1;

    public static synchronized int getPageNo(){
        return  pageNo++;
    }

    //多线程抓取图片
    @Test
    public void downloadWallhaven() {
        for (int i = 0; i < 10; i++) {
            new Thread(new DownloadWallhavenThread()).start();
        }
        while (true){ }
    }

    @Test
    public void test() {
        String html = httpUtil.getHtmlInfoFromUrl("https://wallhaven.cc/search?q=id%3A1&categories=111&purity=110&atleast=1920x1080&sorting=relevance&order=desc&page=2", "UTF-8");
        //获取页面信息
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        //获取图片标签
        Elements smallImgs = document.getElementsByTag("li");
        smallImgs.forEach(e -> {
            System.out.println("e = " + e);
        });

//        Elements elementList = document.getElementsByTag("li");
//        List<Map<String, String>> maps = new ArrayList<>();
//        elementList.forEach(e -> {
//            String img = e.getElementsByClass("yy-img").attr("src");
//            String title = e.getElementsByClass("yy-name").attr("title");
//            if(img != "" && title != ""){
//                maps.add(ImmutableMap.<String,String>builder()
//                        .put("img",img).put("title",title).build());
//            }
//        });
//        maps.forEach(e -> {
//            dowanload.downloadPicture(e.get("img"),path + File.separator + e.get("title") + ".jpg");
//        });
    }
}
