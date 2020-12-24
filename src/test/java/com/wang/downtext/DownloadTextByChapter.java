package com.wang.downtext;

import com.wang.reptile.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;

/**
 * @author wbh
 * @date 2020/9/2 15:20
 * @description 下载小说
 */
@Slf4j
public class DownloadTextByChapter {

    private HttpUtil httpUtil = new HttpUtil();
    private String baseUrl = "http://m.changdusk.com";
    private String fileSavePath = "E:\\download\\other\\text\\";

    @Test
    public void testDownLoadBook() throws Exception {
//        downloadBook("62633");
        downloadBookByPage("96696");
    }

    /**
     * 以此获取一页章节并下载
     * @param bookId
     */
    public void downloadBookByPage(String bookId) throws Exception{
        File saveFile = new File(fileSavePath + "大团结.txt");
        if (!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
            saveFile.createNewFile();
        }
        FileWriter sw = new FileWriter(saveFile);
        BufferedWriter bw = new BufferedWriter(sw);
        //所有章节页数
        Integer chapterPage = getChapterTotalPage(bookId);

        for (int j = 1; j <= chapterPage; j++) {
            //获取对应章节页信息
            HashMap<String, String> bookChapter = getBookChapter(bookId, j + "");
            //写入章节信息
            writerChapterToBook(bw,bookChapter);
            bw.flush();
        }
        bw.close();

    }

