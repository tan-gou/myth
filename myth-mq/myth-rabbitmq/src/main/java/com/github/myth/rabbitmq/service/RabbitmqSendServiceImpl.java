package com.github.myth.rabbitmq.service;

import com.github.myth.common.utils.LogUtil;
import com.github.myth.core.service.MythMqSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 *  Rabbitmq 发消息
 */
public class RabbitmqSendServiceImpl implements MythMqSendService,RabbitTemplate.ConfirmCallback{

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqSendServiceImpl.class);

    private AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * 发送消息
     */
    @Override
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        amqpTemplate.convertAndSend(destination, message);
    }


    /**
     * Confirmation callback.
      * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            LogUtil.info(LOGGER,()->"消息成功发送！");
        } else {
            LogUtil.info(LOGGER,()->"消息发送失败！" + cause+"\n重新发送");

        }
    }
}
