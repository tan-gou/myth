package com.github.myth.common.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum RepositorySupportEnum {

    DB("db"),

    FILE("file"),

    REDIS("redis"),

    MONGODB("mongodb"),

    ZOOKEEPER("zookeeper"),
    ;

    private String support;

    RepositorySupportEnum(String support) {
        this.support = support;
    }


    public static RepositorySupportEnum acquire(String support) {
        Optional<RepositorySupportEnum> repositorySupportEnum =
                Arrays.stream(RepositorySupportEnum.values())
                        .filter(v -> Objects.equals(v.getSupport(), support))
                        .findFirst();
        return repositorySupportEnum.orElse(RepositorySupportEnum.DB);
    }


    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