    /**
     * 获取章节详情
     */
    public void downloadBook(String bookId) throws Exception {

        FileWriter sw = new FileWriter(new File(fileSavePath + "火影之强者系统.txt"));
        BufferedWriter bw = new BufferedWriter(sw);
        String chapterUrl = null;
//        String chapterUrl = "/62/bookId/8855413.html";
//        chapterUrl = chapterUrl.replaceAll("bookId",bookId);
        //替换为章节页码格式的url


        //章节列表 key:章节名称,value:章节url
        HashMap<String, String> bookChapter = getBookChapter(bookId);
        Set<String> keySet = bookChapter.keySet();

        String nextLine = null;

        int count = 1;

        //获取所有章节后等待二十秒
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String cName : keySet) {
            //获取对应的章节URL
            chapterUrl = bookChapter.get(cName);
            chapterUrl = chapterUrl.replaceAll(".html", "_section.html");

            //章节行
            bw.write(cName);
            bw.newLine();
            //获取对应章节下的所有文字
            int pageIndex = 1;
            for (int i = 1; i <= pageIndex; i++) {
                String curChapterUrl = chapterUrl.replaceAll("section", i + "");

                //防止请求过于频繁
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String html = httpUtil.getHtmlInfoFromUrl2(baseUrl + curChapterUrl, "UTF-8");
                //获取页面信息
                Document htmlDoc = Jsoup.parse(html);
                //第一次循环获取总页数,并赋值到遍历到总循环次数中
                if (i == 1) {
                    String title = htmlDoc.getElementById("nr_title").text();
                    int totalPageStart = title.lastIndexOf("(1/");
                    int totalPageEnd = title.lastIndexOf(")");
                    pageIndex = Integer.valueOf(title.substring(totalPageStart + 3, totalPageEnd));
                }
                Element nr1Ele = htmlDoc.getElementById("nr1");
                nextLine = nr1Ele.text();
                log.info(nextLine);
                //如果报错重新来
                if (StringUtils.isNotBlank(nextLine) && nextLine.contains("章节服务器被攻击，紧急维护中")) {
                    i -= 1;
                    continue;
                }
                bw.write(nextLine);
                bw.newLine();
            }
            count++;
            if (count == 100) {
                bw.close();
                sw.close();
                break;
            }
        }

    }

    /**
     * 获取书籍章节分页总数
     *
     * @param bookId
     * @return
     */
    public Integer getChapterTotalPage(String bookId) {
        String chapterUrl = "/62/bookId_1/";
        chapterUrl = chapterUrl.replaceAll("bookId", bookId);
        String html = httpUtil.getHtmlInfoFromUrl2(baseUrl + chapterUrl, "UTF-8");
        return TestUtil.getTotalPage(html);
    }

    /**
     * 获取某一本书下的所有章节
     *
     * @param bookId 书本id
     * @return key:章节名称,value:章节url
     */
    public HashMap<String, String> getBookChapter(String bookId) {
        String chapterUrl = "/62/bookId_section/";
        chapterUrl = chapterUrl.replaceAll("bookId", bookId);
        int pageIndex = 1;
        //key:章节名称,value:章节url
        HashMap<String, String> chapterMap = null;

        for (int i = 1; i <= pageIndex; i++) {
            String curChapterUrl = chapterUrl.replaceAll("section", i + "");
            String html = httpUtil.getHtmlInfoFromUrl(baseUrl + curChapterUrl, "UTF-8");
            //第一次循环获取总页数,并赋值到遍历到总循环次数中
            if (i == 1) {
                pageIndex = TestUtil.getTotalPage(html);
                //一页20条数据
                chapterMap = new HashMap<>(pageIndex * 20);
            }
            //获取页面信息
            Document htmlDoc = Jsoup.parse(html);
            Elements chapterEleList = htmlDoc.getElementsByClass("chapter");
            Element chapter = chapterEleList.get(chapterEleList.size() - 1);
            //获取所有章节导航
            Elements chapterHrefList = chapter.getElementsByTag("a");
            for (Element c : chapterHrefList) {
                //System.out.println(c.text() + " : " + c.attr("href"));
                chapterMap.put(c.text(), c.attr("href"));
            }
            log.info("已经获取 {} 章节", chapterMap.size());
            //try { Thread.sleep(100); } catch (InterruptedException e) {e.printStackTrace();            }
        }
        return chapterMap;
    }

    /**
     * 向文件中写入章节信息
     * @param bw      文件输入流
     * @param bookChapter 章节信息 key:章节名称,value章节url
     * @throws Exception
     */
    public void writerChapterToBook(BufferedWriter bw,HashMap<String, String> bookChapter) throws Exception{
        String chapterUrl = null;
        Set<String> keySet = bookChapter.keySet();

        String nextLine = null;
        for (String cName : keySet) {
            //获取对应的章节URL
            log.info("当前章节名称: {}",cName);
            chapterUrl = bookChapter.get(cName);
            chapterUrl = chapterUrl.replaceAll(".html", "_section.html");

            //章节行
            bw.write(cName);
            bw.newLine();
            //获取对应章节下的所有文字
            int pageIndex = 1;
            for (int i = 1; i <= pageIndex; i++) {
                String curChapterUrl = chapterUrl.replaceAll("section", i + "");

                //防止请求过于频繁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String html = httpUtil.getHtmlInfoFromUrl2(baseUrl + curChapterUrl, "UTF-8");
                //获取页面信息
                Document htmlDoc = Jsoup.parse(html);
                //第一次循环获取总页数,并赋值到遍历到总循环次数中
                if (i == 1) {
                    String title = htmlDoc.getElementById("nr_title").text();
                    int totalPageStart = title.lastIndexOf("(1/");
                    int totalPageEnd = title.lastIndexOf(")");
                    pageIndex = Integer.valueOf(title.substring(totalPageStart + 3, totalPageEnd));
                }
                Element nr1Ele = htmlDoc.getElementById("nr1");
                //是否有标识本章未完的提示元素
                Elements centerList = nr1Ele.getElementsByTag("center");
                if (centerList.size() > 0) {
                    centerList.forEach(c -> c.remove());
                }

                nextLine = nr1Ele.text();
                log.info(nextLine);
                //如果报错重新来
                if (StringUtils.isNotBlank(nextLine) && nextLine.contains("章节服务器被攻击，紧急维护中")) {
                    i -= 1;
                    continue;
                }
                bw.write(nextLine);
                bw.newLine();
            }
        }
    }

    /**
     * 获取某一本书某一页面章节
     *
     * @param bookId  书本id
     * @param section 章节分组对应页面
     * @return key:章节名称,value:章节url
     */
    public HashMap<String, String> getBookChapter(String bookId, String section) {
        String chapterUrl = "/62/bookId_section/";
        chapterUrl = chapterUrl.replaceAll("bookId", bookId);
        chapterUrl = chapterUrl.replaceAll("section", section);
        //key:章节名称,value:章节url
        HashMap<String, String> chapterMap = chapterMap = new HashMap<>(20);;

        String html = httpUtil.getHtmlInfoFromUrl(baseUrl + chapterUrl, "UTF-8");
        //获取页面信息
        Document htmlDoc = Jsoup.parse(html);
        Elements chapterEleList = htmlDoc.getElementsByClass("chapter");
        Element chapter = chapterEleList.get(chapterEleList.size() - 1);
        //获取所有章节导航
        Elements chapterHrefList = chapter.getElementsByTag("a");
        for (Element c : chapterHrefList) {
            //System.out.println(c.text() + " : " + c.attr("href"));
            chapterMap.put(c.text(), c.attr("href"));
        }
        log.info("已经获取 {} 章节", chapterMap.size());
        return chapterMap;
    }
}