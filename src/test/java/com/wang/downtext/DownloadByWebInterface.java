package com.wang.downtext;

import com.wang.reptile.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wbh
 * @date 2020/9/3 9:25
 * @description 使用网站提供的下载接口下载
 */
@Slf4j
public class DownloadByWebInterface {

    private HttpUtil httpUtil = new HttpUtil();
    private String baseUrl = "http://m.changdusk.com";
    private String fileSavePath = "D:\\download\\other\\text\\";
    private String downloadTemplate = "http://txt.changdusk.com/modules/article/packdown.php?id=bookId&type=txt&fname=%B4%F3%CD%C5%BD%E1";

    @Test
    public void downloadAll() {
        int downloadCount = 0;
        int breakDownloadCount = 634;

        Map<String, String> sortMap = getSortInfo();
        Set<String> sortKeys = sortMap.keySet();
        for (String sortKey : sortKeys) {
            String sortValue = sortMap.get(sortKey);
            //该分类小说存储路径
            File sortFileDir = new File(fileSavePath + sortKey);
            sortFileDir.mkdirs();
            //获取分类的总页数
            Integer sortTotalPage = this.getTotalPage(baseUrl + sortValue);
            for (int sortPage = 1; sortPage <= sortTotalPage; sortPage++) {
                //获取页面的所有图书信息
                HashMap<String, String> sortPageBooks = getSortPageBooks(sortValue, sortPage);
                //遍历页面中的图书信息
                Set<String> bookKeySet = sortPageBooks.keySet();
                for (String bookName : bookKeySet) {
                    //跳过已下载的数量
                    if (++downloadCount <= breakDownloadCount){
                        log.info("跳过第{}本书的下载,共需跳过{}本",downloadCount,breakDownloadCount);
                        continue;
                    }else {
                        log.info("开始下载第{}本",downloadCount);
                    }

                    String bookUrl = sortPageBooks.get(bookName);
                    //图书存储路径
                    String savePath = sortFileDir.getAbsolutePath() + File.separator + bookName + ".txt";
                    try {
                        downloadBook(savePath,bookUrl);
                        log.info("图书: {}下载成功",bookName);
                    } catch (IOException e) {e.printStackTrace();}
                }
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }

    public void downloadBook(String savePath,String bookUrl) throws IOException {
        //获取图书id
        int idStartIndex = bookUrl.indexOf("/book/") + 6;
        int idEndIndex = bookUrl.lastIndexOf("/");
        String bookId = bookUrl.substring(idStartIndex, idEndIndex);
        //获取完整图书信息
        String downloadUrl = downloadTemplate.replaceAll("bookId", bookId);
        log.info("图书下载链接:{},存储路径:{}",downloadUrl,savePath);
        String bookTxt = httpUtil.getHtmlInfoFromUrl2(downloadUrl, null);

        try(//图书信息写入到文件中
            BufferedWriter bw = new BufferedWriter(new FileWriter(savePath));
            ){
            bw.write(bookTxt);
        }catch (Exception e){
            log.error("图书id为{}的图书下载失败,下载链接{}",bookId,downloadUrl);
            throw e;
        }

    }

    /**
     * 获取分类对应页码图书信息列表
     * @param sortUrl 分页路径 /sort/5_1/
     * @param page 页码
     * @return key:书本名称,value:书本路径
     * @throws Exception
     */
    public HashMap<String, String> getSortPageBooks(String sortUrl, int page)  {
        HashMap<String, String> booksMap = new HashMap<>(20);

        int pagePreIndex = sortUrl.lastIndexOf("_");
        //截取页码,然后重新拼接页码
        sortUrl = sortUrl.substring(0,pagePreIndex + 1);
        sortUrl += page + "/";

        String html = httpUtil.getHtmlInfoFromUrl2(baseUrl + sortUrl, "UTF-8");
        //获取页面信息
        Document htmlDoc = Jsoup.parse(html);
        //获取图书超链接信息
        Elements coverEleList = htmlDoc.getElementsByClass("cover");
        Element cover = coverEleList.get(coverEleList.size() - 1);
        //所有图书超链接
        Elements booksHref = cover.getElementsByClass("blue");
        booksHref.forEach(book -> {
//            System.out.println(book.text() + " : " + book.attr("href"));
            booksMap.put(book.text(),book.attr("href"));
        });

        return booksMap;
    }

    /**
     * 获取所有分类信息
     *
     * @return key:分类名称,value:分类url
     */
    public Map<String, String> getSortInfo() {
        HashMap<String, String> sortMap = new HashMap<>(10);
        String html = httpUtil.getHtmlInfoFromUrl(baseUrl + "/sort/", "UTF-8");
        //获取页面信息
        Document htmlDoc = Jsoup.parse(html);
        Elements contentClass = htmlDoc.getElementsByClass("content");
        Element content = contentClass.get(contentClass.size() - 1);
        Elements aEleList = content.getElementsByTag("a");
        aEleList.forEach(a -> {
            //System.out.println(a.text() + " : " + a.attr("href"));
            sortMap.put(a.text(), a.attr("href"));
        });
        return sortMap;
    }


    /**
     * 获取对应页面的分页信息
     *
     * @param url 分页的页面
     * @return
     */
    public Integer getTotalPage(String url) {
        String html = httpUtil.getHtmlInfoFromUrl2(url, "UTF-8");
        return TestUtil.getTotalPage(html);
    }

}
