package com.demo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int foot = 0;
        int[] input = new int[n];
        while (sc.hasNext()) {
            input[foot++] = sc.nextInt();
        }
        System.out.println(Arrays.toString(input));
    }

    public int reverse(int n, int m) {
        return n * m / 2;
    }

    public int pooker(int n, int[] m){
        return 1;
    }
}
