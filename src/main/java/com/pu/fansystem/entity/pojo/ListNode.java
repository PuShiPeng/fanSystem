package com.pu.fansystem.entity.pojo;

public class ListNode {
    int val;
    public ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
