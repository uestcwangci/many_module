package jdshixi;

import java.util.Arrays;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] sArr = sc.nextLine().split(" ");
        int[] a = new int[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            a[i] = Integer.parseInt(sArr[i]);
        }
        sArr = sc.nextLine().split(" ");
        int[] b = new int[a[0]];
        for (int i = 0; i < a[0]; i++) {
            b[i] = Integer.parseInt(sArr[i]);
        }
        sArr = sc.nextLine().split(" ");
        int[] c = new int[a[0]];
        for (int i = 0; i < a[0]; i++) {
            c[i] = Integer.parseInt(sArr[i]);
        }
        fun(a, b, c);
    }

    private static void fun(int[] a, int[] ratio, int[] vArr) {
        int n = a[0];
        int v = a[1];
        double minNum = Integer.MAX_VALUE;
        int minIndex = 0;
        double temp;
        for (int i = 0; i < n; i++) {
            temp = vArr[i] / ratio[i];
            if (temp < minNum) {
                minNum = temp;
                minIndex = i;
            }
        }
        int minRatio = ratio[minIndex];
        double total = 0.0;
        // 按照最小的比列来配置
        for (int i = 0; i < n; i++) {
            total += vArr[i] * ratio[i] / minRatio;
        }
        if (total >= v) {
            System.out.println(String.format("%.4f", (double) v));
        } else {
            System.out.println(String.format("%.4f", (double) total));
        }

    }




}
