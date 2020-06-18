package com.wang.other;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: wbh
 * @Date: 2019/9/15 16:36
 * @Description:
 */
public class DateTest {

    @Test
    public void testGetDate(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now.format(format) = " + now.format(format));
        System.out.println("now = " + now);


    }
}
