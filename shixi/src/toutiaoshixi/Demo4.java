package toutiaoshixi;

import java.util.*;

public class Demo4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer. parseInt(sc.nextLine());
        int[][] a = new int[n][n];
        String[] sArr;
        for (int i = 0; i < n; i++) {
            sArr = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                a[i][j] = Integer.parseInt(sArr[j]);
            }
        }
        System.out.println(fun(n, a));
    }

    private static int fun(int n, int[][] a) {
        int cost = Integer.MAX_VALUE;
        Set<Integer> gone = new HashSet<>();

        for (int i = 1; i < n; i++) {
            gone.clear();
            gone.add(0);
            int temp = inner(0,0, i, a, gone);
            if (temp < cost) {
                cost = temp;
            }
        }
        return cost;
    }

    private static int inner(int from, int nowCost, int goTo, int[][] a, Set<Integer> gone) {
        if (!gone.contains(goTo)) {
            gone.add(goTo);
            nowCost += a[from][goTo];
            if (gone.size() < a.length) {
                for (int i = 0; i < a.length; i++) {
                    if (!gone.contains(i)) {
                        nowCost += inner(goTo, nowCost, i, a, gone);
                        return nowCost;
                    }
                }
            } else { //处理回北京
                nowCost += a[goTo][0];
                return nowCost;
            }
        }
        return a[from][goTo];
    }

}
