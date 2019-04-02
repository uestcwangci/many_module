package tx2018shixi;

import java.util.Arrays;
import java.util.Scanner;

public class Demo2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        sc.nextLine();
        myQueue(n, a);
    }

    private static void myQueue(int n, int[] a) {
        if (n == 1) {
            System.out.println(0);
        }
        int j;
        boolean flag;
        for (int i = 0; i < n - 1; i++) {
            flag = false;
            for (j = i + 1; j < n; j++) {
                if (a[j] > a[i]) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                while (i < j) {
                    System.out.println(j + 1);
                    i++;
                }
                i--;
            }else {
                System.out.println(0);
            }
        }
        System.out.println(0);
    }


}
