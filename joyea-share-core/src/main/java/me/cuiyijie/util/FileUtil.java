package me.cuiyijie.util;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:26
 */
public class FileUtil {

    public static String getFileNameByPath(String fullFilePath) {
        String[] filePathArr = fullFilePath.split("/");
        return filePathArr[filePathArr.length - 1];
    }

}
