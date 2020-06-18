package com.wang.rocketmq.controller;

import com.google.zxing.WriterException;
import com.wang.rocketmq.qrcode.ZxingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CodeController {

    /**
     * 生成二维码
     * @param response
     * @param format
     * @param content
     * @throws WriterException
     * @throws IOException
     */
    @RequestMapping(value = "make", method = RequestMethod.GET)
    public void readZxing(HttpServletResponse response, Integer size, Integer margin, String level,
                          String format, String content) throws WriterException, IOException {
        Integer width,height;
        if(size == null){
            width = 200;
            height = 200;
        }else{
            width = size;
            height = size;
        }
        if(margin == null) {
            margin = 0;
        }
        if(level == null) {
            level = "L";
        }
        if(format == null) {
            format = "gif";
        }
        if(content == null) {
            content = "http://tool.yoodb.com/qrcode/generate";
        }
        ZxingUtil.createZxing(response, width, height, margin, level, format, content);

    }
}
