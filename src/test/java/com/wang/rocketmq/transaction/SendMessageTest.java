package com.wang.rocketmq.transaction;

import org.junit.Test;

public class SendMessageTest {

    private SendMessage test = new SendMessage();

    @Test
    public void test() throws Exception {
        test.run("a","b","c","d");
    }
}
