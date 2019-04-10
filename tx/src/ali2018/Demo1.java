package ali2018;

import java.util.Scanner;

public class Demo1 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String res;

        int _m;
        _m = Integer.parseInt(in.nextLine().trim());

        int _n;
        _n = Integer.parseInt(in.nextLine().trim());

        res = maxNum(_m, _n);
        System.out.println(res);
    }

    /** 请完成下面这个函数，实现题目要求的功能 **/
    /**
     * 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^
     **/
    static String maxNum(int m, int n) {
        int last = m - n * 2;
        if (last < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        if (last == 0) {
            for (int i = 0; i < n; i++) {
                sb.append("1");
            }
            return sb.toString();
        }
        int numOf9 = last / 4;
        for (int i = 0; i < numOf9; i++) {
            sb.append("9");
        }
        last = last - 4 * numOf9;
        for (int i = 0; i < last; i++) {
            sb.append("7");
        }
        for (int i = last; i < n; i++) {
            sb.append("1");
        }
        return sb.toString();
    }
}
