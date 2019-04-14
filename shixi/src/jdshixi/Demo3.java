package jdshixi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] sArr = sc.nextLine().split(" ");
        int[] a = new int[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            a[i] = Integer.parseInt(sArr[i]);
        }
        List<String[]> list = new ArrayList<>();
        for (int i = 0; i < a[1]; i++) {
            list.add(sc.nextLine().split(" "));
        }
        fun(a, list);
    }

    private static void fun(int[] a, List<String[]> list) {
        int dots = a[0];
        int lines = a[1];
        int[][] line = new int[lines][2];
        for (int i = 0; i < lines; i++) {
            line[i][0] = Integer.parseInt(list.get(i)[0]) - 1;
            line[i][1] = Integer.parseInt(list.get(i)[1]) - 1;
        }
        int[][] guanxi = new int[dots][dots];
        for (int i = 0; i < lines; i++) {
            guanxi[line[i][1]][line[i][0]] = 1;
        }
        System.out.println(Arrays.deepToString(guanxi));
        for (int i = 0; i < dots; i++) {
            for (int j = 0; j < dots; j++) {
                if (guanxi[i][j] == 1) {
                    for (int k = 0; k < dots; k++) {
                        guanxi[i][k] = guanxi[i][k] | guanxi[j][k];
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(guanxi));
        int[] len = new int[dots];
        for (int i = 0; i < dots; i++) {
            for (int j = 0; j < dots; j++) {
                if (guanxi[i][j] == 1) {
                    len[i]++;
                }
            }
        }

        int[] paixu = new int[dots];
        for (int i = 0; i < dots; i++) {
            paixu[i] = i + 1;
        }
        System.out.println(Arrays.toString(len));

        sort(len, paixu);
        System.out.println(Arrays.toString(paixu));

    }

    private static void sort(int[] len, int[] paixu) {
        boolean flag = true;
        while (flag) {
            int temp;
            for (int i = 0; i < len.length - 1; i++) {
                flag = false;
                for (int j = 0; j < len.length - 1; j++) {
                    if (len[j + 1] > len[j]) {
                        temp = len[j];
                        len[j] = len[j + 1];
                        len[j + 1] = temp;
                        flag = true;
                        temp = paixu[j];
                        paixu[j] = paixu[j + 1];
                        paixu[j + 1] = temp;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        }

    }
}
