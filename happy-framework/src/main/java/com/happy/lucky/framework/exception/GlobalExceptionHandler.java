package com.happy.lucky.framework.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常拦截实现
 *
 * @author songyangpeng
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public R handler(NoHandlerFoundException e) {
        logger.info("接口不存在：----------------{}", e.getMessage());
        return R.error(ResponseStatusEnum.NOT_FOUND.getCode(), ResponseStatusEnum.NOT_FOUND.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) {
        logger.info("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return R.error(objectError.getDefaultMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public R handler(IllegalArgumentException e) {
        logger.error("Assert异常：----------------{}", e.getMessage());
        return R.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R handler(HttpMessageNotReadableException e) {
        logger.error("解析请求参数反向序列化失败：----------------{}", e.getMessage());
        return R.error(ResponseStatusEnum.SERIALIZABLE.getCode(), ResponseStatusEnum.SERIALIZABLE.getMessage());
    }

    @ExceptionHandler(value = NotLoginException.class)
    public R handler(NotLoginException e) {
        return R.error(ResponseStatusEnum.NOT_LOGIN.getCode(), ResponseStatusEnum.NOT_LOGIN.getMessage());
    }

    @ExceptionHandler(value = NotPermissionException.class)
    public R handler(NotPermissionException e) {
        return R.error(ResponseStatusEnum.INSUFFICIENT_PERMISSIONS.getCode(), ResponseStatusEnum.INSUFFICIENT_PERMISSIONS.getMessage());
    }

    @ExceptionHandler(value = NotRoleException.class)
    public R handler(NotRoleException e) {
        return R.error(ResponseStatusEnum.INSUFFICIENT_ROLE.getCode(), ResponseStatusEnum.INSUFFICIENT_ROLE.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e) {
        e.printStackTrace();
        logger.error("运行时异常：----------------{}", e.getMessage());
        return R.error(e.getMessage());
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = AuthenticationException.class)
//    public R handler(AuthenticationException e) {
//        e.printStackTrace();
//        return R.error("11111111111111111111111111");
//    }

}
