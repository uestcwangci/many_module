package tx2018shixi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] ss = sc.nextLine().split(" ");
        int[] a = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            a[i] = Integer.parseInt(ss[i]);
        }
        huiyi(a[0], a[1], a[2]);
    }

    public static void huiyi(int n, int s, int m) {
        int realS = s - 1;
        int realM = m - 1;
        int realN = n;
        List<Integer> list = new ArrayList<>();
        int foot = 0;
        while (n > 0) {
            list.add(++foot);
            n--;
        }
        int now = realS;
        while (!list.isEmpty()) {
            now = (now + realM) % (realN--);
            System.out.println(list.remove(now));
        }
    }
}
