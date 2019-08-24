package com.wang.rocketmq.controller;

import com.wang.rocketmq.service.AsyncProducerService;
import com.wang.rocketmq.service.OneProducerService;
import com.wang.rocketmq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private AsyncProducerService asyncProducerService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private OneProducerService oneService;


    @RequestMapping(value = "/sendOneMsg")
    public Object sendOneMsg(String msg){
        oneService.sendOneWayMsg(msg);
          return null;
    }

    @RequestMapping(value = "/asyncSendMsg")
    public Object asyncSendMsg(String msg){
          return asyncProducerService.asyncSendMsg(msg);
    }

    @RequestMapping(value = "/sendMsg")
    public Object sendMsg(String msg) throws Exception {
        String s = producerService.sendMsg("demo_controller_topic", "tags", "keys_controller", msg);
        return s;
    }
}
