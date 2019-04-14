package tx_jingxuan;

public class Easy5 {
    public int removeDuplicates(int[] nums) {
        int before = 0;
        int after = 0;
        for (; before < nums.length - 1; before++) {
            if (nums[before] != nums[before + 1]) {
                after++;
                nums[after] = nums[before + 1];
            }
        }
        return after + 1;
    }
}
