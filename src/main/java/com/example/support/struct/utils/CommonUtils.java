package com.example.support.struct.utils;

/**
 * Created by xuding on 2018/5/31.
 */

public class CommonUtils {

    /**
     * 取某一位的bit值
     *
     * @param number
     * @param i      从0开始，右边最低位是第0位
     * @return
     */
    public static int getBit(int number, int i) {
        return number >> i & 0x01;
    }

    /**
     * 设置number的第i位为1
     *
     * @param number 整数
     * @param i      第i位，从0开始
     * @return 修改后的值
     */
    public static int setBit1(int number, int i) {
        if (i < 0) {
            throw new RuntimeException("弄啥勒");
        }
        return number | (1 << i);
    }

    /**
     * 设置number的第i位为0
     *
     * @param number 整数
     * @param i      第i位，从0开始
     * @return 修改后的值
     */
    public static int setBit0(int number, int i) {
        if (i < 0) {
            throw new RuntimeException("弄啥勒");
        }
        return number & ~(1 << i);
    }

    /**
     * byte[]转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String toHexString(byte... bytes) {
        String byteString = "";
        for (byte b : bytes) {
            byteString += Integer.toHexString(b & 0xFF).toUpperCase() + " ";
        }
        return byteString;
    }

    /**
     * 返回无符号数字
     */
    public static int unsigned(byte _input) {
        if (_input < 0) {
            return (_input + 256);
        } else {
            return _input;
        }
    }
}
