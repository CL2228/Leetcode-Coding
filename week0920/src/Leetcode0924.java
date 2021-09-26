import java.util.Arrays;

public class Leetcode0924 {


    // [M] 1143     Longest Common Subsequence
    public int longestCommonSubsequence(String text1, String text2) {
        // this is the version using two dimension dp array
//        int M = text1.length();
//        int N = text2.length();
//        int[][] dp = new int[M + 1][N + 1];
//
//        for (int i = 1; i < M + 1; i++) {
//            for (int j = 1; j < N + 1; j++) {
//                if (text1.charAt(i - 1) == text2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
//                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
//            }
//        }
//
//        return dp[M][N];


        // we can use only two one-dimension array
        int M = text1.length(), N = text2.length();

        int[] dp = new int[N + 1];
        for (int i = 0; i < text1.length(); i++) {
            int[] tmpDP = new int[N + 1];
            for (int j = 0; j < text2.length(); j++) {
                if (text1.charAt(i) == text2.charAt(j)) tmpDP[j + 1] = dp[j] + 1;
                else tmpDP[j + 1] = Math.max(tmpDP[j], dp[j + 1]);
            }
            dp = tmpDP;
        }
        return dp[N];
    }


    // [M] 300      Longest Increasing Subsequence
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        dp[0] = 1;
        int maxLen = 1;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }


    // [M] 334      Increasing Triplet Subsequence
    public boolean increasingTriplet(int[] nums) {

        int oneMin = nums[0];
        int secondMin = Integer.MAX_VALUE;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > secondMin) return true;
            else if (nums[i] < oneMin) oneMin = nums[i];
            else if (nums[i] > oneMin && nums[i] < secondMin) secondMin = nums[i];
        }
        return false;
    }


    // [M] 416      Partition Equal Subset Sum
    public boolean canPartition(int[] nums) {
        int ave = 0;
        for (int n : nums) ave += n;
        if (ave % 2 != 0) return false;
        ave /= 2;

        int[] dp = new int[ave + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = ave; j > 0; j--){
                if (j >= nums[i]) dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[ave] == ave;
    }




    public static void main(String[] args) {
    }
}
