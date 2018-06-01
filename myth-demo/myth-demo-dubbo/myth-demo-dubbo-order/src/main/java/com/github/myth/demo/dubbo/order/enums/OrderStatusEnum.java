package com.github.myth.demo.dubbo.order.enums;

public enum OrderStatusEnum {

    NOT_PAY(1, "未支付"),

    PAYING(2, "支付中"),

    PAY_FAIL(3, "支付失败"),

    PAY_SUCCESS(4, "支付成功"),
    ;

    private int code;
    private String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
