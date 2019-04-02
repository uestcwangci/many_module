package tx2018shixi;

import java.util.Arrays;
import java.util.Scanner;

public class Demo5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int n = Integer.parseInt(s[0]);
        long v = Integer.parseInt(s[1]);
        long[][] a = new long[n][2];
        for (int i = 0; i < n; i++) {
            s = sc.nextLine().split(" ");
            a[i][0] = Integer.parseInt(s[0]);
            a[i][1] = Integer.parseInt(s[1]);
        }
        xiaoMin(n, v, a);
    }

    private static void xiaoMin(int n, long v, long[][] a) {
        long result = 1;
        int miMei = 0;
        while (v > 0) {
            if (miMei < n && result == a[miMei][0]) {
                result = result + a[miMei][1] - a[miMei][0];
                miMei++;
            }
            result++;
            v--;
        }
        System.out.println(result - 1);
    }
}
