import java.util.*;

public class Leetcode0923 {


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


    // [M] 99       Recover Binary Search Tree
    TreeNode replacerx;
    TreeNode replacery;
    TreeNode pre;
    public void recoverTree(TreeNode root) {
        inOrder(root);
        int swap = replacerx.val;
        replacerx.val = replacery.val;
        replacery.val = swap;

    }
    private boolean inOrder(TreeNode curr) {
        if (curr == null) return false;
        if (pre == null && curr.left == null) pre = curr;
        if (inOrder(curr.left)) return true;
        if (curr.val < pre.val) {
            replacery = curr;
            if (replacerx == null) replacerx = pre;
            else return true;
        }
        pre = curr;
        if (inOrder(curr.right)) return true;
        return false;
    }


    // [M] 366      Find Leaves of Binary Tree
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        while (root.left != null || root.right != null) {
            List<Integer> path = new LinkedList<>();
            leaves366(root, path);
            res.add(path);
        }
        res.add(new LinkedList<>(Arrays.asList(root.val)));
        return res;
    }
    private void leaves366(TreeNode curr, List<Integer> path) {
        if (curr.left != null) {
            if (curr.left.left == null && curr.left.right == null) {
                path.add(curr.left.val);
                curr.left = null;
            }
            else {
                leaves366(curr.left, path);
            }
        }
        if (curr.right != null) {
            if (curr.right.left == null && curr.right.right == null) {
                path.add(curr.right.val);
                curr.right = null;
            }
            else leaves366(curr.right, path);
        }
    }


    // [M] 103      Binary Free Zigzag Level Order Traversal
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) return res;
        int state = 1;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerLast(root);

        while (!deque.isEmpty()) {
            List<Integer> subRe = new LinkedList<>();
            int levelSize = deque.size();
            if (state > 0) {
                for (int i = 0; i < levelSize; i++) {
                    TreeNode curr = deque.pollFirst();
                    if (curr.left != null) deque.offerLast(curr.left);
                    if (curr.right != null) deque.offerLast(curr.right);
                    subRe.add(curr.val);
                }
                res.add(subRe);
            }
            else {
                Stack<Integer> stack = new Stack<>();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode curr = deque.pollFirst();
                    if (curr.left != null) deque.offerLast(curr.left);
                    if (curr.right != null) deque.offerLast(curr.right);
                    stack.push(curr.val);
                }
                while (!stack.empty()) subRe.add(stack.pop());
                res.add(subRe);
            }
            state *= -1;
        }
        return res;
    }


    // [M] 718      Maximum Length of Repeated Subarray
    public int findLength(int[] nums1, int[] nums2) {
        int M = nums1.length;
        int N = nums2.length;
        int[][] dp = new int[M][N];
        int maxLen = 0;


        for (int c = 0; c < N; c++) {
            if (nums1[0] == nums2[c]) {
                dp[0][c] = 1;
                maxLen = 1;
            }
        }
        for (int r = 0; r < M; r++) {
            if (nums1[r] == nums2[0]) {
                dp[r][0] = 1;
                maxLen = 1;
            }
        }

        for (int r = 1; r < M; r++) {
            for (int c = 1; c < N; c++) {
                if (nums1[r] == nums2[c]) {
                    dp[r][c] = dp[r - 1][c - 1] + 1;
                    if (dp[r][c] > maxLen) maxLen = dp[r][c];
                }
            }
        }



        return maxLen;
    }




    public static void main(String[] args) {
        Leetcode0923 lc = new Leetcode0923();
        int[] a = {0,0,0,0,0};
        int[] b = {0,0,0,0,0};

        System.out.println(lc.findLength(a, b));



    }
}
