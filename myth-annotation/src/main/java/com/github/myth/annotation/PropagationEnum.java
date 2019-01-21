package com.github.myth.annotation;

/**
 * 事务的传播级别
 */
public enum PropagationEnum {

    PROPAGATION_REQUIRED(0),

    PROPAGATION_SUPPORTS(1),
    PROPAGATION_MANDATORY(2),
    PROPAGATION_REQUIRES_NEW(3),

    PROPAGATION_NOT_SUPPORTED(4),
    PROPAGATION_NEVER(5),
    PROPAGATION_NESTED(6);


    private final int value;

    PropagationEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
