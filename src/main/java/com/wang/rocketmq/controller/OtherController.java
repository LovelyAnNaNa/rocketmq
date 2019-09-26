package com.wang.rocketmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class OtherController {

    @ResponseBody
    @RequestMapping(value = "/downloadImg")
    public String downloadImg(HttpServletResponse response){
        String fileName = "1.png";
        
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        response.setContentType("application/force-download");// 设置强制下载不打开
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try(
                ServletOutputStream outStream = response.getOutputStream();
                InputStream inputStream = new BufferedInputStream(new FileInputStream("E:\\wang\\other\\idea2019.2\\image\\1.png"));
        ){
            int i = inputStream.read();
            while (i != -1){
                outStream.write(i);
                i = inputStream.read();
            }
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }

        return "failed";
    }

    @RequestMapping(value = "/")
    public String index(){
          return "index";
    }
}
