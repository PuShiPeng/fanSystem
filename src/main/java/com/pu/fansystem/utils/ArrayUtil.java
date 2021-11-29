package com.pu.fansystem.utils;

import java.util.*;

/**
 * 数组类型Util
 */
public class ArrayUtil {
    /**
     * 买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        int max = 0, index = 0, min = Integer.MAX_VALUE;
        for(; index < prices.length; index++){
            if (prices[index] < min) {
                min = prices[index];
            } else if (prices[index] - min > max) {
                max = prices[index] - min;
            }
        }
        return max;
    }

    /**
     * 只出现一次的数字
     *  使用异或可找到一个数组中，唯一一个同的元素；在两个数组中，可以找到所有不同元素
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for(int num : nums)
            res ^= num;
        return res;
    }
}
