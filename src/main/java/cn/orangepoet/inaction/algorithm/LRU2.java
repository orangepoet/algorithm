package cn.orangepoet.inaction.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用双向链表和Map实现LRU
 *
 * @author chengzhi
 * @date 2022/06/05
 */
public class LRU2 {
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, Node> nodeMap;
    private final int capacity;

    private final Node head = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final Node tail = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public LRU2(int capacity) {
        nodeMap = new HashMap<>(capacity);
        this.capacity = capacity;
        this.head.next = tail;
        this.tail.prev = head;
    }

    public int get(int key) {
        Node node = nodeMap.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (nodeMap.containsKey(key)) {
            // case1: key exist, update value, and move to head
            Node node = nodeMap.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node node = new Node(key, value);
            nodeMap.put(key, node);
            // case2: key not exists, add node to head, (k,v)
            addToHead(node);
            if (nodeMap.size() > capacity) {
                Node r = this.tail.prev;
                remove(r);
                nodeMap.remove(r.key);
            }
        }
    }

    private void addToHead(Node node) {
        node.next = head.next;
        head.next.prev = node;

        node.prev = head;
        head.next = node;
    }

    private void moveToHead(Node node) {
        remove(node);
        addToHead(node);
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = null;
        node.prev = null;
    }
}
