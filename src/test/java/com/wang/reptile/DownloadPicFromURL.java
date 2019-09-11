package com.wang.reptile;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadPicFromURL {




    /**
     * 获取图片路径中图片的名称
     */
    public String getUrlImgName(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    //链接url下载图片
    public boolean downloadPicture(String picUrl, String path) {
        URL url = null;
        try {
            url = new URL(picUrl);
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            DataInputStream dataInputStream = new DataInputStream(uc.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            output.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}