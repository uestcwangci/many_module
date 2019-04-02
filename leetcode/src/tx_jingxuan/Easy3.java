package tx_jingxuan;

/**最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 */
public class Easy3 {
    public static void main(String[] args) {
        Easy3 test = new Easy3();
        System.out.println(test.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        int end = 0;
        while (true) {
            for (int i = 0; i < strs.length - 1; i++) {
                try {
                    if (strs[i].charAt(end) != (strs[i + 1].charAt(end))) {
                        return strs[0].substring(0, end);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    return strs[0].substring(0, end);
                }
                if (i == strs.length - 2) {
                    end++;
                }
            }
        }
    }
}
