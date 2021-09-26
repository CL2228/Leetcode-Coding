import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Leetcode0925 {



    // [M] 1035     Uncrossed Lines
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int N = nums2.length;
        int maxLen = 0;
        int[] dp = new int[N + 1];
        for (int i = 0; i < nums1.length; i++) {
            int[] tmpDP = new int[N + 1];
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    tmpDP[j + 1] = dp[j] + 1;
                    maxLen = Math.max(maxLen, tmpDP[j + 1]);
                }
                else tmpDP[j + 1] = Math.max(dp[j + 1], tmpDP[j]);
            }
            dp = tmpDP;
        }
        return maxLen;
    }


    // [E] 53       Maximum Subarray
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = Math.max(nums[i], nums[i - 1] + nums[i]);
            max = Math.max(max, nums[i]);
        }
        return max;
    }


    // [M] 152      Maximum Product Subarray
    public int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length + 1][2];

        for (int i = 1; i < nums.length + 1; i++) {
            if (nums[i - 1] > 0) {
                dp[i][0] = Math.max(nums[i - 1], dp[i - 1][0] * nums[i - 1]);
                max = Math.max(max, dp[i][0]);
                dp[i][1] = nums[i - 1] * dp[i - 1][1];
            }
            else if (nums[i - 1] < 0){
                dp[i][1] = Math.min(nums[i - 1], nums[i - 1] * dp[i - 1][0]);
                max = Math.max(max, dp[i][1]);
                dp[i][0] = nums[i - 1] * dp[i - 1][1];
                if (i - 1 > 0 && nums[i - 2] != 0) max = Math.max(max, dp[i][0]);
            }
            else max = Math.max(max, 0);
        }
        // for (int[] d : dp) {
        //     for (int i : d) System.out.print(i + " ");
        //     System.out.println();
        // }
        return max;
    }


    // [E] 392      Is Subsequence
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return false;
        int sIdx = 0;
        for (int tIdx = 0; tIdx < t.length(); tIdx++) {
            if (s.charAt(sIdx) == t.charAt(tIdx)) {
                sIdx++;
                if (sIdx == s.length()) return true;
            }
        }
        return false;
    }


    // [H] 115      Distinct Subsequences
    public int numDistinct(String s, String t) {
        // s is the query, t is the key
        int N = t.length(), M = s.length();
        int[][][] dp = new int[M + 1][N + 1][2];
        for (int r = 0; r < M + 1; r++) dp[r][0][1] = 1;
        for (int c = 0; c < N + 1; c++) dp[0][c][1] = 1;

        for (int j = 1; j < N + 1; j++) {
            char cK = t.charAt(j - 1);

            for (int i = 1; i < M + 1; i++){
                char cQ = s.charAt(i - 1);

                if (cK != cQ) {
                    if (dp[i - 1][j][0] > dp[i][j - 1][0]) {
                        dp[i][j][0] = dp[i - 1][j][0];
                        dp[i][j][1] = dp[i - 1][j][1];
                    }
                    else {
                        dp[i][j][0] = dp[i][j - 1][0];
                        dp[i][j][1] = dp[i][j - 1][1];
                    }
                }
                else {
                    int subLen = dp[i - 1][j - 1][0] + 1;
                    dp[i][j][0] = subLen;

                    if (subLen == dp[i - 1][j][0]) dp[i][j][1] = dp[i - 1][j - 1][1] + dp[i - 1][j][1];
                    else dp[i][j][1] = dp[i - 1][j - 1][1];
                }
            }
        }
        if (dp[M][N][0] == N) return dp[M][N][1];
        else return 0;
    }


    // [M] 583      Delete Operation for Two Strings
    public int minDistance1(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] dp = new int[M + 1][N + 1];

        for (int c = 1; c < N + 1; c++) dp[0][c] = c;
        for (int r = 1; r < M + 1; r++) dp[r][0] = r;

        for (int i = 1; i < M + 1; i++) {
            char c1 = word1.charAt(i - 1);

            for (int j = 1; j < N + 1; j++) {
                char c2 = word2.charAt(j - 1);

                if (c1 == c2) dp[i][j] = dp[i - 1][j - 1];
                else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1,
                            Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 2));
                }
            }
        }

