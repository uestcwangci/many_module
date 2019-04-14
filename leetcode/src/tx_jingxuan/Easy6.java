package tx_jingxuan;

public class Easy6 {
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int maxNum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > maxNum) {
                maxNum = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }
        return maxNum;
    }
}
