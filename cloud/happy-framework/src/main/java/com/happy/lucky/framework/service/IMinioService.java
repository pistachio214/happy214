package com.happy.lucky.framework.service;

import java.io.InputStream;
import java.util.List;

/**
 * Minio服务
 *
 * @author songyangpeng
 */
public interface IMinioService {

    //

    /**
     * 获取列表
     *
     * @return
     */
    List<String> listObjects();

    /**
     * 删除
     *
     * @param objectName
     */
    void deleteObject(String objectName);

    /**
     * 上传
     *
     * @param is
     * @param fileName
     * @param contentType
     */
    void uploadObject(InputStream is, String fileName, String contentType);

    /**
     * 获取minio中地址
     *
     * @param objectName
     * @return
     */
    String getObjectUrl(String objectName);

    /**
     * 下载minio服务的文件
     *
     * @param objectName
     * @return
     */
    InputStream getObject(String objectName);
}
