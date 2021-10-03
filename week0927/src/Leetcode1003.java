public class Leetcode1003 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    // [H] 124      Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {
        int[] resRoot = postOrderFind(root);
        return Math.max(resRoot[0], Math.max(resRoot[1], resRoot[2]));
    }
    private int[] postOrderFind(TreeNode curr) {
        // return: [Maximum historical bi-directional path, current maximum unidirectional, historical maximum unidirectional]

        if (curr == null) return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        int[] leftRes = postOrderFind(curr.left);
        int[] rightRes = postOrderFind(curr.right);

        // First, calculate the maximum historical bi-directional path
        int currBiDirection = curr.val;
        if (leftRes[1] > 0) currBiDirection += leftRes[1];
        if (rightRes[1] > 0) currBiDirection += rightRes[1];
        currBiDirection = Math.max(currBiDirection, Math.max(leftRes[0], rightRes[0]));

        // Second, calculate the current unidirectional path
        int currUniDirection = curr.val;
        currUniDirection += Math.max(0, Math.max(leftRes[1], rightRes[1]));

        // Third, calculate the maximum historical unidirectional path
        int hisUniDirection = Math.max(currUniDirection, Math.max(leftRes[2], rightRes[2]));
        return new int[] {currBiDirection, currUniDirection, hisUniDirection};
    }




    public static void main(String[] args) {

    }
}
