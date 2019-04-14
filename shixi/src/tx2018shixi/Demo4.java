package tx2018shixi;

import java.util.*;

public class Demo4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        s = sc.nextLine().split(" ");
        int[] a = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
        }
        sc.nextLine();
        gongsi(n, m, a, b);
    }

    private static void gongsi(int n, int m, int[] xulie, int[] question) {
        Arrays.sort(xulie);
        Map<Integer, Integer> map = new HashMap<>();
        int[] paiming = new int[n];
        for (int i = 0; i < n; i++) {
            paiming[i] = i + 1;
        }
        int salary;
        for (int i = 0; i < n; i++) {
            salary = xulie[i] - paiming[i];
            if (map.containsKey(salary)) {
                map.put(salary, map.get(salary) + 1);
            } else {
                map.put(salary, 1);
            }
        }
        for (int i = 0; i < question.length; i++) {
            if (map.containsKey(question[i])) {
                System.out.println(map.get(question[i]));
            } else {
                System.out.println(0);
            }

        }
    }
}
