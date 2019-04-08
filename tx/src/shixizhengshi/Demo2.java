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
        int cost = ans(n, a, b, 0, true, 0, 0);
        System.out.println(cost);
    }

    private static int ans(int n, int[] strength, int[] money, int i, boolean isBuy, int curCost, int curStr) {
        if (n <= 0) {
            return 0;
        }
        if (i >= n - 1) {
            // 最后一个怪兽能打赢 不用买
            return curCost;
        }
        if (isBuy) {
            curCost += money[i];
            curStr += strength[i];
            i++;
        }else {
            i++;
        }
        if (curStr < strength[i]) {
            // 不得不买的情况
            for (; i < n; i++) {
                if (curStr < strength[i]) {
                    curCost += money[i];
                    curStr += strength[i];
                    continue;
                }
                break;
            }
        }
        curCost = Math.min(ans(n, strength, money, i, true, curCost, curStr),
                    ans(n, strength, money, i, false, curCost, curStr));
        return curCost;

    }

}
