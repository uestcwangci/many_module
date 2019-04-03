package manual.sort;

import java.util.LinkedList;
import java.util.List;

public class BucketSort {
    public static void main(String[] args) {
        BucketSort test = new BucketSort();
        int[] a = new int[]{4, 1, 8, 2, 7, 3, 15, 6, 9, 10, 2, 3};
        test.sort(a, 1, 15);
    }

    private void sort(int[] array, int min, int max) {
        int[] buckets = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            buckets[array[i] - min]++;
        }
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < buckets.length; i++) {
            while (buckets[i]-- > 0) {
                list.add(i + min);
            }
        }
        System.out.println(list);
    }
}
