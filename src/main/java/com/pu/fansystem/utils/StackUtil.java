package com.pu.fansystem.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 栈类型Util
 */
public class StackUtil {
    /**
     * 持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈
     */
    public class MinStack {
        private List<Integer> stackList;
        private int pro;
        private List<Integer> minList;
        public MinStack() {
            stackList = new ArrayList<>();
            minList = new ArrayList<>();
        }

        public void push(int val) {
            stackList.add(val);
            if(pro == 0)
                minList.add(val);
            else
                minList.add(val < minList.get(pro - 1) ? val : minList.get(pro - 1));
            pro++;
        }

        public void pop() {
            if(pro == 0)return;
            minList.remove(pro - 1);
            stackList.remove(pro - 1);
            pro--;
        }

        public int top() {
            return stackList.get(pro - 1);
        }

        public int getMin() {
            return minList.get(pro - 1);
        }

    }
}

