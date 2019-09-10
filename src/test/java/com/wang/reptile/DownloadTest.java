package com.wang.reptile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @Auther: wbh
 * @Date: 2019/9/10 17:01
 */
public class DownloadTest {

    String path = "E:\\wang\\other\\download\\";//文件保存路径
    private DownloadPicFromURL download = new DownloadPicFromURL();
    private HttpUtil httpUtil = new HttpUtil();

    @Test
    public void test2(){
        for (int i = 1; i < 10; i++) {
            String pageUrl = "https://wallhaven.cc/search?q=id%3A1&categories=111&purity=110&atleast=1920x1080&sorting=relevance&order=desc&page=" + i;
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
                    System.out.println("link = " + link);
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
                    download.downloadPicture(imgSrc,path + imgName);
                } catch (Exception ex) {
                    return;
                }
            });
        }

    }

    @Test
    public void test() {
        String html = httpUtil.getHtmlInfoFromUrl("https://wallhaven.cc/w/482gmy", "UTF-8");
        //获取页面信息
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        //获取图片标签
        Element imgId = document.getElementById("wallpaper");
        String src = imgId.attr("src");
        String name = download.getUrlImgName(src);
        download.downloadPicture(src,path + name);


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
