package com.happy.lucky.framework.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.happy.lucky.system.domain.SysExceptionLog;
import com.happy.lucky.system.domain.SysOperLog;
import com.happy.lucky.system.services.ISysExceptionLogService;
import com.happy.lucky.system.services.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时清理日志数据
 *
 * @author songyangpeng
 */
@Configuration
@EnableScheduling
public class ClearingLoginTask {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private ISysExceptionLogService sysExceptionLogService;

    /**
     * 每周五的晚上 23:59 执行
     */
    @Scheduled(cron = "59 23 * * * 05 ?")
    private void configureTasks() {
        System.err.println("开始执行删除日志操作: " + LocalDateTime.now());

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastTime = dateFormat.format(date);

        deleteSysOperLog(lastTime);
        deleteSysExceptionLog(lastTime);
    }

    private void deleteSysExceptionLog(String lastTime) {
        LambdaQueryWrapper<SysExceptionLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.gt(SysExceptionLog::getCreatedAt, lastTime);

        sysExceptionLogService.remove(lambdaQueryWrapper);
    }

    private void deleteSysOperLog(String lastTime) {
        LambdaQueryWrapper<SysOperLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.gt(SysOperLog::getCreatedAt, lastTime);

        sysOperLogService.remove(lambdaQueryWrapper);
    }
}
