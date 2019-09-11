package com.wang.reptile;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

/**
 * @Auther: wbh
 * @Date: 2019/9/10 17:01
 */
@Slf4j
public class DownloadTest {

    String path = "E:\\wang\\other\\download\\";//文件保存路径
    private DownloadPicFromURL download = new DownloadPicFromURL();
    private HttpUtil httpUtil = new HttpUtil();
    static int pageNo = 1;

    public static synchronized int getPageNo(){
        return  pageNo++;
    }

    @Test
    public void testManyThread(){
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {while(true){
                int num = getPageNo();
                if(num > 100){
                    break;
                }
                System.out.println("getNum() = " + num);
            }}).start();
        }
        while (true){

        }
    }

    //多线程抓取图片
    @Test
    public void downloadWallhaven() {
        for (int i = 0; i < 10; i++) {
            new Thread(new DownloadWallhavenThread()).start();
        }
        while (true){

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
        download.downloadPicture(src, path + name);


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
