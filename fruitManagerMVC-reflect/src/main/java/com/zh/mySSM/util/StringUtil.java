package com.zh.mySSM.util;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/16 15:09
 * @description：
 * @modified By：
 * @version:
 */
public class StringUtil {
    //    判断字符串是否为空
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
