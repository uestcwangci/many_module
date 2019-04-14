package ali2018.zhengshi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String[] strArr = sc.nextLine().split(" ");
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(strArr[i]);
        }
        List<Integer> before = new ArrayList<>();
        List<Double> after = new ArrayList<>();
        double[] ratio = new double[]{1.0};
        fun(n, a, 0, ratio, 0.0, before, after);
        double total = 0.0;
        for (double num : after) {
            total += num;
        }
        System.out.println(total);
        System.out.println(before);
        System.out.println(after);
        System.out.println(ratio[0]);
    }

    private static void fun(int n, int[] a, int start, double[] ratio, double allTime, List<Integer> before, List<Double> after) {
        int soilder = a[start];
        for (int i = start + 1; i < n; i++) {
            if (a[i] < soilder) {
                int sum = 0;
                int j = i;
                for (; j < n && a[j] < soilder; j++) {
                    sum += a[j];
                }
                if (sum >= soilder) {
                    // 去掉头,只优化一页
                    a[start] = 0;
                    allTime += sum + a[start + 1] * 0.2;
                    ratio[0] -= 0.05;
                    before.add(a[i]);
                    after.add(a[i] * 1.2);
                    for (int k = i + 1; k < j; k++) {
                        before.add(a[k]);
                        after.add((double) a[k]);
                    }
                } else {
                    allTime += soilder;
                    ratio[0] -= 0.05 * (j - i);
                    before.add(a[i]);
                    after.add((double) a[i]);
                }
                fun(n, a, j, ratio, allTime, before, after);
            } else {
                allTime += a[i];
                before.add(a[i]);
                after.add((double) a[i]);
            }
        }
    }

}
