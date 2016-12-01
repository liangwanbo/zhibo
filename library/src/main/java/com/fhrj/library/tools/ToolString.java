package com.fhrj.library.tools;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 字符串工具类
 *
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:28:03
 */
@SuppressLint("DefaultLocale")
public class ToolString {
    /***
     * 判断字符串是否是Double
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        try {
            double b = Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 判断字符串是否是数字
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取UUID
     *
     * @return 32UUID小写字符串
     */
    public static String gainUUID() {
        String strUUID = UUID.randomUUID().toString();
        strUUID = strUUID.replaceAll("-", "").toLowerCase();
        return strUUID;
    }

    /**
     * 判断字符串是否非空非null
     *
     * @param strParm 需要判断的字符串
     * @return 真假
     */
    public static boolean isNoBlankAndNoNull(String strParm) {
        return !((strParm == null) || (strParm.equals("")));
    }

    /**
     * 将字符首字母转成大写
     *
     * @param str
     * @return
     */
    public static String capitalFirst(String str) {
        if (TextUtils.isEmpty(str))
            return "";

        if (1 == str.length())
            return str.toUpperCase();

        StringBuilder sb = new StringBuilder();
        String first = str.substring(0, 1);
        sb.append(first.toUpperCase());
        sb.append(str.substring(1, str.length()));
        return sb.toString();
    }

    /**
     * 将流转成字符串
     *
     * @param is 输入流
     * @return
     * @throws Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * 将文件转成字符串
     *
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        // Make sure you close all streams.
        fin.close();
        return ret;
    }

    /**
     * 字符全角化
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /***
     * 获取字符在字符串出现的位置
     *
     * @param string    字符串
     * @param character 字符
     * @param number    第几次出现
     * @return
     */
    public static int getCharacterPosition(String string, String character, int number) {
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile(character).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == number) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /***
     * 随机字符串生成
     *
     * @param length 位数
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomString2(int length) {
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(3);
            long result = 0;

            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }
}
