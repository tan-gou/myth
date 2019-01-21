package com.github.myth.common.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum MythRoleEnum {

    START(1, "发起者"),

    LOCAL(2, "本地执行"),

    PROVIDER(3, "提供者"),
    ;

    private int code;
    private String desc;

    MythRoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static MythRoleEnum acquireByCode(int code) {
        Optional<MythRoleEnum> tccRoleEnum =
                Arrays.stream(MythRoleEnum.values())
                        .filter(v -> Objects.equals(v.getCode(), code))
                        .findFirst();
        return tccRoleEnum.orElse(MythRoleEnum.START);

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
