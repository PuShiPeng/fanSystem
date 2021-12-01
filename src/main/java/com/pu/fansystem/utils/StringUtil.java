package com.pu.fansystem.utils;

/**
 * 基本类型Util
 */
public class StringUtil {
    /**
     * 返回它在 Excel 表中相对应的列名称
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

}
