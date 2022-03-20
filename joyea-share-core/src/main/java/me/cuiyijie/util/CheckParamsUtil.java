package me.cuiyijie.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckParamsUtil {
    public CheckParamsUtil() {
    }

    public static String checkAll(Object obj, List<String> paramsBlank, Map<String, ArrayList<String>> paramsEnum, Map<String, Integer> paramsLength) {
        String errMsg = null;
        if (errMsg == null && paramsBlank != null) {
            errMsg = checkBlank(obj, paramsBlank);
        }

        if (errMsg == null && paramsEnum != null) {
            errMsg = checkEnum(obj, paramsEnum);
        }

        if (errMsg == null && paramsLength != null) {
            errMsg = checkLength(obj, paramsLength);
        }

        return errMsg;
    }

    public static String checkMaxMin(Object obj, Map<String, String> paramsMaxMin) {
        Class<? extends Object> clazz = obj.getClass();
        Iterator it = paramsMaxMin.keySet().iterator();

        String mtdName;
        String errKey;
        String valRange;
        boolean isMatch;
        label109:
        do {
            while (it.hasNext()) {
                String param = (String) it.next();
                mtdName = param.split(":")[0];
                errKey = null;
                if (param.split(":").length > 1) {
                    errKey = param.split(":")[1];
                }

                valRange = ((String) paramsMaxMin.get(param)).replaceAll(" ", "");
                isMatch = false;
                char lessSymbol = valRange.charAt(0);
                char greaterSymbol = valRange.charAt(valRange.length() - 1);
                String[] range = valRange.split(",");
                String minStr = range[0].substring(1);
                String maxStr = range[1].substring(0, range[1].length() - 1);

                try {
                    Method m = clazz.getMethod("get" + MethodNameUtil.getMethodName(mtdName));
                    Object val = m.invoke(obj);
                    if (val != null) {
                        double min = Double.parseDouble(minStr);
                        double max = Double.parseDouble(maxStr);
                        if (!m.getReturnType().getName().equals("int") && !Integer.class.isAssignableFrom(m.getReturnType()) && !m.getReturnType().getName().equals("double") && !Double.class.isAssignableFrom(m.getReturnType()) && !m.getReturnType().getName().equals("float") && !Float.class.isAssignableFrom(m.getReturnType())) {
                            isMatch = true;
                            continue label109;
                        }

                        if (isMatch && lessSymbol == '(') {
                            isMatch = Double.valueOf(val.toString()) > min;
                        } else if (isMatch && lessSymbol == '[') {
                            isMatch = Double.valueOf(val.toString()) >= min;
                        }

                        if (isMatch && greaterSymbol == ')') {
                            isMatch = Double.valueOf(val.toString()) < max;
                            continue label109;
                        }

                        if (isMatch && greaterSymbol == ']') {
                            isMatch = Double.valueOf(val.toString()) <= max;
                        }
                        continue label109;
                    }
                } catch (SecurityException var20) {
                    var20.printStackTrace();
                    continue label109;
                } catch (NoSuchMethodException var21) {
                    var21.printStackTrace();
                    continue label109;
                } catch (IllegalArgumentException var22) {
                    var22.printStackTrace();
                    continue label109;
                } catch (IllegalAccessException var23) {
                    var23.printStackTrace();
                    continue label109;
                } catch (InvocationTargetException var24) {
                    var24.printStackTrace();
                    continue label109;
                }
            }

            return null;
        } while (isMatch);

        return StringUtils.isBlank(errKey) ? mtdName + " range is " + valRange + "!" : errKey + " 的必须" + valRange.replace("(", "大于").replace("[", "大于等于").replace(")", "小于").replace("]", "小于等于").replace(",", "且");
    }

    public static String checkLength(Object obj, Map<String, Integer> paramsLength) {
        Class<? extends Object> clazz = obj.getClass();
        Iterator it = paramsLength.keySet().iterator();

        String mtdName;
        String errKey;
        Integer length;
        boolean isGreater;
        label77:
        do {
            while (it.hasNext()) {
                String param = (String) it.next();
                mtdName = param.split(":")[0];
                errKey = null;
                if (param.split(":").length > 1) {
                    errKey = param.split(":")[1];
                }

                length = (Integer) paramsLength.get(param);
                isGreater = true;

                try {
                    Method m = clazz.getMethod("get" + MethodNameUtil.getMethodName(mtdName));
                    Object val = m.invoke(obj);
                    if (val != null) {
                        if (m.getReturnType() == String.class) {
                            isGreater = ((String) val).length() > length;
                        } else if (Object.class.isAssignableFrom(m.getReturnType())) {
                            try {
                                Object rltObj = val == null;
                                Method mLength = m.getReturnType().getMethod("size", Integer.class);
                                isGreater = (Integer) mLength.invoke(rltObj) > length;
                            } catch (Exception var13) {
                                isGreater = false;
                                var13.printStackTrace();
                            }
                        } else {
                            isGreater = false;
                        }
                        continue label77;
                    }
                } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException var14) {
                    var14.printStackTrace();
                    continue label77;
                }
            }

            return null;
        } while (!isGreater);

        return StringUtils.isBlank(errKey) ? mtdName + " size need less than " + length + "!" : errKey + " 最多不能超过 " + length + "个字";
    }

    public static String checkEnum(Object obj, Map<String, ArrayList<String>> paramsEnum) {
        Class<? extends Object> clazz = obj.getClass();
        Iterator it = paramsEnum.keySet().iterator();

        String mtdName;
        String errKey;
        ArrayList valList;
        boolean isIn;
        label58:
        do {
            while (it.hasNext()) {
                String param = (String) it.next();
                mtdName = param.split(":")[0];
                errKey = null;
                if (param.split(":").length > 1) {
                    errKey = param.split(":")[1];
                }

                valList = (ArrayList) paramsEnum.get(param);
                isIn = false;

                try {
                    Method m = clazz.getMethod("get" + MethodNameUtil.getMethodName(mtdName));
                    Object val = m.invoke(obj);
                    if (val != null) {
                        if (m.getReturnType() == String.class) {
                            isIn = StringUtils.collectionIn((String) val, valList);
                        } else if (Object.class.isAssignableFrom(m.getReturnType())) {
                            isIn = StringUtils.collectionIn(val.toString(), valList);
                        } else {
                            isIn = StringUtils.collectionIn(String.valueOf(val), valList);
                        }
                        continue label58;
                    }
                } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException var11) {
                    var11.printStackTrace();
                    continue label58;
                }
            }

            return null;
        } while (isIn);

        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = StringUtils.isBlank(errKey) ? mtdName + " value must in " + objectMapper.writeValueAsString(valList) + "!" : errKey + " 只允许以下值 " + objectMapper.writeValueAsString(valList) + "!";
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public static String checkBlank(Object obj, List<String> paramsBlank) {
        Class<? extends Object> clazz = obj.getClass();
        Iterator var3 = paramsBlank.iterator();

        String mtdName;
        String errKey;
        boolean isBlank;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            String param = (String) var3.next();
            mtdName = param.split(":")[0];
            errKey = null;
            if (param.split(":").length > 1) {
                errKey = param.split(":")[1];
            }

            isBlank = true;

            try {
                Method m = clazz.getMethod("get" + MethodNameUtil.getMethodName(mtdName));
                if (m.getReturnType() == String.class) {
                    isBlank = StringUtils.isBlank((String) m.invoke(obj));
                } else if (List.class.isAssignableFrom(m.getReturnType())) {
                    List<?> list = (List) m.invoke(obj);
                    if (list != null) {
                        isBlank = list.isEmpty();
                    } else {
                        isBlank = true;
                    }
                } else if (Object.class.isAssignableFrom(m.getReturnType())) {
                    isBlank = m.invoke(obj) == null;
                } else {
                    isBlank = false;
                }
            } catch (SecurityException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException var10) {
                var10.printStackTrace();
            }
        } while (!isBlank);

        return StringUtils.isBlank(errKey) ? mtdName + " is required!" : errKey + " 不能为空";
    }
}
