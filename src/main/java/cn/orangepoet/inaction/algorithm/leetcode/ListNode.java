package cn.orangepoet.inaction.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ListNode other0) {
            if (other0.val != this.val) {
                return false;
            }

            if (this.next == null && other0.next == null) {
                return true;
            } else if (this.next == null || other0.next == null) {
                return false;
            } else {
                return this.next.equals(other0.next);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }

    @Override
    public String toString() {
        return this.val + (this.next != null ? "->" + this.next : "");
    }

    /**
     * 通过数组组装链表
     *
     * @param arr 数组
     */
    public static ListNode create(int[] arr) {
        ListNode root = new ListNode(arr[0]);
        var prev = root;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            prev.next = node;
            prev = node;
        }
        return root;
    }

    /**
     * 链表转数组
     */
    public int[] toIntArr() {
        ListNode next = this;
        List<Integer> list = new ArrayList<>();
        while (next != null) {
            list.add(next.val);
            next = next.next;
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
