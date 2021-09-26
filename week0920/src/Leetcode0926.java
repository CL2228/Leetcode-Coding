public class Leetcode0926 {



    // [M] 518      Coin Change 2
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int c : coins) {
            for (int j = 1; j < amount + 1; j++) {
                if (j >= c) dp[j] += dp[j - c];
            }
        }
        return dp[amount];
    }


    // [M] 647      Palindromic Substrings
    public int countSubstrings(String s) {
//        int num = 0;
//        boolean[][] dp = new boolean[s.length()][s.length()];
//        for (int j = 0; j < s.length(); j++) {
//            char cJ = s.charAt(j);
//            for (int i = 0; i <= j; i++) {
//                char cI = s.charAt(i);
//                if (cI == cJ) {
//                    if (i >= j - 1) {
//                        dp[i][j] = true;
//                        num++;
//                    }
//                    else {
//                        if (dp[i + 1][j - 1]) {
//                            dp[i][j] = true;
//                            num++;
//                        }
//                    }
//                }
//            }
//        }
//        return num;

        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            cnt += computePalin647(i, i, s.length(), s);
            cnt += computePalin647(i, i + 1, s.length(), s);
        }
        return cnt;
    }
    private int computePalin647(int i, int j, int n, String s) {
        int cnt = 0;
        while (i >=0 && j < n && s.charAt(i) ==s.charAt(j)) {
            i--;
            j++;
            cnt++;
        }
        return cnt;
    }


    // [M] 516      Longest Palindromic Subsequence
    public int longestPalindromeSubseq(String s) {
        char[] data = s.toCharArray();
        int N = s.length();
        int[] dp = new int[N + 1];

        for (int i = 1; i < N + 1; i++) {
            char cI = data[i - 1];

            int[] tmpDP = new int[N + 1];
            for (int j = 1; j < N + 1; j++) {
                char cJ = data[N - j];
                if (cI == cJ) tmpDP[j] = dp[j - 1] + 1;
                else
                    tmpDP[j] = Math.max(dp[j], tmpDP[j - 1]);
            }
            dp = tmpDP;
        }

        return dp[N];
    }









    public static void main(String[] args) {
        Leetcode0926 lc = new Leetcode0926();
        String a = "bbbab";
        System.out.println(lc.longestPalindromeSubseq(a));
    }
}
