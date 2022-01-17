package com.pu.fansystem.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本类型Util
 */
public class BasicTypeUtil {
    /**
     * 给出一个列序号。返回它在 Excel 表中相对应的列名称
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder title = new StringBuilder();
        while (columnNumber > 0){
            int a0 = (columnNumber - 1) % 26 + 1;
            title.append((char)(a0 - 1 + 'A'));
            columnNumber = (columnNumber - a0) / 26;
        }
        return title.reverse().toString();
    }

    /**
     * 给出一个 Excel 表格中的列名称。返回该列名称对应的列序号
     */
    public int titleToNumber(String columnTitle) {
        int number = 0,j = 0;
        for(int i = columnTitle.length() - 1; i >= 0; i--,j++){
            number += (columnTitle.charAt(i) - 'A' + 1) * (Math.pow(26,j));
        }
        return number;
    }

    /**
     * 给出一个 罗马数字。返回对应的数值
     */
    public int romanToInt(String s) {
        Map<String,Integer> num = new HashMap<>();
        num.put("I",1);
        num.put("V",5);
        num.put("X",10);
        num.put("L",50);
        num.put("C",100);
        num.put("D",500);
        num.put("M",1000);
        char[] numStr = s.toCharArray();
        int target = 0;
        for(int i = 0; i < numStr.length; i++){
            if(i > 0){
                if("V".equalsIgnoreCase(String.valueOf(numStr[i])) && "I".equalsIgnoreCase(String.valueOf(numStr[i-1])))
                    target += num.get(String.valueOf(numStr[i])) - num.get(String.valueOf(numStr[i-1]));
            }else
                target += num.get(String.valueOf(numStr[i]));

        }
        return target;
    }

    /**
     * 给出两个字符串。找出 haystack 字符串中 needle 字符串出现的第一个位置
     */
    public int strStr(String haystack, String needle) {
        if(null == haystack || null == needle)return -1;
        int size = haystack.length();
        int subSize = needle.length();
        if(size == 0 )return -1;
        if(subSize == 0)return 0;
        int prev = 0;
        int len = 0;
        for(int i =0; i < size; i++){
            if(haystack.charAt(i) == needle.charAt(prev)){
                if(prev == 0)len = i;
                prev++;
                if(prev == subSize)return len;
            }else{
                prev = 0;
            }
        }
        return -1;
    }

    /**
     * 颠倒给定的 32 位无符号整数的二进制位
     */
    /** 方法一：逐位颠倒 **/
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32 && n != 0; ++i){
            res |= (n & 1) << (31 - i);
            n >>>= 1;
        }
        return res;
    }
    /**
     * 方法二：位运算分治
     * &运算：单个二进制码 & 1 = 本身，M1提取奇数位。
     * n >>> 1 & M1 ： 提取偶数位变奇数位
     * (n & M1) << 1 ： 提取奇数位变偶数位
     * 以此类推，最后一层只需要交换位置，不用提取
     */
    private static final int M1 = 0x55555555; // 01010101010101010101010101010101
    private static final int M2 = 0x33333333; // 00110011001100110011001100110011
    private static final int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
    private static final int M8 = 0x00ff00ff; // 00000000111111110000000011111111
    public int reverseBits1(int n) {
        n = n >>> 1 & M1 | (n & M1) << 1;
        n = n >>> 2 & M2 | (n & M2) << 2;
        n = n >>> 4 & M4 | (n & M4) << 4;
        n = n >>> 8 & M8 | (n & M8) << 8;
        return n >>> 16 | n << 16;
    }
}
