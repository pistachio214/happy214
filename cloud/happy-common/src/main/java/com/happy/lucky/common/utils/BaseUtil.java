package com.happy.lucky.common.utils;

/**
 * @author songyangpeng
 */
public class BaseUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty() || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断数字为空
     *
     * @param num
     * @return
     */
    public static boolean isEmpty(Integer num) {
        if (num == null) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || (objects.length == 0);
    }
}
