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

    /**
     * 给定一个已按照 非递减顺序排列  的整数数组 numbers ，请你从数组中找出两个数满足相加之和等于目标数 target
     */
    /** 方法一：双指针 **/
    public int[] twoSum(int[] numbers, int target) {
        int head = 0, end = numbers.length - 1;
        while(head < end){
            int tmp = numbers[head] + numbers[end];
            if(tmp == target)return new int[]{head + 1, end + 1};
            else if(tmp < target) head++;
            else end--;
        }
        return new int[]{-1,-1};
    }
    /** 方法二：二分查找 **/
    public int[] twoSum2(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; ++i) {
            int low = i + 1, high = numbers.length - 1;
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] > target - numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     */
    /**  Boyer-Moore 投票算法(时间复杂度：N；空间复杂度：1)  **/
    public int majorityElement(int[] nums) {
        if(nums.length == 0)return -1;
        int count = 0;
        int candidate = -1;
        for(int num : nums){
            if(count == 0){
                candidate = num;
            }
            count += (candidate == num) ? 1 : -1;
        }
        return candidate;
    }
    /**  哈希表(时间复杂度：N；空间复杂度：N)  **/
    public int majorityElement1(int[] nums) {
        Map<Integer, Integer> counts = countNums(nums);
        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }
        return majorityEntry.getKey();
    }
    private Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (!counts.containsKey(num)) {
                counts.put(num, 1);
            } else {
                counts.put(num, counts.get(num) + 1);
            }
        }
        return counts;
    }
    /**  排序(时间复杂度：Nlogn；空间复杂度：logn)  **/
    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
