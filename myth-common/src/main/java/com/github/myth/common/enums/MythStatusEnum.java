package com.github.myth.common.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


public enum MythStatusEnum {

    BEGIN(0, "开始"),

    ROLLBACK(2, "回滚"),

    COMMIT(1, "已经提交"),


    SEND_MSG(3, "可以发送消息"),

    FAILURE(4, "失败"),

    PRE_COMMIT(5, "预提交"),

    LOCK(6, "锁定"),
    ;


    private int code;

    private String desc;

    MythStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static MythStatusEnum acquireByCode(int code) {
        Optional<MythStatusEnum> transactionStatusEnum =
                Arrays.stream(MythStatusEnum.values())
                        .filter(v -> Objects.equals(v.getCode(), code))
                        .findFirst();
        return transactionStatusEnum.orElse(MythStatusEnum.BEGIN);
    }

    public static String acquireDescByCode(int code) {
        return acquireByCode(code).getDesc();
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
