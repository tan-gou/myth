package com.github.myth.common.serializer;


import com.github.myth.common.exception.MythException;

public interface ObjectSerializer {

    /**
     * 序列化对象
     */
    byte[] serialize(Object obj) throws MythException;


    /**
     * 反序列化对象
     */
    <T> T deSerialize(byte[] param, Class<T> clazz) throws MythException;


    /**
     * 设置scheme
     *
     * @return scheme 命名
     */
    String getScheme();
}
