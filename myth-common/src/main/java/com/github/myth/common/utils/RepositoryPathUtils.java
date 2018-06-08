package com.github.myth.common.utils;

import com.github.myth.common.constant.CommonConstant;

/**
 * 获取资源路径的工具类
 */
public class RepositoryPathUtils {


    public static String buildRedisKey(String keyPrefix, String id) {
        return String.join(":", keyPrefix, id);
    }


    public static String buildFilePath(String applicationName) {
        return String.join("/", CommonConstant.PATH_SUFFIX, applicationName.replaceAll("-", "_"));
    }


    public static  String getFullFileName(String filePath,String id) {
        return String.format("%s/%s", filePath, id);
    }


    public static String buildDbTableName(String applicationName) {
        return CommonConstant.DB_SUFFIX + applicationName.replaceAll("-", "_");
    }


    public static String buildMongoTableName(String applicationName) {
        return CommonConstant.DB_SUFFIX + applicationName.replaceAll("-", "_");
    }

    public static String buildRedisKeyPrefix(String applicationName) {
        return String.format(CommonConstant.RECOVER_REDIS_KEY_PRE, applicationName);
    }

    public static String buildZookeeperPathPrefix(String applicationName) {
        return String.join("-", CommonConstant.PATH_SUFFIX, applicationName);
    }


    public static String buildZookeeperRootPath(String prefix, String id) {
        return String.join("/", prefix, id);
    }

}
