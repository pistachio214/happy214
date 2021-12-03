package com.happy.lucky.common.enums;

import com.happy.lucky.common.utils.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {

    SUCCESS(200, "操作成功！"),
    ERROR(500, "操作失败！"),
    UNANTHORIZED(401, "未授权"),
    FORBIDDEN(403, "访问受限,授权过期"),
    NOT_LOGIN(1001, "未登录"),
    ;

    private final Integer code;

    private final String message;

    //根据code获取message
    public static ResponseStatusEnum getEnumByCode(Integer code) {
        if (!BaseUtil.isEmpty(code)) {
            for (ResponseStatusEnum statusEnum : ResponseStatusEnum.values()) {
                if (code.equals(statusEnum.code)) {
                    return statusEnum;
                }
            }
        }
        return null;
    }
}
