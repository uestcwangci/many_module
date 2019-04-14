package manual.sort;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] a = new int[]{8, 1, 4, 9, 6, 3, 5, 2, 7, 0};
        HeapSort test = new HeapSort();
        test.sort(a);
        System.out.println(Arrays.toString(a));
    }

    private void sort(int[] array) {
        // 从第一个非叶子结点开始调整
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            ToMaxHeap(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            ToMaxHeap(array, 0, i);
        }
    }

    private void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private void ToMaxHeap(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {
            // 若右结点存在且，右结点大，选择是否与右结点交换
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            if (array[k] > array[i]) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        array[i] = temp;
    }
}
