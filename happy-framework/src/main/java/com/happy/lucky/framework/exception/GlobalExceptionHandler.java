package com.happy.lucky.framework.exception;

import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.framework.security.exception.CaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public R handler(NoHandlerFoundException e) {
        logger.info("接口不存在：----------------{}", e.getMessage());
        return R.error(ResponseStatusEnum.NOT_FOUND.getCode(), ResponseStatusEnum.NOT_FOUND.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public R handler(AccessDeniedException e) {
        logger.info("security权限不足：----------------{}", e.getMessage());
        return R.error(ResponseStatusEnum.INSUFFICIENT_PERMISSIONS.getCode(), ResponseStatusEnum.INSUFFICIENT_PERMISSIONS.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) {
        logger.info("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return R.error(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public R handler(IllegalArgumentException e) {
        logger.error("Assert异常：----------------{}", e.getMessage());
        return R.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R handler(HttpMessageNotReadableException e) {
        logger.error("解析请求参数反向序列化失败：----------------{}", e.getMessage());
        return R.error(ResponseStatusEnum.SERIALIZABLE.getCode(), ResponseStatusEnum.SERIALIZABLE.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e) {
        logger.error("运行时异常：----------------{}", e.getMessage());
        return R.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CaptchaException.class)
    public R handler(CaptchaException e) {
        logger.error("运行时异常：----------------{}", e.getMessage());
        return R.error(e.getMessage());
    }
}
