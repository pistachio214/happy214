package com.happy.lucky.common.utils;

import com.happy.lucky.common.core.StrFormatter;

import java.util.Arrays;

public class StringUtil {

    //格式化文本
    public static String format(String template, Object... params) {
        if (BaseUtil.isEmpty(template) || BaseUtil.isEmpty(Arrays.toString(params))) {
            return template;
        }

        return StrFormatter.format(template, params);
    }

}
