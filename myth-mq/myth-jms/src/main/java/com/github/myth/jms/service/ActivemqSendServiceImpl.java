package com.github.myth.jms.service;

import com.github.myth.core.service.MythMqSendService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.Objects;

import static com.github.myth.annotation.MessageTypeEnum.P2P;
import static com.github.myth.annotation.MessageTypeEnum.TOPIC;

public class ActivemqSendServiceImpl implements MythMqSendService {

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 发送消息
     */
    @Override
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        Destination queue = new ActiveMQQueue(destination);
        if (Objects.equals(P2P.getCode(), pattern)) {
            queue  = new ActiveMQQueue(destination);
        } else if (Objects.equals(TOPIC.getCode(), pattern)) {
            queue  = new ActiveMQTopic(destination);
        }
        jmsTemplate.convertAndSend(queue , message);
    }

}
