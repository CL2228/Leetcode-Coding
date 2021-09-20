import java.util.Deque;
import java.util.LinkedList;

public class Leetcode0917 {

    // [M] 1049     Last Stone Weight II
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        int averageMass = 0;
        for (int s : stones) sum += s;
//        System.out.println(sum);
        averageMass = sum / 2;
        int[] dp = new int[averageMass + 1];

        for (int j = 0; j < averageMass + 1; j++) if (j >= stones[0]) dp[j] = stones[0];
//        for (int d : dp) System.out.print(d + " ");
//        System.out.println();

        for (int i = 1; i < stones.length; i++) {
            for (int w = averageMass; w >= 0; w--) {
                if (w >= stones[i]) dp[w] = Math.max(dp[w], dp[w - stones[i]] + stones[i]);
            }
//            for (int d : dp) System.out.print(d + " ");
//            System.out.println();
        }
        return sum - 2 * dp[averageMass];
    }


    // [M] 494      Target Sum
    public int findTargetSumWays(int[] nums, int target) {
        if (nums.length == 1 && Math.abs(nums[0]) == Math.abs(target)) return 1;
        // S - M = target
        // S + M = sum, which is the sum of nums
        // S - (sum - S) = target
        // so S = (sum + target) / 2
        // so when sum + target is not dividable by 2, there is no answer
        int sum = 0;
        for (int i : nums) sum += i;
        if ((sum + target) % 2 == 1) return 0;

        int S = (sum + target) / 2;
        if (S < 0) S = -S;
        int[] dp = new int[S + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = S; j >= nums[i]; j--) dp[j] = dp[j] + dp[j - nums[i]];
        }
        return dp[S];
    }


    // [H] 239      Sliding Window Maximum
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < k - 1; i++) push(deque, nums[i]);
        for (int i = k - 1; i < nums.length; i++) {
            if (i > k - 1) pop(deque, nums[i - k]);
            push(deque, nums[i]);
            res[i - k + 1] = deque.peekFirst();
        }
        for (int i : res) System.out.print(i + " ");
        return res;
    }
    private void push(Deque<Integer> deque, int val) {
        while (!deque.isEmpty() && deque.peekLast() < val) deque.removeLast();
        deque.offerLast(val);
    }
    private void pop(Deque<Integer> deque, int val) {
        if (!deque.isEmpty() && deque.peekFirst() == val) deque.pollFirst();
    }


    // [M] 474      Ones and Zeroes
    public int findMaxForm(String[] strs, int m, int n) {
        int N = Math.max(m, n);
        int[][] matrixZero = new int[strs.length][N + 1];
        int[][] matrixOne = new int[strs.length][N + 1];

        for (int j = 0; j < N + 1; j++) {
            int[] counter = count(strs[0]);
            if (j < m + 1) {
                if (j >= counter[0]) matrixZero[0][j] = 1;
            }
            else if (j >= m + 1) matrixZero[0][j] = matrixZero[0][j - 1];

            if (j < n + 1) {
                if (j >= counter[1]) matrixOne[0][j] = 1;
            }
            else if (j >= n +  1) matrixOne[0][j] = matrixZero[0][j - 1];
        }

        for (int i = 1; i < strs.length; i++) {
            int[] counter = count(strs[i]);
            for (int j = 0; j < N + 1; j++) {
                if (j >= m + 1) matrixZero[i][j] = matrixZero[i][j - 1];
                else if (j < m + 1) {
                    if (j >= counter[0]) matrixZero[i][j] = Math.max(matrixZero[i - 1][j], matrixZero[i - 1][j - counter[0]] + 1);
                    else matrixZero[i][j] = matrixZero[i - 1][j];
                }

                if (j >= n + 1) matrixOne[i][j] = matrixOne[i][j - 1];
                else if (j < n + 1) {
                    if (j >= counter[1]) matrixOne[i][j] = Math.max(matrixOne[i - 1][j], matrixOne[i - 1][j - counter[1]] + 1);
                    else matrixOne[i][j] = matrixOne[i - 1][j];
                }

                matrixOne[i][j] = matrixZero[i][j] = Math.min(matrixZero[i][j], matrixOne[i][j]);
            }
        }

        for (int[] c : matrixZero) {
            for (int i : c) System.out.print(i + " ");
            System.out.println();
        }
        System.out.println();
        for (int[] c : matrixOne) {
            for (int i : c) System.out.print(i + " ");
            System.out.println();
        }

        return Math.min(matrixZero[strs.length - 1][m], matrixOne[strs.length - 1][n]);
    }
    private int[] count(String str) {
        int[] counter = new int[2];
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') counter[0]++;
            else counter[1]++;
        }
        return counter;
    }


    public static void main(String[] args) {
        Leetcode0917 lc = new Leetcode0917();
        String[] a = {"10","0001","111001","1","0"};
        System.out.println(lc.findMaxForm(a, 5, 3));
    }
}
