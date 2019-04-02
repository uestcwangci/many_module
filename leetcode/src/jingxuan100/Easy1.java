package jingxuan100;

import java.util.*;

public class Easy1 {
    public static void main(String[] args) {
        Easy1 test = new Easy1();
        System.out.println(Arrays.toString(test.twoSum(new int[]{-3, 4, 3, 90}, 0)));
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > target) {
                continue;
            }
            if (map.containsKey(nums[i])) {
                result[0] = map.get(nums[i]);
                result[1] = i;
            } else {
                map.put(target - nums[i], i);
            }
        }
        return result;
    }
}
