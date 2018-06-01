package com.github.myth.common.bean.mq;

import com.github.myth.common.bean.entity.MythInvocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    /**
     * 事务id
     */
    private String transId;


    /**
     * 执行器
     */
    private MythInvocation mythInvocation;

}
