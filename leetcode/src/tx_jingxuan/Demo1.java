package tx_jingxuan;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 */
public class Demo1 {
    public static void main(String[] args) {
        Demo1 test = new Demo1();
        System.out.println(test.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 存最大的数
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() { // 存最小的数
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int n = nums1.length;
        int m = nums2.length;
        int i, j;
        i = j = 0;
        if (n > 0) {
            maxHeap.add(nums1[i++]);
        } else {
            maxHeap.add(nums2[j++]);
        }

        return 0.0;
    }
}
