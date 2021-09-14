import java.util.*;

public class Leetcode0905 {


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    // [E] 530      Minimum Absolute Difference in BST
    public int getMinimumDifference1(TreeNode root) {
        long[] result = new long[1];
        result[0] = Long.MAX_VALUE;
        getMinDifference530(root, Integer.MAX_VALUE, Integer.MAX_VALUE, result);
        return (int)result[0];
    }
    private void getMinDifference530(TreeNode curr, long lowBound, long hiBound, long[] result) {
        long currDifference = Math.min(Math.abs(curr.val - lowBound), Math.abs(curr.val - hiBound));
        if (currDifference < result[0]) result[0] = currDifference;
        if (curr.left != null) getMinDifference530(curr.left, lowBound, curr.val, result);
        if (curr.right != null) getMinDifference530(curr.right, curr.val, hiBound, result);
    }
    TreeNode pre530;
    int result530 = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return 0;
        findMin530(root);
        return result530;
    }
    private void findMin530(TreeNode curr) {
        if (curr == null) return;
        findMin530(curr.left);
        if (pre530 != null)
            result530 = Math.min(result530, curr.val - pre530.val);
        pre530 = curr;
        findMin530(curr.right);
    }


    // [E] 783      Minimum Distance Between BST Nodes
    public int minDiffInBST(TreeNode root) {
        long[] result = new long[1];
        result[0] = Integer.MAX_VALUE;
        getMinDif783(root, Integer.MIN_VALUE, Integer.MAX_VALUE, result);
        return (int)result[0];
    }
    private void getMinDif783(TreeNode curr, long lowBound, long hiBound, long[] result) {
        long currDis = Math.min(Math.abs(curr.val - lowBound), Math.abs(curr.val - hiBound));
        if (currDis < result[0]) result[0] = currDis;
        if (curr.left != null) getMinDif783(curr.left, lowBound, curr.val, result);
        if (curr.right != null) getMinDif783(curr.right, curr.val, hiBound, result);
    }


    // [M] 532      K-diff Pairs in an Array
    public int findPairs(int[] nums, int k) {
        int counter = 0;
        qs523(nums, 0, nums.length - 1);
        for (int i = 0; i < nums.length - 1; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        if (nums[j] - nums[i] == k) counter++;
                        if (nums[j] - nums[i] > k) break;
                    }
                }
            }

        }
        return counter;
    }
    private void qs523(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, j, lo);
        qs523(a, lo, j - 1);
        qs523(a, j + 1, hi);
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }


    // [E] 1394     Find Lucky Integer in an Array
    public int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int luckyNum = -1;
        for (int n : arr) {
            if (map.containsKey(n)) map.replace(n, map.get(n) + 1);
            else map.put(n, 1);
        }
        for (int key : map.keySet())
            if (key == map.get(key) && key > luckyNum) luckyNum = key;
        return luckyNum;
    }


    // [E] 501      Find Mode int Binary Search Tree
    TreeNode pre501;
    int currCnt = 0;
    private class Node501{
        private int frq;
        private int key;
        public Node501(int key, int frq) {
            this.key = key;
            this.frq = frq;
        }
    }
    public int[] findMode(TreeNode root) {
        Deque<Node501> deque = new LinkedList<>();
        findMode501(root, deque);
        int[] result = new int[deque.size()];
        int idx= 0;
        while (!deque.isEmpty()) result[idx++] = deque.pollFirst().key;
        return result;
    }
    private void findMode501(TreeNode curr, Deque<Node501> deque) {
        if (curr == null) return;
        findMode501(curr.left, deque);
        if (pre501 != null) {
            if (curr.val != pre501.val) {       // not same as the last one
                currCnt = 1;
                pre501 = curr;
                if (deque.peekLast().frq == currCnt)
                    deque.offerLast(new Node501(curr.val, currCnt));
            }
            else {
                currCnt++;
                if (currCnt > deque.peekLast().frq) {
                    while (!deque.isEmpty()) deque.pollFirst();
                    deque.offerLast(new Node501(curr.val, currCnt));
                }
                else if (currCnt == deque.peekLast().frq)
                    deque.offerLast(new Node501(curr.val, currCnt));
            }
        }
        else {
            currCnt = 1;
            pre501 = curr;
            deque.offerLast(new Node501(curr.val, currCnt));
        }
        findMode501(curr.right, deque);
    }


    // [M] 151      Reverse Words in a String
    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        char[] data = s.toCharArray();
        int stIdx = 0, edIdx;
        while (stIdx < data.length) {
            while (stIdx < data.length && data[stIdx] == ' ') stIdx++;  // 一开始就是空格的情况
            if (stIdx > data.length - 1) break;                         // 如果已经尾部了那直接退出

            edIdx = stIdx;
            while (edIdx < data.length && data[edIdx] != ' ') edIdx++;
            char[] tmp = new char[edIdx - stIdx];
            System.arraycopy(data, stIdx, tmp, 0, edIdx - stIdx);   // 把data的[stIdx, edIdx - 1] copy到tmp

            stack.push(String.valueOf(tmp));
            stIdx = edIdx;      // 此时edIdx一定是空格或者到了尾部，stIdx直接跳过他就好
        }


        StringBuilder sb = new StringBuilder();     // 用stringbuilder创造最后的输出
        while (!stack.isEmpty()) {                  // 逐步从stack取出单词
            sb.append(stack.pop());
            if (!stack.isEmpty()) sb.append(" ");
        }
        return sb.toString();
    }


    // [M] 236      Lowest Common Ancestor of a Binary Tree
    public TreeNode lowestCommonAncestor(TreeNode curr, TreeNode p, TreeNode q) {
        if (curr == null) return null;

        boolean leftStat = false, rightStat = false;

        TreeNode leftRes = lowestCommonAncestor(curr.left, p, q);
        if (leftRes != null) {
            leftStat = true;
            if (leftRes != p && leftRes != q) return leftRes;
        }
        TreeNode rightRes = lowestCommonAncestor(curr.right, p, q);
        if (rightRes != null) {
            rightStat = true;
            if (rightRes != p && rightRes != q) return rightRes;
        }

        if (leftStat && rightStat) return curr;
        else if (leftStat) {
            if (curr == p || curr == q) return curr;
            else return leftRes;
        }
        else if (rightStat) {
            if (curr == p || curr == q) return curr;
            else return rightRes;
        }
        else {
            if (curr == p) return p;
            else if (curr == q) return q;
            else return null;
        }
    }


    public static void main(String[] args) {
        Leetcode0905 lc = new Leetcode0905();
        String a = "  hello world  ";
        System.out.println(a);
        System.out.println(lc.reverseWords(a));
    }

}
