package com.elichn.pub.core.util;

/**
 * <p>Title: RandCharUtil</p>
 * <p>Description: 生成随机字符工具类</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class RandCharUtil {

    /**
     * getRandChar 生成随机字符
     *
     * @return String
     */
    public static String getRandChar() {
        int rand = (int) Math.round(Math.random() * 2);
        // int rand = 0;
        long itmp = 0;
        char ctmp = '\u0000';
        // 根据rand的值来决定来生成一个大写字母、小写字母和数字
        switch (rand) {
            // 生成大写字母
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成小写字母
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成数字
            default:
                itmp = Math.round(Math.random() * 7) + 2;
                return String.valueOf(itmp);
        }
    }

    /**
     * getRandStr 获取指定位数的随机字符串
     *
     * @param size 位数
     * @return String
     */
    public static String getRandStr(int size) {
        if (size <= 0) {
            size = 6;
        }
        String randStr = "";
        for (int i = 0; i < size; i++) {
            // 获得一个随机字符
            String tmp = RandCharUtil.getRandChar();
            randStr += tmp;
        }
        return randStr;
    }
}
