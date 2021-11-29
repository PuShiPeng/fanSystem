package com.pu.fansystem.utils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 链表类型Util
 */
public class LinkListUtil {

     class ListNode {
         int val;
         ListNode next;
         ListNode(int x) {
             val = x;
             next = null;
         }
     }

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
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null)return null;
        Deque<ListNode> dequeA = new LinkedList<>();
        Deque<ListNode> dequeB = new LinkedList<>();
        ListNode tmpA = headA;
        while(tmpA != null){
            dequeA.push(tmpA);
            tmpA = tmpA.next;
        }
        ListNode tmpB = headB;
        while(tmpB != null){
            dequeB.push(tmpB);
            tmpB = tmpB.next;
        }
        tmpA = dequeA.pop();
        tmpB = dequeB.pop();
        if(tmpA != tmpB)return null;
        ListNode res = tmpA;
        while(tmpA == tmpB && (dequeA !=null || dequeB != null)){
            res = tmpA;
            tmpA = dequeA.pop();
            tmpB = dequeB.pop();
        }
        return res;
    }
}
