package cn.orangepoet.inaction.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengzhi
 * @date 2022/07/09
 */
public class RangeList {

    /**
     * 内部区间集合
     */
    private List<int[]> ranges = new ArrayList<>();

    /**
     * 增加区间元素
     *
     * @param start 闭区间开始
     * @param end   开区间结束
     */
    public void add(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("end<start");
        }

        List<int[]> mod = new ArrayList<>();
        boolean added = false;
        for (int[] r : ranges) {
            if (r[1] < start) {
                // [start, end) 在r右边，无重叠
                mod.add(r);
            } else if (r[0] > end) {
                // [start, end) 在r左边，无重叠, 因为ranges有序，此时可插入
                if (!added) {
                    mod.add(new int[]{start, end});
                }
                mod.add(r);
                added = true;
            } else {
                // [start, end) 和 r 重叠
                if (!added) {
                    int[] r0 = new int[]{Math.min(r[0], start), Math.max(r[1], end)};
                    mod.add(r0);
                    added = true;
                } else {
                    int[] last = mod.get(mod.size() - 1);
                    last[0] = Math.min(last[0], r[0]);
                    last[1] = Math.max(last[1], r[1]);
                }
            }
        }
        if (!added) {
            mod.add(new int[]{start, end});
        }
        this.ranges = mod;
    }

    /**
     * 移除区间元素
     *
     * @param start 闭区间开始
     * @param end   开区间结束
     */
    public void remove(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("end<start");
        }
        List<int[]> mod = new ArrayList<>();
        for (int[] r : ranges) {
            // [start,end) 覆盖 r，删除
            if (start <= r[0] && end >= r[1]) {
                continue;
            }
            // [start,end) 与 r无交集，保留
            if (end < r[0] || start > r[1]) {
                mod.add(r);
                continue;
            }

            if (r[0] < start && end < r[1]) {
                // split range
                mod.add(new int[]{r[0], start});
                mod.add(new int[]{end, r[1]});
            } else if (start > r[0]) {
                // slice right partial
                mod.add(new int[]{r[0], start});
            } else {
                // slice left partial
                mod.add(new int[]{end, r[1]});
            }
        }
        this.ranges = mod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] r : ranges) {
            sb.append("[").append(r[0]).append(", ").append(r[1]).append(")").append(" ");
        }
        return sb.toString();
    }

    /**
     * 打印当前保存区间
     */
    public void print() {
        System.out.println(this);
    }

    public static void main(String[] args) {
        RangeList rl = new RangeList();

        rl.add(1, 5);
        rl.print();

        rl.add(10, 20);
        rl.print();

        rl.add(20, 20);
        rl.print();

        rl.add(20, 21);
        rl.print();

        rl.add(2, 4);
        rl.print();

        rl.add(3, 8);
        rl.print();

        rl.remove(10, 10);
        rl.print();

        rl.remove(10, 11);
        rl.print();

        rl.remove(15, 17);
        rl.print();

        rl.remove(3, 19);
        rl.print();
    }
}
