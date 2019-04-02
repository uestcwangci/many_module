package tx_jingxuan;

import java.util.ArrayList;

public class Easy2 {
    public static void main(String[] args) {
        Easy2 test = new Easy2();
        System.out.println(test.isPalindrome(121));
    }
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        ArrayList<Integer> list = new ArrayList<>();
        while (x != 0) {
            list.add(x % 10);
            x = x / 10;
        }
        Integer[] a = list.toArray(new Integer[0]);
        int n = list.size();
        int left, right;
        if (n % 2 == 0) {
            right = n / 2;
        } else {
            right = n / 2 + 1;
        }
        left = n / 2 - 1;
        while (left >= 0) {
            if (!a[left].equals(a[right])) {
                return false;
            }
            left--;
            right++;
        }
        return true;
    }
}
