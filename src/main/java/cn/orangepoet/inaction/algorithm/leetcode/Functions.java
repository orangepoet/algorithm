package cn.orangepoet.inaction.algorithm.leetcode;

import java.util.Random;

public class Functions {

    private static final Random random = new Random();

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }


    public static int[] randomSequence(int count) {
        int[] ret = new int[count];
        for (int i = 0; i < count; i++) {
            ret[i] = random.nextInt(3);
        }
        return ret;
    }

}
