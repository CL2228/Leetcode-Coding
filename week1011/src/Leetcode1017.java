import java.util.*;

public class Leetcode1017 {

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

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [M] 437      Path Sum III
    int num437 = 0;
    public int pathSum(TreeNode root, int targetSum) {
        /**
         * this solution uses postorder traversal with a recursion function return the path sum of that subtree
         * the map: key: path sum value, value: frequency
         */
        if (root == null) return 0;
        postOrderTraversal(root, targetSum);
        return num437;
    }
    private Map<Integer, Integer> postOrderTraversal(TreeNode curr, int targetSum) {
        if (curr.left == null && curr.right == null) {          // if this node is a leaf, return its value with freq 1
            if (curr.val == targetSum) num437++;
            Map<Integer, Integer> res = new HashMap<>();
            res.put(curr.val, 1);
            return res;
        }

        Map<Integer, Integer> res = new HashMap<>();            // define a map and its target for this level
        int levelTarget = targetSum - curr.val;

        if (curr.left != null) {                                // visit the left
//            Map<Integer, Integer> leftRes = postOrderTraversal(curr.left, targetSum);
            fillResMap(levelTarget, postOrderTraversal(curr.left, targetSum), res, curr.val);
        }
        if (curr.right != null) {
//            Map<Integer, Integer> rightRes = postOrderTraversal(curr.right, targetSum);
            fillResMap(levelTarget, postOrderTraversal(curr.right, targetSum), res, curr.val);
        }

        if (res.containsKey(curr.val)) res.replace(curr.val, res.get(curr.val) + 1);
        else res.put(curr.val, 1);
        if (curr.val == targetSum) num437++;
        return res;
    }
    private void fillResMap(int levelTarget, Map<Integer, Integer> subRes, Map<Integer, Integer> res, int currVal) {
        if (subRes.containsKey(levelTarget)) num437 += subRes.get(levelTarget);
        for (Map.Entry<Integer, Integer> e : subRes.entrySet()) {
            int nextKey = currVal + e.getKey();
            if (res.containsKey(nextKey)) res.replace(nextKey, res.get(nextKey) + e.getValue());
            else res.put(nextKey, e.getValue());
        }
    }


    // [H] 10       Regular Expression Matching
    public boolean isMatch1(String support, String query) {
        /*
          this solution uses dynamic programing, a boolean[][] array
          the dp[r][c] represents whether query[0: r - 1] can match support[0: c - 1]

          TO determine the dp[r][c]:
          1. if Q[r-1] == S[c-1],
               dp[r][c] = dp[r-1][c-1]   if Q{0: r-2} matches S{0: c-2}, this can match

          2. if Q[r-1] == '.', this is a multi-use factor,
               thus dp[r][c] = dp[r-1][c-1], similar to 1.

          3. if Q[r-1] == '*', this is the most tricky case,
               a. if Q[r-2] == S[c-1], means that the factor matches.
                   dp[r][c] = dp[r-1][c](use this multiplier only once) || dp[r-2][c] (not use it at all)
                   dp[r][c] = dp[r][c] || dp[r][c-1]   (use this multiplier more than once, ONLY when S[c-2] = S[c-1] OR Q[r-2] == '*'
               b. else, this header doesn't match,  dp[r][c] = dp[r-2][c] (try not to use it at all

          4. else, Q[r-1] is not a special character and it does not match, just let dp[r][c] unchanged (false)
         */
        int M = query.length(), N = support.length();
        boolean[][] dp = new boolean[M + 1][N + 1];

        dp[0][0] = true;                        // initialize the dp array
        for (int r = 1; r < M + 1; r++)
            if (query.charAt(r - 1) == '*') dp[r][0] = dp[r - 2][0];

        for (int r = 1; r < M + 1; r++) {
            char currQ = query.charAt(r - 1);
            for (int c = 1; c < N + 1; c++) {
                char currS = support.charAt(c - 1);

                if (currQ == currS) dp[r][c] = dp[r - 1][c - 1];                // the characters match (not special characters)
                else {
                    if (currQ == '.') dp[r][c] = dp[r - 1][c - 1];              // special factor
                    else if (currQ == '*') {                                    // it is a multiplier
                        if (query.charAt(r - 2) == currS || query.charAt(r - 2) == '.') {       // the header fits
                            dp[r][c] = (dp[r - 2][c] || dp[r - 1][c]);                        // it can use it once, or dont use it

                            if (c > 1 && (support.charAt(c - 2) == currS || query.charAt(r - 2) == '.'))         // use it more than once
                                dp[r][c] = dp[r][c] || dp[r][c - 1];
                        }
                        else dp[r][c] = dp[r - 2][c];                           // if the header does not fit, try not to use it
                    }
                }
            }
        }

//        for (int i = 0; i < M + 1; i++) {
//            boolean[] row = dp[i];
//            if (i == 0)
//                System.out.print("#  ");
//            else System.out.print(query.charAt(i - 1) + "  ");
//            for (boolean e : row) {
//                System.out.print(e + " ");
//            }
//            System.out.println();
//        }

        return dp[M][N];
    }


