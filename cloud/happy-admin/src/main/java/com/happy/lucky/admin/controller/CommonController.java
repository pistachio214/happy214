package com.happy.lucky.admin.controller;

import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.framework.service.IMinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author songyangpeng
 */
@Api(tags = "公共接口")
@RequestMapping("/common")
@RestController
public class CommonController {

    @Autowired
    private IMinioService minioService;

    @ApiOperation(value = "上传文件", notes = "上传文件至minio")
    @OperLog(operModul = "公共模块 - 上传文件", operType = Const.UPLOAD, operDesc = "上传文件")
    @PostMapping("/upload")
    public R<String> update(@RequestParam(name = "file", required = false) MultipartFile file) {
        try {
            //得到文件流
            InputStream is = file.getInputStream();
            //文件名
            String fileName = file.getOriginalFilename();
            String newFileName = UUID.randomUUID() + "-" + System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();
            minioService.uploadObject(is, newFileName, contentType);

            String url = minioService.getObjectUrl(newFileName);
            return R.success(newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败");
        }
    }

}
