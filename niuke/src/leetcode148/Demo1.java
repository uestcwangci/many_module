package leetcode148;

import beans.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along
 * the shortest path from the root node down to the nearest leaf node.
 */
public class Demo1 {
    public static void main(String[] args) {
        Demo1 test = new Demo1();
        TreeNode root = CreatNewTree();
        System.out.println(test.run(root));
    }

/*    public int run(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left == null || root.right == null) {
            return Math.max(run(root.left), run(root.right)) + 1;
        }
        return Math.min(run(root.left), run(root.right)) + 1;
    }*/

    public int run(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> Q = new LinkedList<>();
        Q.offer(root);
        int depth = 1;

        while(!Q.isEmpty()){
            int size = Q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = Q.poll();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    Q.add(node.left);
                }
                if (node.right != null) {
                    Q.add(node.right);
                }
            }
            depth++;
        }
        return depth;
    }

    private static TreeNode CreatNewTree() {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t3.right = t5;
        t4.left = t6;
        return t1;
    }
}
