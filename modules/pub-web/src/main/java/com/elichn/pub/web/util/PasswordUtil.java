package com.elichn.pub.web.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.regex.Pattern;

/**
 * <p>Title: PasswordUtil</p>
 * <p>Description: 密码工具类</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class PasswordUtil {

    private static char[] NUM = {
            '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static char[] LOWER = {
            'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static char[] UPPER = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static char[] OTHER = {
            '.', ',', '?', '\\', '/', '+', '-', '%', '\'', '!', '@', '#', '$', '^', '&', '*', '(', ')', '[', ']'
    };

    private static Pattern numberCheck = Pattern.compile("\\d");
    private static Pattern lowerCaseCheck = Pattern.compile("[a-z]");
    private static Pattern upperCaseCheck = Pattern.compile("[A-Z]");
    private static Pattern otherCaseCheck = Pattern.compile("\\W");

    /**
     * randomPassword  randomPassword
     *
     * @param length length
     * @return String
     */
    public static String randomPassword(int length) {
        while (true) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int m = RandomUtils.nextInt() % 4;
                char[] arr = null;
                switch (m) {
                    case 0:
                        arr = NUM;
                        break;
                    case 1:
                        arr = LOWER;
                        break;
                    case 2:
                        arr = UPPER;
                        break;
                    case 3:
                        arr = OTHER;
                        break;
                    default:
                        arr = NUM;
                        break;
                }
                buf.append(arr[(int) Math.floor(Math.random() * arr.length)]);
            }
            String password = buf.toString();
            if (isComplexPassword(password) >= 3) {
                return password;
            }
        }
    }

    /**
     * randomPassword  randomPassword
     *
     * @return String
     */
    public static String randomPassword() {
        return randomPassword(6);
    }

    /**
     * isComplexPassword isComplexPassword
     *
     * @param password password
     * @return int
     */
    public static int isComplexPassword(String password) {
        int length = 6;
        if (StringUtils.isBlank(password) || password.length() < length) {
            return 0;
        }
        int n = 0;
        // 包含数字
        if (numberCheck.matcher(password).find()) {
            n++;
        }
        // 包含小写字母
        if (lowerCaseCheck.matcher(password).find()) {
            n++;
        }
        // 包含大写字母
        if (upperCaseCheck.matcher(password).find()) {
            n++;
        }
        // 包含其他字符
        if (otherCaseCheck.matcher(password).find()) {
            n++;
        }
        return n;
    }
}
