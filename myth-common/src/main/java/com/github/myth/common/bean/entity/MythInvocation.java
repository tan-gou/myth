package com.github.myth.common.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MythInvocation implements Serializable {

    private static final long serialVersionUID = -5108578223428529356L;

    // 类
    @Getter
    private Class targetClass;

    // 方法
    @Getter
    private String methodName;

    // 参数类型
    @Getter
    private Class[] parameterTypes;

    // 参数
    @Getter
    private Object[] args;
}
