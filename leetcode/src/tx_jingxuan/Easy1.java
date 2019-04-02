package tx_jingxuan;

import java.util.LinkedList;
import java.util.Queue;

public class Easy1 {
    public static void main(String[] args) {
        Easy1 test = new Easy1();
        System.out.println(test.reverse(-2147483648));

    }

    public int reverse(int x) {
        Queue<Integer> Q = new LinkedList<>();

        while (x != 0) {
            Q.offer(x % 10);
            x = x / 10;
        }
        while (!Q.isEmpty() && Q.peek() == 0) {
            Q.poll();
        }
        int n = Q.size();
        int result = 0;
        if (n > 10) {
            return 0;
        } else if (n == 10) {
            if (Math.abs(Q.peek()) > 3) {
                return 0;
            }
        }
        while (!Q.isEmpty()) {
            try {
                result = Math.addExact(result, Q.poll() * (int) Math.pow(10, --n));
            } catch (ArithmeticException e) {
                return 0;
            }
        }
        return result;
    }

}
