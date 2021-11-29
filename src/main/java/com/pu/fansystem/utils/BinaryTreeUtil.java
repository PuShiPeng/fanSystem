package com.pu.fansystem.utils;


import java.util.*;

/**
 * 二叉树Util
 */
public class BinaryTreeUtil {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 对称二叉树
     */
    public static boolean isSymmetric(TreeNode root) {
        return isSymmetric1(root.left,root.right) || isSymmetric2(root,root);
    }
    /** 递归对称二叉树 **/
    private static boolean isSymmetric1(TreeNode leftNode, TreeNode rightNode){
        if(leftNode == null && rightNode == null)return true;
        if(leftNode == null || rightNode == null)return false;
        return leftNode.val == rightNode.val && isSymmetric1(leftNode.left,rightNode.right) && isSymmetric1(leftNode.right,rightNode.left);
    }
    /** 迭代对称二叉树 **/
    private static boolean isSymmetric2(TreeNode leftNode, TreeNode rightNode){
        Queue<TreeNode> treeQueue = new LinkedList<>();
        treeQueue.offer(leftNode);
        treeQueue.offer(rightNode);
        while(!treeQueue.isEmpty()){
            leftNode = treeQueue.poll();
            rightNode = treeQueue.poll();
            if(leftNode == null && rightNode == null)continue;
            if(leftNode == null || rightNode == null || leftNode.val != rightNode.val)return false;

            treeQueue.offer(leftNode.left);
            treeQueue.offer(rightNode.right);

            treeQueue.offer(leftNode.right);
            treeQueue.offer(rightNode.left);
        }
        return true;
    }

    /**
     * 二叉树的最大深度
     */
    public static int maxDepth(TreeNode root){
        boolean fag = true;
        return fag ? maxDepth1(root) : maxDepth2(root);
    }
    /** 深度优先遍历 **/
    private static int maxDepth1(TreeNode root){
        if(root == null)return 0;
        int left = maxDepth1(root.left);
        int right = maxDepth1(root.right);
        return Math.max(left,right) + 1;
    }
    /** 广度优先遍历 **/
    private static int maxDepth2(TreeNode root){
        if(root == null)return 0;
        Queue<TreeNode> treeQueue = new LinkedList<>();
        treeQueue.offer(root);
        int ans = 0;
        while(!treeQueue.isEmpty()){
            int size = treeQueue.size();
            while(size > 0){
                TreeNode tmpNode = treeQueue.poll();
                if(tmpNode.left != null) treeQueue.offer(tmpNode.left);
                if(tmpNode.right != null) treeQueue.offer(tmpNode.right);
                size--;
            }
            ans++;
        }
        return ans;
    }

    /**
     * 将有序数组转换为二叉搜索树
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        int mid = nums.length / 2;
        int left = mid - 1, right = mid + 1;
        TreeNode root = new TreeNode(nums[mid]);
        if(left >= 0)root.left = sortedArrayToBST(Arrays.copyOf(nums,mid));
        if(right < nums.length)root.right = sortedArrayToBST(Arrays.copyOfRange(nums,right,nums.length));
        return root;
    }

    /**
     * 平衡二叉树
     */
    public static boolean isBalanced(TreeNode root) {
        return balanced(root) >= 0;
    }
    private static int balanced(TreeNode root){
        if(root == null)return 0;
        int left = balanced(root.left);
        int right = balanced(root.right);
        if(Math.abs(left - right) > 1)return -1;
        return Math.max(left,right) + 1;
    }

    /**
     *  前序遍历
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> resPreorder = new ArrayList<>();
        preorder(resPreorder,root);
        return resPreorder;
    }
    private void preorder(List<Integer> resPreorder,TreeNode root){
        if(root == null)return;
        resPreorder.add(root.val);
        if(root.left != null)preorder(resPreorder,root.left);
        if(root.right != null)preorder(resPreorder,root.right);
    }

    /**
     *  后续遍历
     */
    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> resPostOrder = new ArrayList<>();
        postOrder(resPostOrder,root);
        return resPostOrder;
    }
    private void postOrder(List<Integer> resPreorder,TreeNode root){
        if(root == null)return;
        if(root.left != null)postOrder(resPreorder,root.left);
        if(root.right != null)postOrder(resPreorder,root.right);
        resPreorder.add(root.val);
    }
}


