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
        while (bNum > 0 && bigger * bNum > n) {
            bNum--;
        }
        int i = 0;
        int sumNum = 0;
        while (i <= bNum) {
            sumNum = smaller * i + bigger * bNum;
            if (sumNum > n) {
                bNum--;
                i++;
            } else if (sumNum < n) {
                i++;
            } else {
            }
        }
        return 1;
    }
}
