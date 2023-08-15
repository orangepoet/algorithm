package cn.orangepoet.inaction.algorithm;

import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;


public class TreeAlgo {
    @Getter
    static class Node {
        Node left;
        Node right;
        Integer element;

        public Node(Integer element) {
            this.element = element;
        }

        public Node(int x, Node left, Node right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }

    }

    /**
     * 堆是一种完全二叉树, 是由满二叉树衍生而来
     */
    static class Heap {
        private int[] element;
        private int current = -1;

        public int get(int index) {
            if (isEmpty()) {
                return -1;
            }

            if (index > element.length - 1) {
                throw new IndexOutOfBoundsException("index");
            }

            return element[index];
        }

        public int size() {
            return this.element.length;
        }

        /**
         * 把输入的无序数组转为堆
         *
         * @param element
         */
        public Heap(int[] element) {
            if (element == null || element.length == 0) {
                throw new IllegalArgumentException("element");
            }

            this.element = new int[element.length];
            this.element[++current] = 0;

            for (int i : element) {
                insert(i);
            }
        }

        public int findMin() {
            if (isEmpty()) {
                return -1;
            }
            return element[1];
        }

        /**
         * 最小堆生成 (parent<children) 对每个新元素, 先放入到最后位置, 比较它和父元素大小, 如果小于, 交换它和父元素的位置, 递归下去;
         *
         * @param newItem
         */
        private void insert(int newItem) {
            ensureForAdd();
            int position = ++current;
            int parent;
            // position>1 说明不是堆顶元素
            for (; position > 1 && newItem < (parent = element[position / 2]); position /= 2) {
                // 当父元素 > 新插元素, 父元素向下移动
                element[position] = parent;
            }
            // 当父元素下沉后, 剩余的位置就是新元素插入位置;
            element[position] = newItem;
        }

        private void ensureForAdd() {
            if (current == element.length - 1) {
                int[] copy = new int[element.length * 2];
                System.arraycopy(element, 0, copy, 0, element.length);
                element = copy;
            }
        }

        public boolean isEmpty() {
            return size() < 2;
        }

        @Override
        public String toString() {
            if (this.isEmpty()) {
                return "";
            }
            StringBuilder s = new StringBuilder();
            s.append("------------------------------");
            s.append(System.lineSeparator());
            for (int i = 1; i < this.size(); i++) {
                if (this.get(i) == 0) {
                    break;
                }

                int step = (int) Math.floor(Math.log(i) / Math.log(2));
                s.append("-".repeat(step));
                s.append(this.get(i));
                s.append(System.lineSeparator());
            }
            return s.toString();
        }
    }

    /**
     * 树的操作, 左子树的值都小于右子树
     */
    static class SearchTree {
        /**
         * 递归查找到x所在的子树, 如果就是当前节点 ->
         * <p>
         * 1. 从右子树选一个最小的作为 root
         * </p>
         * <p>
         * 2. 从右子树中 删除1中的root节点, 递归下去 ->1
         * </p>
         *
         * @param node
         * @param x
         * @return
         */
        public static Node remove(Node node, int x) {
            if (node == null) {
                return node;
            }

            if (x < node.element) {
                node.left = remove(node.left, x);
            } else if (x > node.element) {
                node.right = remove(node.right, x);
            } else if (node.left != null && node.right != null) {
                node.element = findMin(node.right).element;
                node.right = remove(node.right, node.element);
            } else {
                node = (node.left != null) ? node.left : node.right;
            }
            return node;
        }

        public static Node findMin(Node node) {
            while (node != null && node.left != null) {
                node = node.left;
            }
            return node;
        }

        public static Node add(int x, Node node) {
            if (node == null) {
                return new Node(x, null, null);
            }

            if (x < node.element) {
                node.left = add(x, node.left);
            } else if (x > node.element) {
                node.right = add(x, node.right);
            } else {
                // do nothing, duplicate
            }
            return node;
        }

        public static void add0(int x, Node node) {
            if (node == null) {
                throw new NullPointerException("node");
            }

            Node t = node;

            while (true) {
                if (x < t.element) {
                    if (t.left != null) {
                        t = t.left;
                    } else {
                        t.left = new Node(x, null, null);
                        break;
                    }
                } else if (x > t.element) {
                    if (t.right != null) {
                        t = t.right;
                    } else {
                        t.right = new Node(x, null, null);
                        break;
                    }
                } else {
                    // do nothing, duplicate
                    break;
                }
            }
        }
    }


    /**
     * 广度优先遍历, 使用队列实现
     *
     * @param node
     */
    public static List<Integer> wideFirst(Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<>();

        Queue<Node> queue = new ArrayBlockingQueue<Node>(10);
        queue.add(node);
        while (!queue.isEmpty()) {
            Node node1 = queue.poll();
            ret.add(node1.getElement());

            if (node1.left != null) {
                queue.add(node1.left);
            }
            if (node1.right != null) {
                queue.add(node1.right);
            }
        }
        return ret;
    }

    /**
     * 先序遍历, 先根后子
     *
     * @param node
     */
    public static List<Integer> preOrder(Node node) {
        Deque<Node> stack = new LinkedList<>();

        stack.push(node);
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            Node node1 = stack.pop();
            result.add(node1.element);

            if (node1.right != null) {
                stack.push(node1.right);
            }
            if (node1.left != null) {
                stack.push(node1.left);
            }
        }
        return result;
    }

    /**
     * 先序遍历, 先根后子
     *
     * @param node
     */
    public static void preOrder2(Node node, List<Integer> list) {
        if (node != null) {
            list.add(node.element);
            preOrder2(node.left, list);
            preOrder2(node.right, list);
        }
    }

    /**
     * 中序遍历, 左 -> 中 -> 右
     *
     * @param node
     * @return
     */
    public static List<Integer> inorder(Node node) {
        List<Integer> list = new ArrayList<>();

        Deque<Node> stack = new ArrayDeque<>();

        Node n = node;
        while (n != null || !stack.isEmpty()) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }

            n = stack.pop();
            list.add(n.element);
            n = n.right;
        }
        return list;
    }

    public static void inorder2(Node node, List<Integer> list) {
        if (node != null) {
            inorder2(node.left, list);
            list.add(node.element);
            inorder2(node.right, list);
        }
    }

    /**
     * 后序遍历, 先子树后根节点
     *
     * @param node
     * @return
     */
    public static List<Integer> postOrder(Node node) {
        List<Integer> result = new ArrayList<>();
        if (node == null) {
            return result;
        }

        Stack<Node> stack = new Stack<>();
        Node current = node;
        Node lastVisited = null;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                Node peekNode = stack.peek();
                if (peekNode.right != null && peekNode.right != lastVisited) {
                    current = peekNode.right;
                } else {
                    result.add(peekNode.element);
                    lastVisited = stack.pop();
                }
            }
        }
        return result;
    }

    /**
     * 后序遍历, 先子树后根节点
     *
     * @param node
     */
    public static void postOrder2(Node node, List<Integer> list) {
        if (node != null) {
            postOrder2(node.left, list);
            postOrder2(node.right, list);
            list.add(node.element);
        }
    }
}
