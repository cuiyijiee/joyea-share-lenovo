package me.cuiyijie.util;

/**
 * @Author: cuiyijie
 * @Date: 2021/11/19 15:38
 */

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    
    private static int sequence = 10000;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static String DATE_TIMEZONE = "GMT+8";

    public StringUtils() {
    }

    public static synchronized String getSequence() {
        if (sequence >= 99999) {
            sequence = 10000;
        }

        ++sequence;
        return sdf.format(new Date()) + sequence;
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else {
            return str1 != null && str1.equals(str2);
        }
    }

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else {
            return obj1 != null && obj1.equals(obj2);
        }
    }

    public static String leftPad(String str, int strLength) {
        return leftPad(str, strLength, '0');
    }

    public static String leftPad(String str, int strLength, char padChar) {
        int strLen = getGBKLen(str);
        if (strLen < strLength) {
            while(strLen < strLength) {
                str = padChar + str;
                strLen = getGBKLen(str);
            }
        }

        return str;
    }

    public static String rightPad(String str, int strLength) {
        return rightPad(str, strLength, '0');
    }

    public static String rightPad(String str, int strLength, char padChar) {
        int strLen = str.length();
        if (strLen < strLength) {
            while(strLen < strLength) {
                str = str + "0";
                strLen = str.length();
            }
        }

        return str;
    }

    public static int getGBKLen(String str) {
        return getLen(str, "gb18030");
    }

    public static int getUTF8Len(String str) {
        return getLen(str, "utf-8");
    }

    public static int getLen(String str, String charset) {
        if (str == null) {
            return 0;
        } else {
            try {
                return str.getBytes(charset).length;
            } catch (UnsupportedEncodingException var3) {
                log.error(var3.getMessage(), var3);
                return 0;
            }
        }
    }

    public static int getLen(String str) {
        return getLen(str, "utf-8");
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString().toUpperCase();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    public static byte[] HexString2Bytes(String src) {
        return HexString2Bytes(src, "utf-8");
    }

    public static byte[] HexString2Bytes(String src, String charset) {
        int len = src.length() / 2;
        byte[] ret = new byte[len];

        try {
            byte[] tmp = src.getBytes(charset);

            for(int i = 0; i < len; ++i) {
                ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
            }
        } catch (UnsupportedEncodingException var6) {
            log.error(var6.getMessage());
        }

        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        _b0 = (byte)(_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }

    public static String genRandomStr(int pwd_len) {
        boolean maxNum = true;
        int count = 0;
        char[] str = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();

        while(count < pwd_len) {
            int i = Math.abs(r.nextInt(62));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                ++count;
            }
        }

        return pwd.toString();
    }

    public static String genRandomForNumStr(int pwd_len) {
        int count = 0;
        char[] str = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();

        while(count < pwd_len) {
            int i = Math.abs(r.nextInt(36));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                ++count;
            }
        }

        return pwd.toString();
    }

    public static String getStringText(String str) {
        return str == null ? "" : str;
    }

    public static String substring(String orignal, int idx, int count) {
        if (orignal != null && !"".equals(orignal)) {
            int pos = 0;

            try {
                if (count > 0 && count <= orignal.getBytes("GBK").length) {
                    StringBuffer buff = new StringBuffer();

                    for(int i = 0; i < count; ++i) {
                        ++pos;
                        char c = orignal.charAt(i);
                        if (pos > idx) {
                            buff.append(c);
                        }

                        if (isChineseChar(c)) {
                            --count;
                            ++pos;
                        }
                    }

                    return buff.toString();
                }
            } catch (UnsupportedEncodingException var7) {
                log.error("字符串截取错误[{}]", var7.getMessage());
            }
        }

        return orignal;
    }

    public static boolean isChineseChar(char c) throws UnsupportedEncodingException {
        return String.valueOf(c).getBytes().length > 1;
    }

    public static String delHTMLTag(String htmlStr) {
        if (isNotBlank(htmlStr)) {
            String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
            String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile(regEx_script, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            return htmlStr.trim();
        } else {
            return htmlStr;
        }
    }

    public static boolean hasBlank(String... strs) {
        String[] var1 = strs;
        int var2 = strs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String str = var1[var3];
            if (isBlank(str)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasEmpty(String... strs) {
        String[] var1 = strs;
        int var2 = strs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String str = var1[var3];
            if (isEmpty(str)) {
                return true;
            }
        }

        return false;
    }

    public static boolean collectionIn(String str, String... strList) {
        String[] var2 = strList;
        int var3 = strList.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String _str = var2[var4];
            if (equals(str, _str)) {
                return true;
            }
        }

        return false;
    }

    public static boolean collectionIn(String str, List<String> strList) {
        Iterator<String> var2 = strList.iterator();

        String _str;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            _str = (String)var2.next();
        } while(!equals(str, _str));

        return true;
    }

    public static String getFirstNotBlank(String... strs) {
        int var2 = strs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String str = strs[var3];
            if (isNotBlank(str)) {
                return str;
            }
        }

        return null;
    }

    public static Integer getIntegerValue(CharSequence cs) {
        return isNumeric(cs) ? Integer.getInteger(cs.toString()) : null;
    }

    public static boolean checkMobile(String mobile) {
        return checkMobile(mobile, "1[0-9]{10}");
    }

    public static boolean checkMobile(String mobile, String regex) {
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean equalsAny(String obj, String... objs) {
        int var3 = objs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String obj1 = objs[var4];
            if (equals(obj, obj1)) {
                return true;
            }
        }

        return false;
    }

    public static String toString(Object value) {
        String strValue;
        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String)value;
        } else if (value instanceof BigDecimal) {
            strValue = ((BigDecimal)value).toString();
        } else if (value instanceof Integer) {
            strValue = ((Integer)value).toString();
        } else if (value instanceof Long) {
            strValue = ((Long)value).toString();
        } else if (value instanceof Float) {
            strValue = ((Float)value).toString();
        } else if (value instanceof Double) {
            strValue = ((Double)value).toString();
        } else if (value instanceof Boolean) {
            strValue = ((Boolean)value).toString();
        } else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
            strValue = format.format((Date)value);
        } else {
            strValue = value.toString();
        }

        return strValue;
    }
}
