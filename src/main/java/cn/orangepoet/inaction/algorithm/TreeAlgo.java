package cn.orangepoet.inaction.algorithm;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 树的操作, 左子树的值都小于右子树
 *
 * @author Orange
 * @date 16/8/27.
 */
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

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        /**
         * [1,2,3,4,5,6,7]
         *
         * @param elements
         * @return
         */
        public static Node parse(Integer[] elements) {
            if (elements == null || elements.length == 0) {
                return null;
            }
            Node root = null;
            int maxDeep = (int) (Math.log(elements.length + 1) / Math.log(2));
            for (int deep = 1; deep <= maxDeep; deep++) {
                Node current;
                int index = (int) Math.pow(2, deep - 1);
                if (deep != maxDeep) {
                    Node left = null;
                    Node right = null;
                    if (elements[index * 2 + 1] != null) {
                        left = new Node(elements[index * 2 + 1]);
                    }
                    if (elements[index * 2 + 2] != null) {
                        right = new Node(elements[index * 2 + 2]);
                    }
                    current = new Node(elements[index], left, right);
                } else {
                    current = new Node(elements[index]);
                }

                if (deep == 1) {
                    root = current;
                }
            }
            return root;
        }
    }

    /**
     * 堆是一种完全二叉树, 是由满二叉树衍生而来
     */
    public static class Heap {
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

        private int findMin() {
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
                for (int i = 0; i < element.length; i++) {
                    copy[i] = element[i];
                }
                element = copy;
            }
        }

        public boolean isEmpty() {
            return size() < 2;
        }

        public static void main(String[] args) {
            int[] arr = new int[]{150, 80, 40, 20, 10, 50, 110, 100, 30, 90, 60, 70, 120, 140, 130};
            Heap heap = new Heap(arr);
            printHeap(heap);
            System.out.println(String.format("min element: %d", heap.findMin()));
        }

        private static void printHeap(Heap heap) {
            if (heap.isEmpty()) {
                return;
            }
            System.out.println("------------------------------");
            for (int i = 1; i < heap.size(); i++) {
                if (heap.get(i) == 0) {
                    break;
                }

                int step = (int) Math.floor(Math.log(i) / Math.log(2));
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < step; j++) {
                    sb.append("-");
                }

                System.out.println(String.format("%s[%d]", sb.toString(), heap.get(i)));
            }
            System.out.println();
        }
    }

    /**
     * 二分查找, 小于走左, 大于走右
     *
     * @param node
     * @param x
     * @return
     */
    public static boolean contain(Node node, int x) {
        if (node == null) {
            return false;
        }

        Node t = node;
        while (t != null) {
            if (x < t.element) {
                t = t.left;
            } else if (x > t.element) {
                t = t.right;
            } else {
                return true;
            }
        }
        return false;
    }

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
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
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
        if (node == null)
        //            return new BinaryNode(x, null, null);
        {
            throw new NullPointerException("node");
        }

        Node t = node;

        while (t != null) {
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
     * 深度优先遍历, 使用栈实现
     *
     * @param node
     */
    public static void dfs(Node node) {
        Stack<Node> stack = new Stack<Node>();

        stack.push(node);
        while (!stack.empty()) {
            Node node1 = stack.pop();
            System.out.println(node1.element);

            if (node1.right != null) {
                stack.push(node1.right);
            }
            if (node1.left != null) {
                stack.push(node1.left);
            }
        }

        BigDecimal x = new BigDecimal("");
    }

    /**
     * 先序遍历
     *
     * @param node
     */
    public static void parentFirst(Node node) {
        if (node != null) {
            System.out.println(node.element);
            parentFirst(node.left);
            parentFirst(node.right);
        }
    }

    /**
     * 后序遍历, 先子树后根节点
     *
     * @param node
     */
    public static void ChildrenFirst(Node node) {
        if (node != null) {
            ChildrenFirst(node.left);
            ChildrenFirst(node.right);
            System.out.println(node.element);
        }
    }

    /**
     * 树的序列化
     *
     * @param root
     * @return
     */
    public String serialize(Node root) {
        if (root == null) {
            return "";
        }
        Queue<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);

        Node emptyNode = new Node(-1);

        List<Integer> values = new ArrayList<>();
        while (nodeQueue.peek() != null) {
            Node node = nodeQueue.poll();
            values.add(node.element);

            if (node.left != null) {
                nodeQueue.add(node.left);
            } else if (node != emptyNode) {
                nodeQueue.add(emptyNode);
            }

            if (node.right != null) {
                nodeQueue.add(node.right);
            } else if (node != emptyNode) {
                nodeQueue.add(emptyNode);
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i == 0) {
                result.append(values.get(i));
            } else {
                result.append("_").append(values.get(i));
            }
        }
        return result.toString();
    }

    /**
     * 树的反序列化
     *
     * @param data
     * @return
     */
    public Node deserialize(String data) {
        String[] parts = data.split("_");
        Map<Integer, Node> treeMap = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            int val = Integer.parseInt(parts[i]);
            if (val == -1) {
                continue;
            }
            Node node = new Node(val);
            treeMap.put(i, node);
            if (i > 0) {
                int pIndex = (i + 1) / 2 - 1;
                boolean mod = (i + 1) % 2 == 0;
                if (mod) {
                    treeMap.get(pIndex).left = node;
                } else {
                    treeMap.get(pIndex).right = node;
                }
            }
        }
        return treeMap.get(0);
    }
}
