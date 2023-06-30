package cn.orangepoet.inaction.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 基于最近使用频次淘汰算法
 *
 * @author chengzhi
 * @date 2022/06/27
 */
public class LFU {
    static class Node {
        int key;
        int value;
        long frequency;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }

        public static Node copy(Node copy, long frequency) {
            Node node = new Node(copy.key, copy.value);
            node.frequency = frequency;
            return node;
        }
    }

    private final PriorityQueue<Node> pq;

    private final Map<Integer, Node> map;
    private final int capacity;

    public LFU(int capacity) {
        this.map = new HashMap<>(capacity);
        this.pq = new PriorityQueue<>(Comparator.comparingLong(n -> n.frequency));
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        updateAccessFrq(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            if (map.size() >= capacity && !pq.isEmpty()) {
                Node rm = pq.poll();
                map.remove(rm.key);
            }

            node = new Node(key, value);
            map.put(key, node);
            pq.offer(node);
        } else {
            node.value = value;
            node.frequency++;

            updateAccessFrq(node);
        }
    }

    private void updateAccessFrq(Node node) {
        pq.remove(node);
        Node copy = Node.copy(node, node.frequency + 1);
        pq.offer(copy);
        map.put(node.key, copy);
    }
}