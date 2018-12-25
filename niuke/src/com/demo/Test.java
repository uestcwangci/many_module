package com.demo;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        TreeNode t1 = new TreeNode(10);
        TreeNode t2 = new TreeNode(6);
        TreeNode t3 = new TreeNode(14);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(8);
        TreeNode t6 = new TreeNode(12);
        TreeNode t7 = new TreeNode(16);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        TreeNode t = test.Convert(t1);

    }

    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null){
            return null;
        }
        ArrayList<TreeNode> L = new ArrayList<>();
        FindMinTreeNode(pRootOfTree, L);
        int n = L.size();
        for (int i = 0; i < n - 1; i++) {
            L.get(i).right = L.get(i + 1);
            L.get(i + 1).left = L.get(i);
        }

        return L.get(0);

    }

    private void FindMinTreeNode(TreeNode pRootOfTree, ArrayList<TreeNode> L){
        //二叉搜索树中根遍历得到顺序序列
        if (pRootOfTree != null) {
            FindMinTreeNode(pRootOfTree.left, L);
            L.add(pRootOfTree);
            FindMinTreeNode(pRootOfTree.right, L);
        }
    }



}