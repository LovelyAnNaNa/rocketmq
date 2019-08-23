package com.wang.rocketmq.controller;

import com.wang.rocketmq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @RequestMapping(value = "/sendMsg")
    public Object sendMsg(String msg) throws Exception {
        String s = producerService.sendMsg("demo_controller_topic", "tags", "keys_controller", msg);
        return s;
    }
}
