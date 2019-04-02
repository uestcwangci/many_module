package shixi2018;

import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int[] a = new int[2];
        for (int i = 0; i < s.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        System.out.println(chooclate(a));
    }

    public static int chooclate(int[] a) {
        return (int) Math.ceil(a[1] / (2 - Math.pow(0.5, a[0] - 1)));
    }

}
