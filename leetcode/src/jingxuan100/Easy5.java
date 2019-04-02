package jingxuan100;

public class Easy5 {
    public static void main(String[] args) {
        Easy5 test = new Easy5();
        System.out.println(test.climbStairs(3));
    }


/*    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }*/

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int first = 1;
        int second = 2;
        int now = 0;
        for (int i = 2; i < n; i++) {
            now = first + second;
            first = second;
            second = now;
        }
        return now;
    }
}
