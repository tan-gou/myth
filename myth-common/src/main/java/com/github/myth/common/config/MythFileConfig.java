package com.github.myth.common.config;

import lombok.Data;

@Data
public class MythFileConfig {

    /**
     * 文件保存路径
     */
    private String path;

    /**
     * 文件前缀
     */
    private String prefix;
}
