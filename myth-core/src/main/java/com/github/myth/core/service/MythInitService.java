package com.github.myth.core.service;

import com.github.myth.common.config.MythConfig;


@FunctionalInterface
public interface MythInitService {

    /**
     * Myth分布式事务初始化方法
     */
    void initialization(MythConfig mythConfig);
}
