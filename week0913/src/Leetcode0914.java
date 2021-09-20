import java.util.Stack;

public class Leetcode0914 {

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

    // [M] 738      Monotone Increasing Digits
    public int monotoneIncreasingDigits(int n) {
        if (n < 10) return n;
        int curr = n;
        int N = 0;
        while (curr > 0) {
            curr /= 10;
            N++;
        }
        int[] data = new int[N];
        N--;
        curr = n;
        int right = curr % 10;
        curr /= 10;
        int left = curr % 10;
        curr /= 10;

        while (N > 0) {
            if (right >= left) data[N] = right;
            else {
                left--;
                for (int i = N; i < data.length; i++) data[i] = 9;
            }
            right = left;
            left= curr % 10;
            curr /= 10;
            N--;
        }
        data[N] = right;


        int res = 0;
        for (int i = 0; i < data.length; i++) res += data[i] * Math.pow(10, data.length - 1 - i);
        return res;
    }


    // [M] 714      Best Time to Buy and Sell Stock with Transaction Fee
    public int maxProfit(int[] prices, int fee) {
        if (prices.length == 1) return 0;
        int profit = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) minPrice = prices[i];

            if (prices[i] <= minPrice + fee) continue;

            if (prices[i] > minPrice + fee) {
                profit += prices[i] - minPrice - fee;
                minPrice = prices[i] - fee;
            }
        }
        return profit;
    }


    // [H] 968      Binary Tree Cameras
    public int minCameraCover(TreeNode root) {
        if (root.left == null && root.right == null) return 1;
        int[] res = new int[1];
        if (findCamera(root, res) == 2) res[0] ++;
        return res[0];
    }
    private int findCamera(TreeNode curr, int[] a) {
        if (curr == null) return 1;

        int left = findCamera(curr.left, a);
        int right = findCamera(curr.right, a);

        if (left == 2 || right == 2) {
            a[0]++;
            return 0;
        }

        if (left == 0 || right == 0) {
            return 1;
        }

        return 2;
    }
    // 0-is camera, 1-is covered, 2-uncovered


    // [M] 376      Wiggle Subsequence
    public int wiggleMaxLength(int[] nums) {
        if (nums.length == 1) return 1;

        int preDif = 0;
        int count = 1;
        int currDif = 0;

        for (int i = 1; i < nums.length; i++) {
            currDif = nums[i] - nums[i - 1];
            if ((currDif > 0 && preDif <= 0) || (currDif < 0 && preDif >= 0)) {
                count++;
                preDif = currDif;
            }
        }

        return count;
    }


    // [E] 509      Fibonacci Number
    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int n_2 = 0, n_1 = 1;
        int result = -1;
        for (int i = 0; i < n - 1; i++) {
            result = n_2 + n_1;
            n_2 = n_1;
            n_1 = result;
        }
        return result;
    }


    // [E] 70       Climbing Stairs
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] steps = new int[n];
        steps[0] = 1;
        steps[1] = 2;
        for (int i = 2; i < n; i++) steps[i] = steps[i - 1] + steps[i - 2];
        return steps[n - 1];
    }



    public static void main(String[] args) {
        Leetcode0914 lc = new Leetcode0914();
//        TreeNode t1 = new TreeNode(4);
//        TreeNode t2 = new TreeNode(5);
//        TreeNode t3 = new TreeNode(6);
//        TreeNode t4 = new TreeNode(7);
//        t1.right = t2;
//        t2.right = t3;
//        t3.right = t4;

        int[] a = {1,17,5,10,13,15,10,5,16,8};
        System.out.println("erewq");
    }
}
