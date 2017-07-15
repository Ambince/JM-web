package ssoft.utils;

import java.util.UUID;

/**
 * Created by 杨柳 on 2016/8/5 0005.
 * email:yangliu@buestc.com
 * tel:18523437817
 */
public class StringUtils {

    public static boolean isEmpty(String target) {
        if (target == null) {
            return true;
        }
        return "".equals(target);
    }

    public static boolean isNotEmpty(String target) {
        return !(target == null || "".equals(target));
    }

    public static String hide(String target, int preRemain, int sufRemain) {
        int length = target.length();
        if (length < preRemain) {
            throw new RuntimeException("源字符串长度不能小于开始不隐藏的长度");
        }
        if (length < sufRemain) {
            throw new RuntimeException("源字符串长度不能小于最后不隐藏的长度");
        }
        char[] source = target.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (i >= preRemain && i < (source.length - sufRemain)) {
                source[i] = '*';
            }
        }
        return new String(source);
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    public static String ascii2native(String asciicode) {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];
        try {
            for (int i = 1; i < asciis.length; i++) {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
                if (code.length() > 4) {
                    nativeValue += code.substring(4, code.length());
                }
            }
        } catch (NumberFormatException e) {
            return asciicode;
        }
        return nativeValue;
    }


}
