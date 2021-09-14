import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode0903 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // [M] 222      Count Complete Tree Nodes
    public int countNodes1(TreeNode root) {
        if (root == null) return 0;
        int count = countNodes1(root.left);
        count += countNodes1(root.right);
        return count + 1;
    }
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = getDepth222(root.left), rightDepth = getDepth222(root.right);
        if (leftDepth == rightDepth)
            return (1 << leftDepth) + countNodes(root.right);
        else return (1 << rightDepth) + countNodes(root.left);
    }
    private int getDepth222(TreeNode root) {
        if (root == null) return 0;
        return getDepth222(root.left) + 1;
    }


    // [E] 110      Balanced Binary Tree
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(getHeight110(root.left) - getHeight110(root.right)) > 1) return false;
        else {
            boolean result = isBalanced(root.left);
            if (!result) return false;
            return isBalanced(root.right);
        }
    }
    public int getHeight110(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = getHeight110(root.left);
        int rightHeight = getHeight110(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }


    // [E] 257      Binary Tree Paths
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new LinkedList<>();
        searchPath257(root, "", result);
        return result;
    }
    private void searchPath257(TreeNode curr, String preStr, List<String> result) {
        String currStr = new String(preStr);
        currStr += curr.val;
        if (curr.left == null && curr.right == null) {
            result.add(currStr);
            return;
        }
        currStr += "->";
        if (curr.left != null) searchPath257(curr.left, currStr, result);
        if (curr.right != null) searchPath257(curr.right, currStr, result);
    }


    // [M] 113      Path Sum II
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans = new LinkedList<>();
        if (root == null) return ans;
        List<Integer> preList = new LinkedList<>();
        calSum(root, ans, preList, 0, targetSum);
        return ans;
    }
    private void calSum(TreeNode curr, List<List<Integer>> ans, List<Integer> preList, int preSum, int targetSum) {
        List<Integer> newList = new LinkedList<>(preList);
        newList.add(curr.val);
        if (curr.left == null && curr.right == null) {
            if (preSum + curr.val == targetSum) ans.add(newList);
            return;
        }
        if (curr.left != null) calSum(curr.left, ans, newList, preSum + curr.val, targetSum);
        if (curr.right != null) calSum(curr.right, ans, newList, preSum + curr.val, targetSum);
    }


    // [E] 112      Path Sum
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        return hasPathSum112(root, 0, targetSum);
    }
    private boolean hasPathSum112(TreeNode curr, int preSum, int targetSum) {
        if (curr.left == null && curr.right == null)
            return preSum + curr.val == targetSum;
        if (curr.left != null && hasPathSum112(curr.left, preSum + curr.val, targetSum))
            return true;
        if (curr.right != null && hasPathSum112(curr.right, preSum + curr.val, targetSum))
            return true;
        else return false;
    }






    public static void main(String[] args) {
    }
}
