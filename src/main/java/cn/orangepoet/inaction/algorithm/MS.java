package cn.orangepoet.inaction.algorithm;

import java.util.ArrayList;
import java.util.List;

public class MS {
    static class LongString {

        private Node root;

        public LongString(String s) {
            Node root;
            if (s.length() < 8) {
                root = new Node(s, 0, s.length());
            } else {
                List<Node> nodes = new ArrayList<>();
                int i = 0;
                int partLength = s.length() / 8;
                while (i < s.length()) {
                    int j = i + partLength;
                    j = Math.min(j, s.length() - 1);
                    String s0 = s.substring(i, j + 1);
                    Node node = new Node(s0, i, j);
                    nodes.add(node);
                    i = j + 1;
                }
                root = makeTree(nodes, 0, nodes.size() - 1);
            }

            this.root = root;
        }

        public Character charAt(int index) {
            Node n = root;
            while (n != null) {
                int comp = n.compare(index);
                if (comp == 0) {
                    return n.s.charAt(index - n.start);
                }
                n = comp > 0 ? n.right : n.left;
            }
            return null;
        }

        private Node makeTree(List<Node> nodes, int left, int right) {
            if (nodes.size() == 0 || right < left) {
                return null;
            }
            int mid = (left + right) >> 1;
            Node root = nodes.get(mid);
            root.left = makeTree(nodes, 0, mid - 1);
            root.right = makeTree(nodes, mid + 1, right);
            return root;
        }

        private class Node {
            private final String s;
            private final int start;
            private final int end;

            private Node left;
            private Node right;

            public Node(String s, int start, int end) {
                this.s = s;
                this.start = start;
                this.end = end;
            }

            /**
             * return -1: on left, 0: in range, 1: on the right
             */
            public int compare(int index) {
                if (index < start) {
                    return -1;
                }
                if (index > end) {
                    return 1;
                }
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        String str = "oah9hiifahhfiwh92g8g3yxh9fh9afh2gfg23f2f";
        LongString ls = new LongString(str);
        //for (int i = 0; i < str.length(); i++) {
        //    System.out.println(ls.charAt(i));
        //}
        System.out.println(ls.charAt(-1));
    }
}
