import java.util.*;

public class Leetcode0921 {


    // [E] 121      Best Time to Buy and Sell Stock
    public int maxProfit1(int[] prices) {
        int[][] dp = new int[2][prices.length];
        dp[0][0] = 0;
        dp[1][0] = -prices[0];

        for (int j = 1; j < prices.length; j++) {
            if (prices[j] <= -dp[1][j - 1]) {
                dp[1][j] = -prices[j];
                dp[0][j] = dp[0][j - 1];
            }
            else {
                dp[1][j] = dp[1][j - 1];
                dp[0][j] = Math.max(dp[0][j - 1], prices[j] + dp[1][j - 1]);
            }
        }
        return dp[0][prices.length - 1];
    }


    // [M] 122      Best Time to Buy and Sell Stock II
    public int maxProfit2(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++)
            if (prices[i] - prices[i - 1] > 0) profit += prices[i] - prices[i - 1];
        return profit;
    }


    // [H] 123      Best Time to Buy and Sell Stock III
    public int maxProfit(int[] prices) {
        int s0 = 0, s1 = -prices[0], s2 = 0, s3 = -prices[0], s4 = 0;

        for (int i = 1; i < prices.length; i++) {
            int tmp1 = Math.max(s0 - prices[i], s1);
            int tmp2 = Math.max(s2, s1 + prices[i]);
            int tmp3 = Math.max(s3, s2 - prices[i]);
            int tmp4 = Math.max(s4, s3 + prices[i]);
            s1 = tmp1;
            s2 = tmp2;
            s3 = tmp3;
            s4 = tmp4;
        }

        return Math.max(s2, s4);
    }


    // [H] 188      Best Time to Buy and Sell Stock IV
    public int maxProfit(int k, int[] prices) {
        if (prices.length <= 1) return 0;
        int[] dp = new int[1 + 2 * k];
        // for the ith prince and jth transaction:
        // dp[i][2 * j - 1] = MAX{dp[i - 1][2 * j - 1], dp[i - 1][2 * j - 2] - price[i]}
        // dp[i][2 * j] = MAX{dp[i - 1][2 * j], dp[i - 1][2 * j - 1] + prince[i]}
        for (int i = 1; i <= k; i++) dp[2 * i - 1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            for (int j = k; j > 0; j--) {
                dp[2 * j] = Math.max(dp[2 * j], dp[2 * j - 1] + prices[i]);
                dp[2 * j - 1] = Math.max(dp[2 * j - 1], dp[2 * j - 1 - 1] - prices[i]);
            }
        }
        int max = 0;
        for (int i = 1; i <= k; i++) max = Math.max(max, dp[2 * i]);
        return max;
    }


    // [M] 3        Longest SubString Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        int maxLen = 1;
        int[] indices = new int[256];
        Arrays.fill(indices, -1);
        int stIdx = 0, edIdx = 0;

        while (stIdx < s.length()) {
            while (edIdx < s.length() && indices[s.charAt(edIdx)] < stIdx) {
                indices[s.charAt(edIdx)] = edIdx;
                edIdx++;
            }
            if (edIdx == s.length()) {
                if (edIdx - stIdx > maxLen) maxLen = edIdx - stIdx;
                break;
            }
            if (edIdx - stIdx > maxLen) maxLen = edIdx - stIdx;
            stIdx = indices[s.charAt(edIdx)] + 1;
            indices[s.charAt(edIdx)] = edIdx;
            edIdx = edIdx + 1;
        }
        return maxLen;
    }


    // [M] 340      Longest Substring with At Most K Distinct Characters
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        if (s.length() == 1) return 1;
        int maxLen = 1;
        int stIdx = 0;
        Set<Character> set = new HashSet<>();
        set.add(s.charAt(0));

        for (int edIdx = 1; edIdx < s.length(); edIdx++) {
            char curr = s.charAt(edIdx);
            if (!set.contains(curr)){
                if (set.size() < k) {
                    set.add(curr);
                    if (edIdx - stIdx + 1 > maxLen) maxLen = edIdx - stIdx + 1;
                }
                else {
                    set.clear();
                    set.add(curr);
                    int curSt = edIdx - 1;
                    while (true) {
                        if (!set.contains(s.charAt(curSt)) && set.size() >= k) break;
                        set.add(s.charAt(curSt));
                        curSt--;
                    }
                    stIdx = curSt + 1;
                }
            }
            if (edIdx - stIdx + 1 > maxLen) maxLen = edIdx - stIdx + 1;
        }
        return maxLen;
    }


    // [M] 159      Longest Substring with At Mosst Two Distinct Characters
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() <= 2) return s.length();

        int maxLen = 1;
        Deque<Node159> deque = new LinkedList<>();
        int stIdx = 0;
        deque.addLast(new Node159(s.charAt(0), 0));

        for (int edIdx = 1; edIdx < s.length(); edIdx++) {
            char curr = s.charAt(edIdx);
            if (deque.peekFirst().val == curr) deque.peekFirst().lastIdx = edIdx;
            else if (deque.peekLast().val == curr) deque.peekLast().lastIdx = edIdx;
            else if (deque.size() < 2) deque.addLast(new Node159(curr, edIdx));
            else {
                int curstIdx;
                if (deque.peekLast().lastIdx < deque.peekFirst().lastIdx) {
                    curstIdx = deque.peekLast().lastIdx + 1;
                    deque.pollLast();
                }
                else {
                    curstIdx = deque.peekFirst().lastIdx + 1;
                    deque.pollFirst();
                }
                stIdx = curstIdx;
                deque.offerLast(new Node159(curr, edIdx));
            }
//            System.out.println("st:" + stIdx + "  ed:" + edIdx);
            if (edIdx - stIdx + 1 > maxLen) maxLen = edIdx - stIdx + 1;
        }
        return maxLen;
    }
    static class Node159 {
        private char val;
        private int lastIdx;
        public Node159(char val, int idx) {
            this.val = val;
            this.lastIdx = idx;
        }
    }


    // [M] 5        Longest Palindromic Substring
    public String longestPalindrome(String s) {
        if (s.length() == 1) return s;
        String res = String.valueOf(s.charAt(0));

        for (int i = 0; i < s.length(); i++) {
            if (res.length() > (s.length() - i)) break;
            for (int j = s.length() - 1; j >= i + res.length(); j--) {
                if (subRe(s, i, j)) {
                    res = s.substring(i, j + 1);
                    break;
                }
            }
        }
        return res;
    }
    private boolean subRe(String s, int lo, int hi) {
        while (lo < hi) {
            if (s.charAt(lo) != s.charAt(hi)) return false;
            lo++;
            hi--;
        }
        return true;
    }




    public static void main(String[] args) {
        Leetcode0921 lc = new Leetcode0921();
        int[] a = {3,2,6,5,0,3};
        String b = "cbbd";
        System.out.println(lc.longestPalindrome(b));
    }
}