//        for (int[] d : dp) {
//            for (int i : d) System.out.print(i + " ");
//            System.out.println();
//        }

        return dp[M][N];
    }


    // [M] 712      Minimum ASCII Delete Sum for Two Strings
    public int minimumDeleteSum(String s1, String s2) {
        int M = s1.length(), N = s2.length();
        int[][] dp = new int[M + 1][N + 1];

        for (int c = 1; c < N + 1; c++) dp[0][c] = dp[0][c - 1] + (int)s2.charAt(c - 1);
        for (int r = 1; r < M + 1; r++) dp[r][0] = dp[r - 1][0] + (int)s1.charAt(r - 1);

        for (int i = 1; i < M + 1; i++) {
            char c1 = s1.charAt(i - 1);

            for (int j = 1; j < N + 1; j++) {
                char c2 = s2.charAt(j - 1);

                if (c1 == c2) dp[i][j] = dp[i - 1][j - 1];
                else {
                    dp[i][j] = Math.min(dp[i - 1][j] + (int)c1,
                            Math.min(dp[i][j - 1] + (int)c2, dp[i - 1][j - 1] + (int)c1 + (int)c2));
                }
            }
        }

//        for (int[] d : dp) {
//            for (int i : d) System.out.print(i + " ");
//            System.out.println();
//
//        }
        return dp[M][N];
    }


    // [M] 1029     Two City Scheduling
    public int twoCitySchedCost(int[][] costs) {
        int N = costs.length / 2;
        PriorityQueue<Node1029> p1 = new PriorityQueue<>(N,(a, b) -> Integer.compare(a.cB, b.cB));
        PriorityQueue<Node1029> p2 = new PriorityQueue<>(N, (a, b) -> Integer.compare(a.cA, b.cA));
        int totalCost = 0;


        for (int i = 0; i < costs.length; i++) {
            if (costs[i][0] < costs[i][1]) {
                if (p1.size() < N) {
                    p1.add(new Node1029(costs[i][0], costs[i][1]));
                    totalCost += costs[i][0];
                }
                else {
                    Node1029 peek = p1.peek();
                    if (peek.cB + costs[i][0] < peek.cA + costs[i][1]) {
                        peek = p1.poll();
                        totalCost -= peek.cA;
                        p2.add(peek);
                        totalCost += peek.cB;

                        p1.add(new Node1029(costs[i][0], costs[i][1]));
                        totalCost += costs[i][0];
                    }
                    else {
                        p2.add(new Node1029(costs[i][0], costs[i][1]));
                        totalCost += costs[i][1];
                    }

                }
            }
            else {
                if (p2.size() < N) {
                    p2.add(new Node1029(costs[i][0], costs[i][1]));
                    totalCost += costs[i][1];
                }
                else {
                    Node1029 peek = p2.peek();
                    if (peek.cA + costs[i][1] < peek.cB + costs[i][0]) {
                        peek = p2.poll();
                        totalCost = totalCost - peek.cB + peek.cA;
                        p1.add(peek);
                        p2.add(new Node1029(costs[i][0], costs[i][1]));
                        totalCost += costs[i][1];
                    }
                    else {
                        p1.add(new Node1029(costs[i][0], costs[i][1]));
                        totalCost += costs[i][0];
                    }
                }
            }
        }

        return totalCost;
    }
    static class ComparatorFor2 implements Comparator<Node1029> {
        public int compare(Node1029 a, Node1029 b) {
            return Integer.compare(a.cB, b.cB);
        }
    }
    static class Node1029 {
        private int cA;
        private int cB;
        public Node1029(int c1, int c2) {
            this.cA = c1;
            this.cB = c2;
        }
    }


    // [H] 72       Edit Distance
    public int minDistance(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] dp = new int[M + 1][N + 1];

        for (int c = 1; c < N + 1; c++) dp[0][c] = c;
        for (int r = 1; r < M + 1; r++) dp[r][0] = r;

        for (int i = 1; i < M + 1; i++) {
            char cQ = word1.charAt(i - 1);
            for (int j = 1; j < N + 1; j++) {
                char cK = word2.charAt(j - 1);

                if (cQ == cK) dp[i][j] = dp[i - 1][j - 1];
                else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

//        for (int[] d : dp) {
//            for (int i : d) System.out.print(i + " ");
//            System.out.println();
//        }

        return dp[M][N];
    }


    // [M] 74       Sort Colors
    public void sortColors(int[] nums) {
        quickSort74(nums, 0, nums.length - 1);
    }
    private void quickSort74(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition74(a, lo, hi);
        exch74(a, lo, j);
        quickSort74(a, lo, j - 1);
        quickSort74(a, j + 1, hi);
    }
    private void exch74(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition74(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] < a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch74(a, i, j);
        }
        return j;
    }


    // [M] 161      One Edit Distance
    public boolean isOneEditDistance(String s, String t) {
        if (s.length() == 0 && t.length() == 0) return false;
        else if (s.length() == 0 && t.length() == 1) return true;
        else if (s.length() == 1 && t.length() == 0) return true;
        else if (s.length() == 1 && t.length() == 1) return s.charAt(0) != t.charAt(0);

        int M = s.length(), N = t.length();
        int[][] dp = new int[M + 1][N + 1];
        for (int c = 1; c < N + 1; c++) dp[0][c] = c;
        for (int r = 1; r < M + 1; r++) dp[r][0] = r;

        for (int i = 1; i < M + 1; i++) {
            char cQ = s.charAt(i - 1);
            for (int j = 1; j < N + 1; j++) {
                char cK = t.charAt(j - 1);
                if (cK == cQ) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        }
        return dp[M][N] == 1;
    }



    // Hacker rank
    public static int minimizeCost(List<Integer> numPeople, List<Integer> x, List<Integer> y) {
        int xMax = 0, yMax = 0, xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE;
        int[] xArr = new int[x.size()];
        int[] yArr = new int[y.size()];
        int[] pArr = new int[numPeople.size()];
        int minCost = Integer.MAX_VALUE;

        int idx = 0;
        for (int i : x) {
            xArr[idx++] = i;
            if (i > xMax) xMax = i;
            if (i < xMin) xMin = i;
        }
        idx = 0;
        for (int i : y) {
            yArr[idx++] = i;
            if (i > yMax) yMax = i;
            if (i < yMin) yMin = i;
        }
        idx = 0;
        for (int i : numPeople) pArr[idx++] = i;


        for (int r = xMin; r <= xMax; r++) {
            for (int c = yMin; c <= yMax; c++) {

                int subCost = 0;
                for (int i = 0; i < pArr.length; i++) {
                    subCost += pArr[i] * (Math.abs(r - xArr[i]) + Math.abs(c - yArr[i]));
                }

                if (subCost < minCost) minCost = subCost;

            }
        }

        return minCost;
    }


    public static void main(String[] args) {
        Leetcode0925 lc = new Leetcode0925();
        String q = "plasma";
        String k = "altruism";
        System.out.println(lc.minDistance(q, k));
    }

}
