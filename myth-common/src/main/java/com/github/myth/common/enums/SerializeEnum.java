package com.github.myth.common.enums;


import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum SerializeEnum {

    JDK("jdk"),

    KRYO("kryo"),

    HESSIAN("hessian"),

    PROTOSTUFF("protostuff"),
    ;

    private String serialize;

    SerializeEnum(String serialize) {
        this.serialize = serialize;
    }

    /**
     * Acquire serialize protocol serialize protocol enum.
     *
     * @param serialize the serialize protocol
     * @return the serialize protocol enum
     */
    public static SerializeEnum acquire(String serialize) {
        Optional<SerializeEnum> serializeEnum =
                Arrays.stream(SerializeEnum.values())
                        .filter(v -> Objects.equals(v.getSerialize(), serialize))
                        .findFirst();
        return serializeEnum.orElse(SerializeEnum.KRYO);

    }

    public String getSerialize() {
        return serialize;
    }
    public void setSerialize(String serialize) {
        this.serialize = serialize;
    }
}
