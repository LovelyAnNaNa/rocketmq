package com.wang.rocketmq.controller;

import com.wang.rocketmq.aop.NoRepeatSubmit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OtherController {

    private final MessageSource messageSource;

    @RequestMapping("/aaa")
    @ResponseBody
    public String test11(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return messageSource.getMessage("user.name",null, LocaleContextHolder.getLocale());
    }

    @ResponseBody
    @NoRepeatSubmit
    @RequestMapping("/test")
    public Object test(@RequestParam String str){
        return str;
    }

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
