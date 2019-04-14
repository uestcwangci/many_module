package ali2018.zhengshi;

import java.beans.IntrospectionException;
import java.util.*;

public class Demo3 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] line = in.nextLine().split(",");
        int m = Integer.valueOf(line[0]);
        int k = Integer.valueOf(line[1]);
        System.out.println(calculate(m, k));

    }

    static String calculate(int m, int k) {
        int last3 = 2;
        int last2 = 3;
        int last1 = 4;
        int result;
        List<Integer> bianHao = new ArrayList<>();

        bianHao.add(last3);
        bianHao.add(last2);
        bianHao.add(last1);
        for (int i = 4; i <= m; i++) {
            result = last2 + last3;
            bianHao.add(result);
            last3 = last2;
            last2 = last1;
            last1 = result;
        }
        Integer[] b = new Integer[bianHao.size()];
        b = bianHao.toArray(b);

        int year = 2018;
        List<Integer> small = new ArrayList<>();
        List<Integer> big = new ArrayList<>();
        small.add(2);
        small.add(1);
        big.add(3);
        int pigs = 3;
        while (pigs < m) {
            year++;
            for (int i = 0; i < small.size(); i++) {
                int age = small.get(i);
                if (++age >= 3) {
                    small.remove(i);
                    big.add(1);
                } else {
                    small.set(i, age + 1);
                }
            }
            for (int i = 0; i < big.size(); i++) {
                int age = big.get(i);
                if (++age >= 3) {
                    small.add(1);
                    pigs++;
                } else {
                    big.set(i, age + 1);
                }
            }
        }
        for (int i = 0; i < bianHao.size(); i++) {
            int num = bianHao.get(i);
            StringBuilder sb = new StringBuilder();
            for (char c : String.valueOf(num).toCharArray()) {
                sb.append(c);
            }
            sb.reverse();
            bianHao.set(i, Integer.parseInt(sb.toString()));
            sb.delete(0, sb.length());
        }

        Collections.sort(bianHao);
        StringBuilder sb = new StringBuilder();
        for (char c : String.valueOf(bianHao.get(bianHao.size() - k)).toCharArray()) {
            sb.append(c);
        }
        sb.reverse();
        int num = Integer.parseInt(sb.toString());
        int count = 0;
        for (int i : b) {
            count++;
            if (num == i) {
                break;
            }
        }
        return last1 + "," + year + "," + count;
    }
}
