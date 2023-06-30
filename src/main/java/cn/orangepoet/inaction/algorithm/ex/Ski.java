package cn.orangepoet.inaction.algorithm.ex;

import java.util.Scanner;

/**
 * @author chengzhi
 * @date 2019/10/22
 */
public class Ski {
    private static int[][] arr; //存放输入的矩阵
    private static int[][] value; //对应arr没点的“滑雪长度”
    private static int c; //arr的列数
    private static int r; //arr的行数

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            r = sc.nextInt();
            c = sc.nextInt();
            int max = 0;
            arr = new int[r][c];
            value = new int[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    arr[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    int temp = ski(i, j, Integer.MAX_VALUE);
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
            System.out.println(max);

        }
        sc.close();
    }

    /**
     * @param col：arr的列数
     * @param row：arr的行数
     * @param maxValue：上一个arr点的值
     * @return
     */
    private static int ski(int row, int col, int maxValue) {
        System.out.println("row: " + row + ", col: " + col);
        //出了矩阵的区域或者现在arr这个点的值大于上一个arr点的值，即跑到更高的点去了，无效，返回0
        if (col >= c || col < 0 || row >= r || row < 0 || maxValue <= arr[row][col]) {
            return 0;
        }
        //若已经计算过了此arr点的“滑雪长度”，直接返回
        if (value[row][col] > 0) {
            return value[row][col];
        }
        //不然就计算此点的“滑雪长度” = 上下左右的点的“滑雪长度”的最大值 + 1
        value[row][col] = max(
                ski(row - 1, col, arr[row][col]),
                ski(row + 1, col, arr[row][col]),
                ski(row, col - 1, arr[row][col]),
                ski(row, col + 1, arr[row][col])
        ) + 1;
        return value[row][col];
    }

    /**
     * @param ski1
     * @param ski2
     * @param ski3
     * @param ski4
     * @return 最大值
     */
    private static int max(int ski1, int ski2, int ski3, int ski4) {
        return Math.max(Math.max(ski1, ski2), Math.max(ski3, ski4));
    }
}