    // [H] 44       Wildcard Matching
    public boolean isMatch(String support, String query) {
        /*
        this solution uses dynamic programming, using a boolean[][]
        dp[r][c] represents whether Q{0: r-1} matches S{0: c-1}

        initialization:
        dp[0][0] = true, if Q[r - 1] == '*', dp[r][0] = dp[r - 1][0]

        for Each r, c

        1. if Q[r-1] == S[c-1] OR Q[r-1] == '?', dp[r][c] = dp[r-1][c-1]

        2. if Q[r-1] == '*',
            dp[r][c] = dp[r-1][c-1](start to use super matcher) || dp[r-1][c] (not to use super matcher) || dp[r][c-1] (use super matcher more than once)

       time complexity: O(M*N)
       space complexity: O(M*N)


       can improve the dp array to use a one-dimensional one, space complexity: O(N)
         */

        int M = query.length(), N = support.length();
        boolean[] dp = new boolean[N + 1];              // dp array
        dp[0] = true;

        for (int r = 0; r < M; r++) {
            boolean[] tmpDP = new boolean[N + 1];       // initialize the first element of the dp array
            char currQ = query.charAt(r);
            if (currQ == '*') tmpDP[0] = dp[0];

            for (int c = 1; c < N + 1; c++) {
                char currS = support.charAt(c - 1);
                if (currQ == '?' || currQ == currS) tmpDP[c] = dp[c - 1];       // match the character
                if (currQ == '*') tmpDP[c] = tmpDP[c - 1] || dp[c - 1] || dp[c];    // super matcher
            }
            dp = tmpDP;
        }
        return dp[N];
    }


    // [M] 57       Insert Interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        Deque<int[]> deque = new LinkedList<>();
        for (int i = 0; i < intervals.length; i++) {
            if (!isOverlap(newInterval, intervals[i]))
                deque.offerLast(intervals[i]);
            else merge(newInterval, intervals[i]);
        }

        int[][] res = new int[deque.size() + 1][];
        int idx = 0;
        while (!deque.isEmpty()) res[idx++] = deque.pollFirst();
        res[idx] = newInterval;

        for (int i = res.length - 1; i > 0; i--) {
            if (res[i][0] < res[i - 1][0]) {
                int[] swap = res[i];
                res[i] = res[i - 1];
                res[i - 1] = swap;
            }
            else break;
        }

        return res;
    }
    private boolean isOverlap(int[] a, int[] b) {
        return a[1] >= b[0] && b[1] >= a[0];
    }
    private void merge(int[] a, int[] b) {
        int tmp1 = Math.min(a[0], b[0]);
        int tmp2 = Math.max(a[1], b[1]);
        a[0] = tmp1;
        a[1] = tmp2;
    }


    // [E] 58       Length of Last Word
    public int lengthOfLastWord(String s) {
        int edIdx = s.length() - 1;
        while (edIdx >= 0 && s.charAt(edIdx) == ' ') edIdx--;
        if (edIdx < 0) return 0;
        int stIdx = edIdx - 1;
        while (stIdx >= 0 && s.charAt(stIdx) != ' ') stIdx--;
        return edIdx - stIdx;
    }


    // [M] 48       Rotate Image
    public void rotate(int[][] matrix) {
        int R, C;
        if (matrix.length % 2 == 0) {
            R = matrix.length / 2;
            C = matrix.length / 2;
        }
        else {
            R = matrix.length / 2;
            C = matrix.length / 2 + 1;
        }
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) subRotate(matrix, r, c);
        }

    }
    private void subRotate(int[][] matrix, int r, int c) {
        int lastVal = matrix[matrix.length - 1 - c][r];
        for (int i = 0; i < 4; i++) {
            int tmpLastVal = matrix[r][c];
            matrix[r][c] = lastVal;
            lastVal = tmpLastVal;

            int tmpC = matrix.length - 1 - r;
            r = c;
            c = tmpC;
        }
    }


    // [M] 61       Rotate List
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null) return head;
        Deque<ListNode> deque = new LinkedList<>();
        ListNode curr = head;
        while (curr != null) {
            deque.offerLast(curr);
            curr = curr.next;
        }
        int changeNum = k % deque.size();
        for (int i = 0; i < changeNum; i++) deque.offerFirst(deque.pollLast());

        ListNode resHead = deque.pollFirst();
        curr = resHead;
        while (!deque.isEmpty()) {
            curr.next = deque.pollFirst();
            curr = curr.next;
        }
        curr.next = null;
        return resHead;

    }


    // [M] 725      Split Linked List in Parts
    public ListNode[] splitListToParts(ListNode head, int k) {
        int listLen = getLen(head);
        int based = listLen / k;
        int remain = listLen % k;
        int[] segLen = new int[k];
        Arrays.fill(segLen, based);
        for (int i = 0; i < remain; i++) segLen[i]++;

        ListNode[] res = new ListNode[k];
        ListNode curr = head;
        for (int i = 0; i < k; i++) {
            res[i] = curr;
            curr = segment(curr, segLen[i]);
        }
        return res;
    }
    private int getLen(ListNode head) {
        int cnt = 0;
        while (head != null) {
            head = head.next;
            cnt++;
        }
        return cnt;
    }
    private ListNode segment(ListNode head, int len) {
        if (len == 0) return null;
        ListNode curr = head;
        for (int i = 0; i < len - 1; i++) {
            curr = curr.next;
        }
        ListNode preNext = curr.next;
        curr.next = null;
        return preNext;
    }








    public static void main(String[] args) {
        Leetcode1017 lc = new Leetcode1017();
        System.out.println(lc.isMatch("abcdede", "ab.*de"));
    }

}
