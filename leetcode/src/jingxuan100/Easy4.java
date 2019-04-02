package jingxuan100;

/**
 * Given an integer array nums, find the
 * contiguous subarray (containing at least one number)
 * which has the largest sum and return its sum
 */
public class Easy4 {
    public static void main(String[] args) {
        Easy4 test = new Easy4();
        test.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});

    }
//    public int maxSubArray(int[] nums) {
//        int maxNum = Integer.MIN_VALUE;
//        int sumNum = 0;
//        for (int i = 0; i < nums.length; i++) {
//            sumNum += nums[i];
//            if (sumNum > maxNum) {
//                maxNum = sumNum;
//            }
//            if (sumNum < 0) {
//                sumNum = 0;
//            }
//
//        }
//        return maxNum;
//    }

    public int maxSubArray(int[] A) {
        int n = A.length;
        int[] dp = new int[n];//dp[i] means the maximum subarray ending with A[i];
        dp[0] = A[0];
        int max = dp[0];

        for(int i = 1; i < n; i++){
            dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
