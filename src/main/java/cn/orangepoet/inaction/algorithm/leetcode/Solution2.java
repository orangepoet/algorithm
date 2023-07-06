package cn.orangepoet.inaction.algorithm.leetcode;

import java.util.*;

public class Solution2 {
    /**
     * 上下翻转二叉树
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        dfs(stack, root);

        TreeNode p = null;
        TreeNode ret = null;
        boolean left = true;
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            if (n != null) {
                n.left = null;
                n.right = null;
            }
            if (p == null) {
                p = n;
                ret = p;
            } else {
                if (left) {
                    p.left = n;
                    left = false;
                } else {
                    p.right = n;
                    p = n;
                    left = true;
                }
            }
        }
        return ret;
    }

    private void dfs(LinkedList<TreeNode> stack, TreeNode root) {
        if (root == null) {
            return;
        }
        stack.push(root);
        stack.push(root.right);
        dfs(stack, root.left);
    }

    /**
     * 从未排序的链表中移除重复元素
     */
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        Map<Integer, Integer> map = new HashMap<>();
        ListNode p = head;
        while (p != null) {
            map.compute(p.val, (k, v) -> v == null ? 1 : v + 1);
            p = p.next;
        }
        ListNode h = new ListNode(-1);
        ListNode prev = h;
        p = head;
        while (p != null) {
            ListNode next = p.next;
            if (map.get(p.val) == 1) {
                prev.next = p;
                p.next = null;
                prev = p;
            }
            p = next;
        }
        return h.next;
    }

    private int max = 0;
    private Map<Node, Integer> depthMap = new HashMap<>();

    public int diameter(Node root) {
        if (root == null) {
            return 0;
        }
        max = Math.max(max, diameter0(root));
        for (Node child : root.children) {
            diameter(child);
        }
        return max;
    }

    private int diameter0(Node root) {
        List<Node> children = root.children;
        if (children == null || children.size() == 0) {
            return 0;
        }
        if (children.size() == 1) {
            return depth0(children.get(0));
        }
        int size = root.children.size();
        int[] depths = new int[size];
        for (int i = 0; i < size; i++) {
            depths[i] = depth0(root.children.get(i));
        }

        Arrays.sort(depths);
        return depths[depths.length - 1] + depths[depths.length - 2];
    }

    private int depth0(Node node) {
        if (depthMap.containsKey(node)) {
            return depthMap.get(node);
        }
        if (node == null) {
            return 0;
        }
        int depth = 1;
        if (node.children != null) {
            for (Node child : node.children) {
                depth = Math.max(depth, depth0(child) + 1);
            }
        }
        depthMap.put(node, depth);
        return depth;
    }

    /**
     * 返回比当前数大的最小回文素数
     */
    public int primePalindrome(int n) {
        int num = n;
        while (true) {
            if (num == reverse(num) && isPrime(num)) {
                return num;
            }
            num++;
        }
    }

    public int reverse(int N) {
        int ans = 0;
        while (N > 0) {
            ans = 10 * ans + (N % 10);
            N /= 10;
        }
        return ans;
    }

    private boolean isPrime(int n) {
        double to = Math.sqrt(n);
        for (int i = 2; i <= to; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 到达终点数字
     * <p>
     * 在一根无限长的数轴上，你站在0的位置。终点在target的位置。
     * <p>
     * 每次你可以选择向左或向右移动。第 n 次移动（从 1 开始），可以走 n 步。
     * <p>
     * 返回到达终点需要的最小移动次数。
     * <p>
     * 示例 1:
     * <p>
     * 输入: target = 3
     * 输出: 2
     * 解释:
     * 第一次移动，从 0 到 1 。
     * 第二次移动，从 1 到 3 。
     * 示例 2:
     * <p>
     * 输入: target = 2
     * 输出: 3
     * 解释:
     * 第一次移动，从 0 到 1 。
     * 第二次移动，从 1 到 -1 。
     * 第三次移动，从 -1 到 2 。
     * 注意:
     * <p>
     * target是在[-10^9, 10^9]范围中的非零整数。
     */
    public int reachNumber(int target) {
        int sum = 0;
        int target0 = Math.abs(target);
        int j = Math.max(target0, 3);
        for (int i = 1; i <= j; i++) {
            sum += i;
            if (sum >= target0 && (sum - target0) % 2 == 0) {
                return i;
            }
        }
        return target0;
    }


    /**
     * 划分数组为连续数字的集合
     */
    public boolean isPossibleDivide(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int len = nums.length;
        while (count < len) {
            int j = 0;
            int prev = -1;
            for (int i = 0; i < k; i++) {
                while (j < len && (nums[j] == 0 || nums[j] == prev)) {
                    j++;
                }
                if (j >= len) {
                    return false;
                }
                if (prev == -1 || nums[j] == prev + 1) {
                    prev = nums[j];
                    nums[j] = 0;
                    j++;
                    count++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 二叉树最长连续序列
     */
    public int longestConsecutive(TreeNode root) {
        int max0 = longestConsecutive0(-1, root, 0);
        max = Math.max(max, max0);

        if (root.left != null) {
            longestConsecutive(root.left);
        }
        if (root.right != null) {
            longestConsecutive(root.right);
        }
        return max;
    }

    private int longestConsecutive0(int prev, TreeNode node, int depth) {
        if (prev != -1 && node.val != prev + 1) {
            return depth;
        }
        depth = depth + 1;
        int ans = depth;
        if (node.left != null) {
            ans = Math.max(ans, longestConsecutive0(node.val, node.left, depth));
        }
        if (node.right != null) {
            ans = Math.max(ans, longestConsecutive0(node.val, node.right, depth));
        }
        return ans;
    }

    public int shortestBridge(int[][] grid) {
        int min = Integer.MAX_VALUE;
        int x = grid.length;
        int y = grid[0].length;
        boolean first = true;
        List<int[]> A = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grid[i][j] == 1) {
                    if (first) {
                        dfs(grid, i, j, x, y, A);
                        first = false;
                    } else {
                        int bread = bread0(i, j, A) - 1;
                        min = Math.min(min, bread);
                    }
                }
            }
        }
        return min;
    }

    //标记2
    private void dfs(int[][] grid, int i, int j, int n, int m, List<int[]> A) {
        if (!(i >= 0 && i < n && j >= 0 && j < m)) {
            return;
        }
        if (grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 0;
        A.add(new int[]{i, j});
        dfs(grid, i, j + 1, n, m, A);
        dfs(grid, i, j - 1, n, m, A);
        dfs(grid, i + 1, j, n, m, A);
        dfs(grid, i - 1, j, n, m, A);
    }

    private int bread0(int i, int j, List<int[]> A) {
        int path = Integer.MAX_VALUE;
        for (int[] p : A) {
            int p0 = Math.abs(i - p[0]) + Math.abs(j - p[1]);
            path = Math.min(path, p0);
        }
        return path;
    }

    public int compareVersion(String version1, String version2) {
        String[] part1 = version1.split("\\.");
        String[] part2 = version2.split("\\.");
        int len = Math.min(part1.length, part2.length);
        for (int i = 0; i < len; i++) {
            int left = Integer.parseInt(trimZero(part1[i]));
            System.out.println("left" + left);
            int right = Integer.parseInt(trimZero(part2[i]));
            System.out.println("right" + right);
            int compare = Integer.compare(left, right);
            if (compare != 0) {
                return compare;
            }
        }
        if (part1.length > len) {
            return isZero(part1, len) ? 0 : 1;
        } else if (part2.length > len) {
            return isZero(part2, len) ? 0 : -1;
        } else {
            return 0;
        }
    }

    private String trimZero(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return i > 0 ? s.substring(i) : s;
    }

    private boolean isZero(String[] arr, int start) {
        for (int i = start; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length(); j++) {
                if (arr[i].charAt(j) != '0') {
                    return false;
                }
            }
        }
        return true;
    }

    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = 1;
            }
        }
        for (int i = 0; i < mines.length; i++) {
            int[] p = mines[i];
            ans[p[0]][p[1]] = 0;
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (ans[i][j] == 1 && i > 0 && i < n - 1 && j > 0 && j < n - 1) {
                    int val = plusSign0(ans, i, j, n);
                    ans[i][j] += val;
                }
                max = Math.max(ans[i][j], max);
            }
        }
        return max;
    }

    private int plusSign0(int[][] ans, int i, int j, int n) {
        int h = 0;
        for (int k = 0; ; k++) {
            int left = j - k;
            int right = j + k;
            if (left < 0 || right >= n || ans[i][left] == 0 || ans[i][right] == 0) {
                break;
            }
            h = k;
        }
        int v = 0;
        for (int k = 0; ; k++) {
            int up = i - k;
            int down = i + k;
            if (up < 0 || down >= n || ans[up][j] == 0 || ans[down][j] == 0 || k > h) {
                break;
            }
            v = k;
        }
        return Math.min(h, v);
    }

    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            boolean hasNext = false;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode tn = queue.poll();
                list.add(tn == null ? null : tn.val);
                if (tn != null) {
                    queue.offer(tn.left);
                    queue.offer(tn.right);
                    if (tn.left != null || tn.right != null) {
                        hasNext = true;
                    }
                }
            }
            if (!hasNext) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        String[] trees0 = data.split(",");
        Integer[] trees = new Integer[trees0.length];
        for (int i = 0; i < trees0.length; i++) {
            trees[i] = trees0[i].equals("null") ? null : Integer.parseInt(trees0[i]);
        }

        int idx = 0;
        TreeNode root = new TreeNode(trees[idx], null, null);
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                if (idx >= trees.length - 1) {
                    return root;
                }
                TreeNode tn = queue.poll();
                tn.left = trees[++idx] != null ? new TreeNode(trees[idx], null, null) : null;
                tn.right = trees[++idx] != null ? new TreeNode(trees[idx], null, null) : null;
                if (tn.left != null) {
                    queue.offer(tn.left);
                }
                if (tn.right != null) {
                    queue.offer(tn.right);
                }
            }
        }
        return root;
    }

    /**
     * 第n丑数
     */
    public int nthUglyNumber(int n) {
        int[] factors = {2, 3, 5};
        PriorityQueue<Long> heap = new PriorityQueue<>();
        heap.offer(1L);
        Set<Long> seen = new HashSet<>();
        int ugly = 1;
        for (int i = 0; i < n; i++) {
            long curr = heap.poll();
            ugly = (int) curr;
            for (int j = 0; j < factors.length; j++) {
                long e = curr * factors[j];
                if (seen.add(e)) {
                    heap.offer(e);
                }
            }
        }
        return ugly;
    }

    /**
     * 会议室冲突
     */
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));
        int[] prev = null;
        for (int[] each : intervals) {
            if (prev != null) {
                if (each[0] < prev[1]) {
                    return false;
                }
            }
            prev = each;
        }
        return true;
    }

    /**
     * 会议室2
     */
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));
        int size = intervals.length;
        int[] taken = new int[size];
        int i = 0;
        int rooms = 0;
        int[] prev = null;
        while (i < size) {
            for (int j = 0; j < size; j++) {
                if (taken[j] == 1) {
                    continue;
                }
                if (prev == null || intervals[j][0] >= prev[1]) {
                    taken[j] = 1;
                    i++;
                    prev = intervals[j];
                }
            }
            rooms++;
            prev = null;
        }
        return rooms;
    }

    /**
     * 摆动排序
     */
    public void wiggleSort(int[] nums) {
        if (nums.length <= 1) {
            return;
        }
        int dir = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if ((dir < 0 && nums[i] < nums[i + 1]) || (dir > 0 && nums[i] > nums[i + 1])) {
                swap(nums, i, i + 1);
            }
            dir = -1 * dir;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 排列
     */
    public List<List<Integer>> permute(int count) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            output.add(i);
        }
        permute0(ans, output, 0);
        return ans;
    }

    private void permute0(List<List<Integer>> ans, List<Integer> output, int first) {
        if (first == output.size()) {
            ans.add(new ArrayList<>(output));
            return;
        }
        for (int i = first; i < output.size(); i++) {
            Collections.swap(output, first, i);
            permute0(ans, output, first + 1);
            Collections.swap(output, i, first);
        }
    }

    /**
     * 组合
     */
    public List<List<Integer>> combine(int count) {
        List<List<Integer>> ans = new ArrayList<>();
        combine0(count, 0, new ArrayList<>(), ans);
        return ans;
    }

    private void combine0(int count, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < count; i++) {
            current.add(i + 1);
            combine0(count, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * 荷兰国旗问题
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        /**
         *  [0,p0) 0
         *  [p0, i)  1
         *  (p2, n-1]  2
         */
        int n = nums.length;
        int p0 = 0;
        int i = 0;
        int p2 = n - 1;
        while (i <= p2) {
            if (nums[i] == 0) {
                assert nums[p0] == 1 || (nums[p0] == 0 && p0 == i);
                swap(nums, i, p0);
                i++;
                p0++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, i, p2);
                p2--;
            }
        }
    }

    /**
     * k数组合 (n的组合数限定数量为k)
     */
    public List<List<Integer>> combineK(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        combineK0(n, k, 1, new ArrayList<>(), ans);
        return ans;
    }

    private void combineK0(int n, int k, int p, List<Integer> cur, List<List<Integer>> ans) {
        if (cur.size() + (n - p + 1) < k) {
            return;
        }
        if (cur.size() == k) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        cur.add(p);
        combineK0(n, k, p + 1, cur, ans);

        cur.remove(cur.size() - 1);
        combineK0(n, k, p + 1, cur, ans);
    }

    /**
     * 数组中选k个数和为n, 数量为k
     */
    public List<List<Integer>> combineK2(int[] arr, int sum, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        combineK20(arr, sum, k, 1, new ArrayList<>(), ans);
        return ans;
    }

    private void combineK20(int[] arr, int sum, int k, int p, List<Integer> cur, List<List<Integer>> ans) {
        if (cur.size() == k && cur.stream().mapToInt(Integer::intValue).sum() == sum) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        if (cur.size() > k || p >= arr.length) {
            return;
        }
        cur.add(arr[p]);
        combineK20(arr, sum, k, p + 1, cur, ans);
        cur.remove(cur.size() - 1);
        combineK20(arr, sum, k, p + 1, cur, ans);
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left > right) {
            return head;
        }
        // p1, p2 指代left位置的prev， p
        ListNode p1 = new ListNode(-501, head), p2 = head;
        for (int j = 0; j < left - 1; j++) {
            if (p2 == null) {
                break;
            }
            p1 = p2;
            p2 = p2.next;
        }
        if (p2 != null) {
            // p3, p4 指代right位置的prev， p
            ListNode p3 = p2;
            ListNode p4 = p3.next;
            for (int i = 0; i < right - left; i++) {
                if (p4 == null) {
                    break;
                }

                ListNode next = p4.next;
                p1.next = p4;
                p4.next = p2;
                p3.next = next;

                p2 = p1.next;
                p4 = next;
            }
        }
        return left != 1 ? head : p1.next;
    }

    public ListNode reverseBetween2(ListNode head, int left, int right) {
        if (head == null || left > right) {
            return head;
        }
        ListNode p = head;
        List<ListNode> list = new ArrayList<>();
        while (p != null) {
            list.add(p);
            p = p.next;
        }
        int r = Math.min(right - 1, list.size() - 1);
        int l = Math.max(0, left - 1);
        if (l < r) {
            for (int i = r; i > l; i--) {
                list.get(i).next = list.get(i - 1);
                list.get(i - 1).next = null;
            }
            if (l > 0) {
                list.get(l - 1).next = list.get(r);
            }
            if (r + 1 < list.size()) {
                list.get(l).next = list.get(r + 1);
            }
        }

        return l > 0 ? head : list.get(r);
    }

    /**
     * 不同的二叉搜索树
     * <p>
     * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        return makeTree(1, n);
    }

    private List<TreeNode> makeTree(int from, int to) {
        if (from > to) {
            return new ArrayList<>();
        }
        if (from == to) {
            return Collections.singletonList(new TreeNode(from));
        }
        List<TreeNode> list = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            List<TreeNode> lefts = makeTree(from, i - 1);
            List<TreeNode> rights = makeTree(i + 1, to);
            if (lefts.isEmpty()) lefts.add(null);
            if (rights.isEmpty()) rights.add(null);
            for (TreeNode left : lefts) {
                for (TreeNode right : rights) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    list.add(root);
                }
            }
        }
        return list;
    }

    /**
     * 同上，只是统计数量
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] ans = new int[n + 1];
        ans[0] = 1;

        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 0; j <= i - 1; j++) {
                sum += ans[j] * ans[i - j - 1];
            }
            ans[i] = sum;
        }
        return ans[n];
    }

    /**
     * 阶乘后的零
     *
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        int m = 0;
        int[] ans = new int[n + 1];
        for (int i = 5; i <= n; i += 5) {
            ans[i] = 1 + ans[i / 5];
            m += ans[i];
        }
        return m;
    }

    /**
     * 链表重排 交叉序列
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            list.add(p);
            p = p.next;
        }
        int size = list.size();
        int i;
        int j = size - 1;
        for (i = 0; i + 1 < j; i++, j--) {
            ListNode l = list.get(i);
            ListNode m = list.get(j);
            ListNode r = l.next;
            l.next = m;
            m.next = r;
        }
        list.get(j).next = null;
    }

    /**
     * 给你二叉搜索树的根节点 root ，该树中的 恰好 两个节点的值被错误地交换。请在不改变其结构的情况下，恢复这棵树 。
     *
     * @param root
     */
    public void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode x = null, y = null, pred = null;

        TreeNode p = root;
        while (!stack.isEmpty() || p != null) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if (pred != null && p.val < pred.val) {
                y = p;
                if (x == null) {
                    x = pred;
                } else {
                    break;
                }
            }
            pred = p;
            p = p.right;
        }

        swap(x, y);
    }

    private void swap(TreeNode left, TreeNode right) {
        int tmp = left.val;
        left.val = right.val;
        right.val = tmp;
    }

    /**
     * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), p = s3.length();
        if (n + m != p) {
            return false;
        }
        boolean fn[][] = new boolean[n + 1][m + 1];
        fn[0][0] = true;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                int k = i + j - 1;
                if (i > 0) {
                    fn[i][j] = fn[i][j] || (fn[i - 1][j] && s1.charAt(i - 1) == s3.charAt(k));
                }
                if (j > 0) {
                    fn[i][j] = fn[i][j] || (fn[i][j - 1] && s2.charAt(j - 1) == s3.charAt(k));
                }

            }
        }
        return fn[n][m];
    }

    public int matrixSum(int[][] nums) {
        if (nums == null || nums.length == 0 || nums[0].length == 0) {
            return 0;
        }
        int m = nums.length, n = nums[0].length;
        for (int j = 0; j < m; j++) {
            Arrays.sort(nums[j]);
        }
        int sum = 0;
        for (int j = n - 1; j >= 0; j--) {
            int max = 0;
            for (int i = 0; i < m; i++) {
                max = Math.max(max, nums[i][j]);
            }
            sum += max;
        }
        return sum;
    }

    public int maxMoves(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int[][] f = new int[m][n];

        int max = 0;
        for (int col = 1; col < n; col++) {
            int colMax = 0;
            for (int row = 0; row < m; row++) {
                for (int k = Math.max(0, row - 1); k < Math.min(m, row + 2); k++) {
                    if (col - 1 != 0 && f[k][col - 1] == 0) {
                        continue;
                    }
                    if (grid[row][col] > grid[k][col - 1]) {
                        f[row][col] = Math.max(f[row][col], f[k][col - 1] + 1);
                    }
                }
                colMax = Math.max(colMax, f[row][col]);
            }
            if (colMax == 0) {
                break;
            }
            max = colMax;
        }
        return max;
    }

    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();

        findSubsequences0(nums, 0, ans, new ArrayList<>(), Integer.MIN_VALUE);
        return new ArrayList<>(ans);
    }

    private void findSubsequences0(int[] nums, int cur, List<List<Integer>> ans, List<Integer> seq, int prev) {
        if (cur == nums.length) {
            if (seq.size() > 1) {
                ans.add(new ArrayList<>(seq));
            }
            return;
        }

        if (nums[cur] >= prev) {
            List<Integer> list = new ArrayList<>(seq);
            list.add(nums[cur]);
            findSubsequences0(nums, cur + 1, ans, list, nums[cur]);
        }
        if (nums[cur] != prev) {
            findSubsequences0(nums, cur + 1, ans, seq, prev);
        }
    }

    /**
     * 数组中最长的方波
     *
     * @param nums
     * @return
     */
    public int longestSquareStreak(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int[] f = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            map.put((int) Math.pow(nums[i], 2), i);
        }
        for (int i = 0; i < n; i++) {
            f[i] = fn(nums, f, i, map);
            max = Math.max(f[i], max);
        }
        return max < 2 ? -1 : max;
    }

    private int fn(int[] nums, int[] f, int i, Map<Integer, Integer> map) {
        if (f[i] != 0) {
            return f[i];
        }
        if (map.containsKey(nums[i])) {
            return fn(nums, f, map.get(nums[i]), map) + 1;
        } else {
            return 1;
        }
    }

    /**
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int length = m + n;
        int i = -1, j = -1;
        int k = length % 2 == 0 ? length / 2 - 1 : length / 2;
        while (i + j + 2 < k) {
            int r = k - (i + j + 2) >= 2 ? (k - (i + j + 2)) / 2 : 1;

            if (i < m - 1 && j < n - 1) {
                int i1 = Math.min(i + r, m - 1);
                int j1 = Math.min(j + r, n - 1);
                if (nums1[i1] < nums2[j1]) {
                    i = i1;
                } else {
                    j = j1;
                }
            } else if (i < m - 1) {
                i = i + r;
            } else {
                j = j + r;
            }
        }
        i++;
        j++;
        if (length % 2 == 0) {
            int ret = 0;
            for (int l = 0; l < 2; l++) {
                if (i < m && j < n) {
                    ret += nums1[i] < nums2[j] ? nums1[i++] : nums2[j++];
                } else if (i < m) {
                    ret += nums1[i++];
                } else {
                    ret += nums2[j++];
                }

            }
            return ret / 2.0;
        } else {
            if (i < m && j < n) {
                return Math.min(nums1[i], nums2[j]);
            } else if (i < m) {
                return nums1[i];
            } else {
                return nums2[j];
            }
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode dum = new ListNode(Integer.MIN_VALUE);
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> Integer.compare(a.val, b.val));

        for (ListNode item : lists) {
            if (item != null) {
                pq.offer(item);
            }
        }
        ListNode iter = dum;
        while (!pq.isEmpty()) {
            ListNode n = pq.poll();
            iter.next = n;
            iter = n;
            if (n.next != null) {
                pq.offer(n.next);
            }
        }
        iter.next = null;
        return dum.next;
    }
}
