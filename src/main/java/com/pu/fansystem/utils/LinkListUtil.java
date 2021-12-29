package com.pu.fansystem.utils;

import com.pu.fansystem.entity.pojo.ListNode;

import java.util.*;

/**
 * 链表类型Util
 */
public class LinkListUtil {

    /**
     * 判断链表中是否有环
     *  快慢指针「Floyd 判圈算法」（又称龟兔赛跑算法）
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast)return true;
        }
        return false;
    }

    /**
     * 两个单链表相交的起始节点。
     * !!!!! 判断链表是否相同，使用哈希集合的contains ！！！！！
     */
    /** 方法一：哈希集合 **/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> nodeHash = new HashSet<>();
        ListNode tmp = headA;
        while (null != tmp){
            nodeHash.add(tmp);
            tmp = tmp.next;
        }
        tmp = headB;
        while (null != tmp){
            if(nodeHash.contains(tmp))return tmp;
            tmp = tmp.next;
        }
        return null;
    }
    /**
     * 方法二：双指针 (时间复杂度：O(m+n),空间复杂度：O(1),最大就是当两个链表不相交)
     *
     * 方法三：双指针衍生方法，将headA和headB对齐，对比剩余的节点。
    **/
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if(headA == null || headB == null)return null;
        ListNode tmpA = headA, tmpB = headB;
        while(tmpA != tmpB){
            tmpA = tmpA == null ? headB : tmpA.next;
            tmpB = tmpB == null ? headA : tmpB.next;
        }
        return tmpA;
    }

}
