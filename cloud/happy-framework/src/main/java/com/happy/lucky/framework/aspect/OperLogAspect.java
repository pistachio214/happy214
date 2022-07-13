package com.happy.lucky.framework.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.framework.utils.IpUtil;
import com.happy.lucky.system.domain.SysExceptionLog;
import com.happy.lucky.system.domain.SysOperLog;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.services.ISysExceptionLogService;
import com.happy.lucky.system.services.ISysOperLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面处理类，操作日志异常日志记录处理
 *
 * @author Pengsy
 */
@Aspect
@Component
public class OperLogAspect {

    @Value("${happy.version}")
    private String operVer;

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private ISysExceptionLogService sysExceptionLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.happy.lucky.framework.annotation.OperLog)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.happy.lucky.*.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) throws IOException {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperLog sysOperLog = new SysOperLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String operModul = opLog.operModul();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                // 操作模块
                sysOperLog.setOperModul(operModul);
                // 操作类型
                sysOperLog.setOperType(operType);
                // 操作描述
                sysOperLog.setOperDesc(operDesc);
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            // 请求方法
            sysOperLog.setOperMethod(methodName);

            // 请求方式
            sysOperLog.setOperRequMethod(request.getMethod());

            // 请求的参数
            String params = getParamToString(request);

            // 请求参数
            sysOperLog.setOperRequParam(params);
            // 返回结果
            sysOperLog.setOperRespParam(JSON.toJSONString(keys));

            SysUser sysUser = (SysUser) StpUtil.getSession().get("user");

            // 请求用户ID
            sysOperLog.setOperUserId(sysUser.getId());
            // 请求用户名称
            sysOperLog.setOperUserName(sysUser.getUsername());
            // 请求IP
            sysOperLog.setOperIp(IpUtil.getIp(request));
            // 请求URI
            sysOperLog.setOperUri(request.getRequestURI());
            // 操作版本
            sysOperLog.setOperVer(operVer);
            sysOperLogService.save(sysOperLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) throws IOException {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysExceptionLog sysExceptionLog = new SysExceptionLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            // 请求的参数
            String params = getParamToString(request);
            // 请求参数
            sysExceptionLog.setExcRequParam(params);
            // 请求方式
            sysExceptionLog.setOperRequMethod(request.getMethod());
            // 请求方法名
            sysExceptionLog.setOperMethod(methodName);
            // 异常名称
            sysExceptionLog.setExcName(e.getClass().getName());
            // 异常信息
            sysExceptionLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));

            SysUser sysUser = (SysUser) StpUtil.getSession().get("user");

            // 操作员ID
            sysExceptionLog.setOperUserId(sysUser.getId());
            // 操作员名称
            sysExceptionLog.setOperUserName(sysUser.getUsername());
            // 操作URI
            sysExceptionLog.setOperUri(request.getRequestURI());
            // 操作员IP
            sysExceptionLog.setOperIp(IpUtil.getIp(request));
            // 操作版本号
            sysExceptionLog.setOperVer(operVer);

            sysExceptionLogService.save(sysExceptionLog);

        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    public String getParamToString(HttpServletRequest request) throws IOException {
        String params;
        switch (request.getMethod()) {
            case "GET":
                Map<String, String> rtnMap = converMap(request.getParameterMap());
                params = JSON.toJSONString(rtnMap);
                break;
            case "POST":
            case "PUT":
                params = getPostParams(request);
                break;
            default:
                params = null;
        }

        return params;
    }

    private String getPostParams(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bfReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bfReader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            System.out.println("key=" + key);
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
