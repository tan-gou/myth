package com.github.myth.common.config;

import lombok.Data;

@Data
public class MythMongoConfig {

    /**
     * mongo数据库设置
     */
    private String mongoDbName;

    private String mongoDbUrl;

    private String mongoUserName;
    private String mongoUserPwd;
}
