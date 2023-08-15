package cn.orangepoet.inaction.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * 通过数组组装树
     *
     * @param arr tree的数组
     * @return 返回根节点
     */
    public static TreeNode create(Integer[] arr) {
        int idx = 0;
        TreeNode root = new TreeNode(arr[idx], null, null);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                if (idx >= arr.length - 1) {
                    return root;
                }
                TreeNode tn = queue.poll();
                assert tn != null;
                if (idx + 1 < arr.length && arr[++idx] != null) {
                    tn.left = new TreeNode(arr[idx], null, null);
                    queue.offer(tn.left);
                }
                if (idx + 1 < arr.length && arr[++idx] != null) {
                    tn.right = new TreeNode(arr[idx], null, null);
                    queue.offer(tn.right);
                }
            }
        }
        return root;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TreeNode node2) {
            return this.val == node2.val
                    && Objects.equals(this.left, node2.left)
                    && Objects.equals(this.right, node2.right);
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    /**
     * 广度遍历
     *
     * @return
     */
    private List<Integer> toList() {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(this);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node == null ? null : node.val);
            if (node != null) {
                if (node.left != null || node.right != null) {
                    queue.add(node.left);
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
            }
        }
        return list;
    }
}
