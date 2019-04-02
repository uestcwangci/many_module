package com.demo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

//        String[] sA = s.split(" ");
//        plus(sA);
    }

    public static void plus(String[] strings) {
        int[] a = new int[2];
        int foot = 0;
        for (String s : strings) {
            a[foot++] = Integer.parseInt(s);
        }
        System.out.println(a[0] / 2 * a[1]);

    }

    public static void pooker(String[] strings) {
        int n = Integer.parseInt(strings[0]);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(strings[i + 1]);
        }

    }
}
