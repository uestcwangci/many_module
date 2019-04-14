package shixi2018;

import java.util.Scanner;

public class Demo4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String[] s = sc.nextLine().split(" ");
        int[] a = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        System.out.println(music(n, a) % 1000000007);
    }

    public static int music(int n, int[] a) {
        int result = 0;
        int bigger, bNum, smaller, sNum;
        if (a[0] > a[2]) {
            bigger = a[0];
            bNum = a[1];
            smaller = a[2];
            sNum = a[3];
        } else {
            bigger = a[2];
            bNum = a[3];
            smaller = a[0];
            sNum = a[1];
        }

        int i = 0;
        int j = bNum;
        while (j > 0 && bigger * j > n) {
            j--;
        }
        int sumNum = 0;
        while (i <= sNum && j > 0) {
            sumNum = smaller * i + bigger * j;
            if (sumNum > n) {
                j--;
                i++;
            } else if (sumNum < n) {
                i++;
            } else {
                result += (Cnm(bNum, j) * Cnm(sNum, i));
                j--;
            }
        }
        return result;
    }

    private static int Cnm(int n, int m) {
        if (m == 0 || n == m) {
            return 1;
        }
        if (m == 1) {
            return n;
        }
        if (m > n / 2) {
            m = n - m;
        }
        long fenzi = 1;
        long fenmu = 1;
        for (int i = 0; i < m; n--, i++) {
            fenzi *= n;
        }
        for (; m > 0; m--) {
            fenmu *= m;
        }
        return (int) (fenzi / fenmu);
    }
}
