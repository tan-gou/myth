package com.github.myth.annotation;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {

    P2P(1, "点对点模式"),

    TOPIC(2, "TOPIC模式"),
    ;

    private Integer code;
    private String desc;

    MessageTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
