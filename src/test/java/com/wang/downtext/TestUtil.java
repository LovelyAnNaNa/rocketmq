package com.wang.downtext;

import com.wang.reptile.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @author wbh
 * @date 2020/9/2 14:27
 * @description
 */
public class TestUtil {

    private DownloadByWebInterface webDown = new DownloadByWebInterface();
    private HttpUtil httpUtil = new HttpUtil();
    private String baseUrl = "http://m.changdusk.com";

    @Test
    public void test11() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://wap.changduzw.com/62/96696_1/")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string().toString());
    }

    /**
     * 获取章节详情
     */
    @Test
    public void getChapterInfo() throws Exception {
        String chapterUrl = "/62/62633/8855413.html";
        chapterUrl = chapterUrl.replaceAll(".html","_section.html");
        int pageIndex = 1;
        for (int i = 1; i <= pageIndex; i++) {
            String curChapterUrl = chapterUrl.replaceAll("section", i+"");
            String html = httpUtil.getHtmlInfoFromUrl(baseUrl + curChapterUrl, "UTF-8");
            //第一次循环获取总页数,并赋值到遍历到总循环次数中
            if(i == 1){
                int totalPageIndex = html.indexOf("(1/");
                int startIndex = html.indexOf("");
                pageIndex = 2;
            }
            //获取页面信息
            Document htmlDoc = Jsoup.parse(html);
            Element nr1Ele = htmlDoc.getElementById("nr1");
            System.out.println(nr1Ele.text());
        }
    }

    /**
     * 获取章节信息列表
     */
    @Test
    public void getBookChapter(){
        String chapterUrl = "/62/62633_section/";
        int pageIndex = 1;
        for (int i = 1; i <= pageIndex; i++) {
            String curChapterUrl = chapterUrl.replaceAll("section", i+"");
            String html = httpUtil.getHtmlInfoFromUrl(baseUrl + curChapterUrl, "UTF-8");
            //第一次循环获取总页数,并赋值到遍历到总循环次数中
            if(i == 1){
                pageIndex = getTotalPage(html);
            }
            //获取页面信息 
            Document htmlDoc = Jsoup.parse(html);
            Elements chapterEleList = htmlDoc.getElementsByClass("chapter");
            Element chapter = chapterEleList.get(chapterEleList.size() - 1);
            Elements chapterHrefList = chapter.getElementsByTag("a");
            chapterHrefList.forEach(c -> {
                System.out.println(c.text() + " : " + c.attr("href"));
            });

        }
    }

    /**
     * 获取某一个分类下的所有图书列表
     */
    @Test
    public void getSortBooks() throws Exception{
        String sortUrl = "/sort/5_index/";
        int pageIndex = 1;

        for (int i = 1; i <= pageIndex; i++) {
            String curSortUrl = sortUrl.replaceAll("index", i+"");
            String html = httpUtil.getHtmlInfoFromUrl2(baseUrl + curSortUrl, "UTF-8");
            //第一次循环获取总页数,并赋值到遍历到总循环次数中
            if(i == 1){
                pageIndex = getTotalPage(html);
            }

            //获取页面信息
            Document htmlDoc = Jsoup.parse(html);
            //获取图书超链接信息
            Elements coverEleList = htmlDoc.getElementsByClass("cover");
            Element cover = coverEleList.get(coverEleList.size() - 1);
            //所有图书超链接
            Elements booksHref = cover.getElementsByClass("blue");
            booksHref.forEach(book -> {
                System.out.println(book.text() + " : " + book.attr("href"));
            });
        }
    }

    /**
     * 获取所有分类信息
     */
    @Test
    public void getSortList(){
        String html = httpUtil.getHtmlInfoFromUrl(baseUrl + "/sort/", "UTF-8");
        //获取页面信息
        Document htmlDoc = Jsoup.parse(html);
        Elements contentClass = htmlDoc.getElementsByClass("content");
        Element content = contentClass.get(contentClass.size() - 1);
        Elements aEleList = content.getElementsByTag("a");
        aEleList.forEach(a -> {
            System.out.println(a.text() + " : " + a.attr("href"));
        });
    }

    /**
     * 获取页面中的总页数
     * 需符合 "(第1/153页)当前" 格式
     * @param html
     * @return
     */
    public static Integer getTotalPage(String html){
        int totalPageIndex = html.indexOf("页)当前");
        int startIndex = html.indexOf("第1/");
        if (totalPageIndex == -1 && startIndex == -1) {
            return 0;
        }
        return Integer.valueOf(html.substring(startIndex + 3, totalPageIndex));
    }

    @Test
    public void downloadTxtByInteface(){
        System.out.println(httpUtil.getHtmlInfoFromUrl2("http://txt.changdusk.com/modules/article/packdown.php?id=62633&type=txt&fname=%B4%F3%CD%C5%BD%E1", null));
    }

    @Test
    public void textOther() throws Exception{
        webDown.getSortPageBooks("/sort/5_1/",10);
    }
}
