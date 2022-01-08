package com.happy.lucky.framework.service;

import java.io.InputStream;
import java.util.List;

/**
 * Minio服务
 */
public interface IMinioService {

    //获取列表
    List<String> listObjects();

    //删除
    void deleteObject(String objectName);

    //上传
    void uploadObject(InputStream is, String fileName, String contentType);

    //获取minio中地址
    String getObjectUrl(String objectName);

    //下载minio服务的文件
    InputStream getObject(String objectName);
}
