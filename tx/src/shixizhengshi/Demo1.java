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
        Arrays.sort(b);
        for (int i = 0; i < b.length - 1; i++) {
            s = s - b[0];
        }
        if (s <= 0) {
            System.out.println(b[0]);
            return;
        }
        if (s < b.length) {
            System.out.println(b[0] - 1);
            return;
        } else {
            int ave = (s + 1) / b.length; //向上取整
            System.out.println(b[0] - ave);
            return;
        }
    }

}
