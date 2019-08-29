package com.wang.rocketmq.controller;

import com.wang.rocketmq.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/")
    public Object index(){
          return "index";
    }

    @RequestMapping(value = "/getMsg")
    public Object getMsg(String topic,String tags){

          return consumerService.getMsg(topic,tags);
    }
}
