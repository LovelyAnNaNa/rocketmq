package com.wang.rocketmq.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getCurTime(){
        return df.format(new Date());
    }

}
