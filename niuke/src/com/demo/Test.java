package com.demo;


import beans.TreeNode;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        TreeNode root = CreatNewTree();
    }

    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        boolean[] isRepeat = new boolean[matrix.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (judge(matrix, rows, cols, i, j, isRepeat, str, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean judge(char[] matrix, int rows, int cols, int i, int j, boolean[] flag, char[] str, int index) {
        int k = i * rows + j;
        if (i < 0 || i >= rows || j < 0 || j >= cols || matrix[k] != str[index] || flag[k]) {
            return false;
        }
        if (index == str.length - 1) {
            return true;
        }
        flag[k] = true;
        if (judge(matrix, rows, cols, i + 1, j, flag, str, index + 1)
                || judge(matrix, rows, cols, i - 1, j, flag, str, index + 1)
                || judge(matrix, rows, cols, i, j + 1, flag, str, index + 1)
                || judge(matrix, rows, cols, i, j - 1, flag, str, index + 1)) {
            return true;
        }
        flag[k] = false;
        return false;
    }


    private static TreeNode CreatNewTree() {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        TreeNode t8 = new TreeNode(8);
        TreeNode t9 = new TreeNode(9);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        t4.left = t8;
        t4.right = t9;
        return t1;
    }




}
