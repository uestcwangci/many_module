package toutiaoshixi;

import java.util.*;

public class Demo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer[]> list = new ArrayList<>();
        String s = null;
        String[] sArr = null;

        while ((s = sc.nextLine()).length() != 0) {

            sArr = s.split(" ");
            Integer[] iArr = new Integer[sArr.length];
            for (int i = 0; i < sArr.length; i++) {
                iArr[i] = Integer.parseInt(sArr[i]);
            }
            list.add(iArr);
        }
        if (list.size() == 0) {
            System.out.println(-1);
            return;
        }

        int[][] a = new int[list.size()][list.get(0).length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = list.get(i)[j];
            }
        }

        System.out.println(fun(a));
    }

    private static int fun(int[][] arr) {
        int count = 0;
        int rows = arr.length;
        int columns = arr[0].length;
        Queue<Integer[]> q1 = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 2) {
                    q1.offer(new Integer[]{i, j});
                }
            }
        }

        int row, column;
        boolean flag;
        Integer[] loc;
        Queue<Integer[]> q2 = new LinkedList<>();
        while ((!q1.isEmpty()) || (!q2.isEmpty())) {
            flag = false;
            while (!q1.isEmpty()) {
                loc = q1.poll();
                row = loc[0];
                column = loc[1];
                if (row - 1 >= 0 && arr[row - 1][column] == 1) { //上
                    arr[row - 1][column] = 2;
                    q2.offer(new Integer[]{row - 1, column});
                    flag = true;
                }
                if (column - 1 >= 0 && arr[row][column - 1] == 1) { //左
                    arr[row][column - 1] = 2;
                    q2.offer(new Integer[]{row, column - 1});
                    flag = true;
                }
                if (row + 1 < rows && arr[row + 1][column] == 1) { //下
                    arr[row + 1][column] = 2;
                    q2.offer(new Integer[]{row + 1, column});
                    flag = true;
                }
                if (column + 1 < columns && arr[row][column + 1] == 1) { //右
                    arr[row][column + 1] = 2;
                    q2.offer(new Integer[]{row, column + 1});
                    flag = true;
                }
            }
            if (flag) {
                count++;
                flag = false;
            }
            while (!q2.isEmpty()) {
                loc = q2.poll();
                row = loc[0];
                column = loc[1];
                if (row - 1 >= 0 && arr[row - 1][column] == 1) { //上
                    arr[row - 1][column] = 2;
                    q1.offer(new Integer[]{row - 1, column});
                    flag = true;
                }
                if (column - 1 >= 0 && arr[row][column - 1] == 1) { //左
                    arr[row][column - 1] = 2;
                    q1.offer(new Integer[]{row, column - 1});
                    flag = true;
                }
                if (row + 1 < rows && arr[row + 1][column] == 1) { //下
                    arr[row + 1][column] = 2;
                    q1.offer(new Integer[]{row + 1, column});
                    flag = true;
                }
                if (column + 1 < columns && arr[row][column + 1] == 1) { //右
                    arr[row][column + 1] = 2;
                    q1.offer(new Integer[]{row, column + 1});
                    flag = true;
                }
            }
            if (flag) {
                count++;
                flag = false;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 1) {
                    return -1;
                }
            }
        }
        return count;
    }

}
