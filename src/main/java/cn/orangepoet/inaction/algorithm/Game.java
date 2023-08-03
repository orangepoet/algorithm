package cn.orangepoet.inaction.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * 博弈算法 （如棋类等）
 */
public class Game {
    /**
     * 
     * @param node  博弈树
     * @param alpha 我方期望的最大值
     * @param beta  对方期望的最小值
     * @return 我方的最大值
     */
    public int alphaBetaNegMax(Node node, long alpha, long beta) {
        if (node.children == null || node.children.isEmpty()) {
            return node.val;
        }
        int bestValue = Integer.MIN_VALUE;
        for (Node child : node.children) {
            int value = -1 * alphaBetaNegMax(child, -1 * beta, -1 * Math.max(alpha, bestValue));
            bestValue = Math.max(value, bestValue);

            if (bestValue >= beta) {
                break;
            }
        }
        return bestValue;
    }

    public int negMax(Node node) {
        if (node.children == null || node.children.isEmpty()) {
            return node.val;
        }
        int bestValue = Integer.MIN_VALUE;
        for (Node child : node.children) {
            int value = -1 * negMax(child);
            bestValue = Math.max(value, bestValue);
        }
        System.out.println("for children: " + node.getChildValue() + "->" + bestValue);
        node.val = bestValue;
        return bestValue;
    }

    public static void main(String[] args) {
        var terminals = new int[] { 15, 58, 81, 74, 88, 15, 20, 92, 9, 62, 82, 92, 54, 17, 63, 60 };
        Node[] nodes = new Node[terminals.length];
        for (int i = 0; i < terminals.length; i++) {
            nodes[i] = new Node(terminals[i]);
        }
        while (nodes.length > 1) {
            Node[] parent = new Node[nodes.length / 2];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = new Node(nodes[i * 2], nodes[i * 2 + 1]);
            }
            nodes = parent;
        }

        int bestValue = new Game().alphaBetaNegMax(nodes[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
        // int bestValue = new Game().negMax(nodes[0]);
        System.out.println(bestValue);
    }
}

class Node {
    int val;
    List<Node> children;

    Node(Node... nodes) {
        this.children = Arrays.asList(nodes);
    }

    Node(int val) {
        this.val = val;
    }

    String getChildValue() {
        StringBuilder sb = new StringBuilder();
        for (Node node : children) {
            sb.append(-1 * node.val).append(",");
        }
        return sb.toString();
    }
}
