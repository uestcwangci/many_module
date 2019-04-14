package shixizhengshi;

import java.util.Arrays;
import java.util.Scanner;

public class Demo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] str = sc.nextLine().split(" ");
        int[] a = new int[2];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(str[i]);
        }
        str = sc.nextLine().split(" ");
        int[] b = new int[a[0]];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(str[i]);
        }
        ans(a, b);
    }

    private static void ans(int[] a, int[] b) {
        int n = a[0];
        if (n <= 0) {
            System.out.println(-1);
            return;
        }
        int s = a[1];
        int total = 0;
        for (int i = 0; i < b.length; i++) {
            total += b[i];
        }
        if (s > total) {
            System.out.println(-1);
            return;
        }
        if (s == total) {
            System.out.println(0);
            return;
        }
        // 不确定输入饮料是否有序
        Arrays.sort(b);
        // 贪心算法，每个饮料从大到小喝到仅剩体积最小那瓶的容量
        for (int i = b.length - 1; s > 0 && i > 0; i--) {
            s = s - (b[i] - b[0]);
        }
        if (s <= 0) {
            System.out.println(b[0]);
            return;
        }
        if (s <= b.length) {
            // 剩余饮料不足以每瓶都喝1升
            System.out.println(b[0] - 1);
        } else {
            int ave = (int) Math.ceil(s / b.length);
            System.out.println(b[0] - ave);
        }
    }



}
