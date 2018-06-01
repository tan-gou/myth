package com.github.myth.core.service;

@FunctionalInterface
public interface MythMqSendService {

    /**
     * 发送消息
     * @param destination   队列
     * @param pattern       mq 模式
     * @param message       MythTransaction实体对象转换成byte[]后的数据
     */
    void sendMessage(String destination ,Integer pattern, byte[] message);
}
