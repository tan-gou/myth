package com.github.myth.common.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MythParticipant implements Serializable {

    private static final long serialVersionUID = -2590970715288987627L;

    /**
     * 事务id
     */
    private String transId;

    /**
     * 队列(TOPIC,如果是rocketmq或者aliyunmq,这里包含TOPIC和TAG),用,区分
     */
    private String destination;

    /**
     * 消息模式
     */
    private Integer pattern;

    /**
     * 执行器
     */
    private MythInvocation mythInvocation;
}
