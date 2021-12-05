package com.happy.lucky.common.utils;

import com.happy.lucky.common.enums.ResponseStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//接口统一返回数据
@ApiModel
public class R {

    @ApiModelProperty(value = "响应代码")
    private Integer code;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static R success(int code, String msg, Object data) {
        R r = new R();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);

        return r;
    }

    public static R success() {
        R r = new R();
        r.setCode(ResponseStatusEnum.SUCCESS.getCode());
        r.setMessage(ResponseStatusEnum.SUCCESS.getMessage());

        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(ResponseStatusEnum.SUCCESS.getCode());
        r.setMessage(ResponseStatusEnum.SUCCESS.getMessage());
        r.setData(data);

        return r;
    }

    public static R error() {
        R r = new R();
        r.setCode(ResponseStatusEnum.ERROR.getCode());
        r.setMessage(ResponseStatusEnum.ERROR.getMessage());

        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.setCode(ResponseStatusEnum.ERROR.getCode());
        r.setMessage(message);

        return r;
    }

    public static R error(Integer code) {
        R r = new R();
        r.setCode(code);
        r.setMessage(ResponseStatusEnum.ERROR.getMessage());

        return r;
    }

    public static R error(Integer code, String message) {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);

        return r;
    }
}
