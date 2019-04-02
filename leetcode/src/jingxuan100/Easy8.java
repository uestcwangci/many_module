package jingxuan100;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * If you were only permitted to complete at most one transaction
 * (i.e., buy one and sell one share of the stock),
 * design an algorithm to find the maximum profit.
 *
 * Note that you cannot sell a stock before you buy one.
 */
public class Easy8 {
    public static void main(String[] args) {
        Easy8 test = new Easy8();
        System.out.println(test.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int mProfit = 0;
        int lastProfit = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > lastProfit) {
                if (prices[i] - lastProfit > mProfit) {
                    mProfit = prices[i] - lastProfit;
                }
            }else if (prices[i] < lastProfit) {
                lastProfit = prices[i];
            }
        }
        return mProfit;
    }
}
