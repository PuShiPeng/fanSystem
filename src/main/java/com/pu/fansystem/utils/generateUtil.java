package com.pu.fansystem.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角Util
 */
public class generateUtil {
    /**
     * 杨辉三角
     */
    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) return new ArrayList<>();
        List<List<Integer>> generateList = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        generateList.add(tmp);
        for (int i = 0; i < numRows - 1; i++) {
            List<Integer> childList = new ArrayList<>();
            childList.add(1);
            int size = generateList.get(i).size();
            for (int j = 1; j < size; j++) {
                childList.add(generateList.get(i).get(j) + generateList.get(i).get(j - 1));
            }
            childList.add(1);
            generateList.add(childList);
        }
        return generateList;
    }
}
