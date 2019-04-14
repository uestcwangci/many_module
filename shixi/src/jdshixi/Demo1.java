package jdshixi;

import java.util.ArrayList;
import java.util.Scanner;

public class Demo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] sArr = sc.nextLine().split(" ");
        int[] a = new int[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            a[i] = Integer.parseInt(sArr[i]);
        }
        fun(a);
    }

    private static void fun(int[] a) {
        int low = a[0];
        int high = a[1];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = low; i < high; i++) {
            if (isFlower(i)) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            System.out.println("no");
        } else {
            for (int i : list) {
                System.out.println(i);
            }
        }
    }

    private static boolean isFlower(int i) {
        int temp = i;
        int sum = 0;
        while (i != 0) {
            sum += Math.pow((i % 10), 3);
            i = i / 10;
        }
        return sum == temp;
    }


}
