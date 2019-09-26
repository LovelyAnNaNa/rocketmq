package com.wang.other;

import lombok.Data;

import java.io.Serializable;

/**
 * 推送报文一级对象
 */
@Data
public class Message implements Serializable {
    private String method;
    private String params;
}