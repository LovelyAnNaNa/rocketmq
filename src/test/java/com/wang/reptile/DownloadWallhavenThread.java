package com.wang.reptile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @Auther: wbh
 * @Date:
 * @Description:
 */
public class DownloadWallhavenThread implements Runnable {

    String path = "D:\\download\\other\\wallhaven\\";//文件保存路径
    int totalNum = 100;

    @Override
    public void run() {
        DownloadPicFromURL download = new DownloadPicFromURL();
        HttpUtil httpUtil = new HttpUtil();
        int curPage = DownloadTest.getPageNo();
        ArrayList<Integer> pages = new ArrayList<>();
        while(curPage < totalNum){
            if(curPage > 100){
                break;
            }
            pages.add(curPage);
            try {
                //String pageUrl = "https://wallhaven.cc/search?q=id%3A1&categories=111&purity=110&atleast=1920x1080&sorting=relevance&order=desc&page=" + curPage;
                String pageUrl = "https://wallhaven.cc/search?q=id:5&sorting=random&ref=fp&page=" + curPage;
                //获取分页信息
                String html = httpUtil.getHtmlInfoFromUrl(pageUrl, "UTF-8");
                Document document = Jsoup.parse(html);
                //缩略图列表
                Elements smallImgLi = document.getElementsByClass("thumb-anime");
                smallImgLi.forEach(e -> {
                    try {
                        //获取缩略图信息
                        Element img = e.getElementsByClass("preview").get(0);
                        //全图页面连接
                        String link = img.attr("href");
                        //获取全图页面信息
                        String imgHtml = httpUtil.getHtmlInfoFromUrl(link, "UTF-8");
                        //格式化全图页面
                        Document imgDocument = Jsoup.parse(imgHtml);
                        //获取全图img元素
                        Element imgEle = imgDocument.getElementById("wallpaper");
                        //全图位置链接
                        String imgSrc = imgEle.attr("src");
                        //拆分连接,获取文件名
                        String imgName = download.getUrlImgName(imgSrc);
                        download.downloadPicture(imgSrc, path + imgName);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            curPage = DownloadTest.getPageNo();
        }
        System.out.println("Thread Run end,pages: " + pages);
    }
}
