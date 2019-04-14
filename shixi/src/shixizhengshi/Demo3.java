package shixizhengshi;

import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] str = sc.nextLine().split(" ");
        int[] a = new int[2];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(str[i]);
        }
        ans(a);
    }

    private static void ans(int[] a) {
        int n = a[0];
        if (n <= 0) {
            System.out.println(0);
            return;
        }
        int k = a[1];
        int count = 0;
        if (k <= 1) {
            long fenzi = 1;
            long fenmu = 1;
            for (int m = n - 1; m > 0; m--) {
                fenmu *= m;
            }
            fenmu = fenmu * fenmu;
            for (int m = 2 * n - 1; m > n; m--) {
                fenzi *= m;
            }
            count = (int) (fenzi / fenmu);
            System.out.println(count*2);
            return;
        }else {
        int[] one = new int[n];
        int[] two = new int[n];
        for (int i = 0; i < n; i++) {
            one[i] = i + n + 1;
            two[i] = i + 1;
        }
            while (n >= 0 && one[0] - two[0] >= k) {
                count++;
                int temp = one[0];
                one[0] = two[--n];
                two[n] = temp;
            }
        }
        System.out.println(count*2);
    }
}



