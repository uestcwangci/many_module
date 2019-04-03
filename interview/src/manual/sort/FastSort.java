package manual.sort;

import java.util.Arrays;

public class FastSort {
    public static void main(String[] args) {
        FastSort test = new FastSort();
        int[] a = new int[]{4, 1, 8, 2, 7, 3, 15, 6, 9, 10, 2, 3};
        test.sort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private void sort(int[] array, int start, int end) {
        if (start <end) {
            int i = start;
            int j = end;
            int soldier = array[start];
            while (i < j) {
                while (i < j && array[j] >= soldier) {
                    j--;
                }
                array[i] = array[j];
                while (i < j && array[i] <= soldier) {
                    i++;
                }
                array[j] = array[i];
            }
//            array[start] = array[i];
            array[i] = soldier;

            sort(array, start, i - 1);
            sort(array, i + 1, end);
        }
    }
}
