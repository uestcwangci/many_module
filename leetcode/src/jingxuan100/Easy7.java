package jingxuan100;

import beans.TreeNode;

public class Easy7 {
    public static void main(String[] args) {
        Easy7 test = new Easy7();
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
}
