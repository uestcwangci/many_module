package shixi2018;

import java.util.Scanner;

public class Demo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] ss = s.split(" ");
        long[] input = new long[2];
        for (int i = 0; i < ss.length; i++) {
            input[i] = Integer.parseInt(ss[i]);
        }
        System.out.println(plus(input));
    }

    public static long plus(long[] a) {
        return a[0] / 2 * a[1];
    }
}
