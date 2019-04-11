package manual.sort;

public class QuickSortRandom {
    public static void main(String[] args) {
        int[] a = new int[]{8, 1, 4, 9, 6, 3, 5, 2, 7, 0};
        QuickSortRandom test = new QuickSortRandom();
        test.sort(a);

    }

    public void sort(int[] array) {
        if (array.length > 0) {
            innerFun(array, 0, array.length - 1);
        }
    }

    private void innerFun(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int i, j;
        i = start;
        j = end;
        int soldier = array[i];
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
        array[i] = soldier;
        innerFun(array, start, i - 1);
        innerFun(array, i + 1, end);
    }
}
