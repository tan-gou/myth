package com.github.myth.kafka.service;

import com.github.myth.core.service.MythMqSendService;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSendServiceImpl implements MythMqSendService {


    private KafkaTemplate kafkaTemplate;

    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息
     *
     * @param destination 队列
     * @param pattern     mq 模式
     * @param message     MythTransaction实体对象转换成byte[]后的数据
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        kafkaTemplate.send(destination, message);

    }
}
