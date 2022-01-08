package com.happy.lucky.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioUtil {

    private static String endpoint;

    private static String bucketName;

    @Value("${minio.endpoint}")
    public void setEndpoint(String endpoint) {
        MinioUtil.endpoint = endpoint;
    }

    @Value("${minio.bucketName}")
    public void setBucketName(String bucketName) {
        MinioUtil.bucketName = bucketName;
    }

    public static String generatorUrl(String fileName) {
        if (BaseUtil.isEmpty(fileName)) {
            return null;
        }
        return endpoint + "/" + bucketName + "/" + fileName;
    }

}
