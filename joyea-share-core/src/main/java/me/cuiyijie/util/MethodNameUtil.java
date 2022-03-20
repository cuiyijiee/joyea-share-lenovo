package me.cuiyijie.util;

/**
 * @Author: cuiyijie
 * @Date: 2021/11/19 15:39
 */
public class MethodNameUtil {
    public MethodNameUtil() {
    }

    public static String getMethodName(String name) {
        char c1 = name.charAt(0);
        char c2 = name.charAt(1);
        return Character.isUpperCase(c2) ? name : Character.toUpperCase(c1) + name.substring(1);
    }
}
