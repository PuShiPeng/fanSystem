package com.pu.fansystem.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本类型Util
 */
public class StringUtil {
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
}
