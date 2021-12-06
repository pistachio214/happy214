package com.happy.lucky.common.utils;

import com.happy.lucky.common.enums.ResponseStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//接口统一返回数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {

    @ApiModelProperty(value = "响应代码")
    private Integer code;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    private static <T> R<T> of(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public static <T> R<T> success(int code, String msg, T data) {
        return of(code, msg, data);
    }

    public static <T> R<T> success() {
        return of(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMessage(), null);
    }

    public static <T> R<T> success(T data) {
        return of(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> error() {
        return of(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getMessage(), null);
    }

    public static <T> R<T> error(String message) {
        return of(ResponseStatusEnum.ERROR.getCode(), message, null);
    }

    public static <T> R<T> error(Integer code) {
        return of(code, ResponseStatusEnum.ERROR.getMessage(), null);
    }

    public static <T> R<T> error(Integer code, String message) {
        return of(code, message, null);
    }
}
