import java.util.*;

public class Leetcode0920 {

    public class TreeNode {
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

    // [M] 198      House Robber
    public int rob1(int[] nums) {
        if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);

        int d_0 = 0;
        int d_1 = nums[0];
        int d_2 = nums[1];
        int max = Math.max(d_1, d_2);

        for (int i = 2; i < nums.length; i++) {
            int newTmp = Math.max(d_0 + nums[i], d_1 + nums[i]);
            if (newTmp > max) max = newTmp;
            d_0 = d_1;
            d_1 = d_2;
            d_2 = newTmp;
        }
        return max;
    }


    // [M] 213      House Robber II
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);
        int t_3 = 0;
        int t_2 = nums[0];
        int t_1 = nums[1];
        int max = Math.max(t_2, t_1);
        for (int j = 2; j < nums.length - 1; j++) {
            int curr = Math.max(t_3, t_2) + nums[j];
            if (curr > max) max = curr;
            t_3 = t_2;
            t_2 = t_1;
            t_1 = curr;
        }
        t_3 = 0;
        t_2 = nums[1];
        t_1 = nums[2];
        max = Math.max(max, Math.max(t_2, t_1));
        for (int j = 3; j < nums.length; j++) {
            int curr = Math.max(t_2, t_3) + nums[j];
            if (curr > max) max = curr;
            t_3 = t_2;
            t_2 = t_1;
            t_1 = curr;
        }
        return max;
    }


    // [M] 256      Paint House
    public int minCost(int[][] costs) {
        int N = costs.length;
        int red = 0, green = 0, blue = 0;

        for (int i = 0; i < costs.length; i++) {
            int tmpRed = Math.min(green, blue) + costs[i][0];
            int tmpGreen = Math.min(red, blue) + costs[i][1];
            int tmpBlue = Math.min(red, green) + costs[i][2];
            red = tmpRed;
            green = tmpGreen;
            blue = tmpBlue;
        }
        return Math.min(red, Math.min(green, blue));
    }


    // [H] 265      Paint House II
    public int minCostII(int[][] costs) {
        int C = costs[0].length;
        int[] colorCosts = new int[C];
        System.arraycopy(costs[0], 0, colorCosts, 0, colorCosts.length);
        for (int i = 1; i < costs.length; i++) {
            int[] tmpColors = new int[C];
            System.arraycopy(colorCosts, 0, tmpColors, 0, colorCosts.length);
            Arrays.sort(tmpColors);
            Deque<Integer> deque = new LinkedList<>();
            deque.offerFirst(tmpColors[0]);
            if (tmpColors.length > 1) deque.offerLast(tmpColors[1]);
            for (int c = 0; c < C; c++) {
                if (deque.peekFirst() != colorCosts[c]) tmpColors[c] = deque.peekFirst() + costs[i][c];
                else tmpColors[c] = deque.peekLast() + costs[i][c];
            }
            colorCosts = tmpColors;
        }

        int minC = colorCosts[0];
        for (int c : colorCosts)
            if (c < minC) minC = c;
        return minC;
    }


    // [M] 238      Product of Array Except Self
    public int[] productExceptSelf(int[] nums) {
        int zeroNum = 0;
        for (int i : nums) if (i == 0) zeroNum++;

        if (zeroNum >= 2) {
            Arrays.fill(nums, 0);
            return nums;
        }
        int product = 1;
        for (int i : nums) if (i != 0) product *= i;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) nums[i] = product;
            else {
                if (zeroNum == 0) nums[i] = product / nums[i];
                else nums[i] = 0;
            }
        }
        return nums;
    }


    // [M] 337      House Robber III
    public int rob(TreeNode root) {
        int[] res = helper337(root);
        return Math.max(res[0], res[1]);
    }
    private int[] helper337(TreeNode curr) {
        // [rob this node, not rob this node]
        if (curr == null) return new int[]{0, 0};

        int[] left = helper337(curr.left);
        int[] right = helper337(curr.right);

        // rob this node
        int r = curr.val + left[1] + right[1];

        // not rob this node
        int n = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{r, n};
    }
    private int dfsRob(TreeNode curr, Map<TreeNode, Integer> map) {
        if (curr == null) return 0;
        if (curr.left == null && curr.right == null) return curr.val;
        if (map.containsKey(curr)) return map.get(curr);

        // rob the current one
        int val1 = curr.val;
        if (curr.left != null) val1 += dfsRob(curr.left .left, map) + dfsRob(curr.left.right, map);
        if (curr.right != null) val1 += dfsRob(curr.right.left, map) + dfsRob(curr.right.right, map);

        // skip the current one
        int val2 = dfsRob(curr.left, map) + dfsRob(curr.right, map);
        int reVal = Math.max(val1, val2);
        map.put(curr, reVal);
        return reVal;
    }


    // [M] 114      Flatten Binary Tree to Linked List
    public void flatten(TreeNode root) {
        helper114(root);
    }
    private TreeNode helper114(TreeNode curr) {
        if (curr == null) return null;
        if (curr.left == null && curr.right == null) return curr;
        TreeNode leftTail = helper114(curr.left);
        TreeNode rightTail = helper114(curr.right);

        if (leftTail == null && rightTail != null) return rightTail;
        else if (rightTail == null && leftTail != null) {
            curr.right = curr.left;
            curr.left = null;
            return leftTail;
        }
        else {
            leftTail.right = curr.right;
            curr.right = curr.left;
            curr.left = null;
            return rightTail;
        }
    }
    private void helper114(TreeNode curr, List<TreeNode> list) {
        if (curr == null) return;
        list.add(curr);
        helper114(curr.left, list);
        helper114(curr.right, list);
    }


    // [M] 1660     Correct a Binary Tree
    public TreeNode correctBinaryTree(TreeNode root) {
        Deque<TreeNode> currL = new LinkedList<>();
        Deque<TreeNode> nextL = new LinkedList<>();
        currL.offerLast(root);

        TreeNode target = null;
        while (!currL.isEmpty()) {
            TreeNode curr = currL.pollFirst();
            if (curr.left != null) nextL.offerLast(curr.left);
            if (curr.right != null) nextL.offerLast(curr.right);
            if (!currL.isEmpty()) {
                boolean found = false;
                for (TreeNode n : currL) {
                    if (curr.right == n) {
                        target = curr;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            else {
                Deque<TreeNode> swap = currL;
                currL = nextL;
                nextL = swap;
            }
        }

        currL.clear();
        currL.offerLast(root);
        while (!currL.isEmpty()) {
            TreeNode curr = currL.pollFirst();
            if (curr.left != null && curr.left == target) {
                curr.left = null;
                break;
            }
            if (curr.right != null && curr.right == target) {
                curr.right = null;
                break;
            }

            if (curr.left != null) currL.offerLast(curr.left);
            if (curr.right != null) currL.offerLast(curr.right);
        }
        return root;
    }






    public static void main(String[] args) {
        Leetcode0920 lc = new Leetcode0920();
        int[] a = {0, 0};
        int[] res = lc.productExceptSelf(a);
        for (int i : res) System.out.print(i + " ");

    }
}
