package shixizhengshi;

import java.util.Arrays;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] str = sc.nextLine().split(" ");
        int n = Integer.parseInt(str[0]);
        str = sc.nextLine().split(" ");
        int[] a = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(str[i]);
        }
        str = sc.nextLine().split(" ");
        int[] b = new int[n];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(str[i]);
        }
        ans(n, a, b);
    }

    private static void ans(int n, int[] strength, int[] money) {
        if (n <= 0) {
            System.out.println(0);
            return;
        }
        int cost = 0;
        int curStrength = 0;
        for (int i = 0; i < n; i++) {
            if (curStrength < strength[i]) {
                cost += money[i];
                curStrength += strength[i];
            }
        }
        System.out.println(cost);
    }
}
