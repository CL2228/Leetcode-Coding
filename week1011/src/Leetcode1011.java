public class Leetcode1011 {



    // [H] 174      Dungeon Game
    public int calculateMinimumHP(int[][] dungeon) {
        /**
         * this solution uses dynamic programming
         *
         * the dp[i][j] represents the minimum HP needed to reach the end from this tile
         *
         * for each tile, it can choose to go rightward or downward,
         * choose the optimal case based on the HP need on the right tile or the bottom tile
         */
        int M = dungeon.length, N = dungeon[0].length;
        int[] dp = new int[N];
        if (dungeon[M - 1][N - 1] > 0) dp[N - 1] = 1;       // initialization of the corner
        else dp[N - 1] = -dungeon[M - 1][N - 1] + 1;

        // initialization of the dp array (the bottom border)
        for (int c = N - 2; c >= 0; c--) {
            int tmp = dp[c + 1] - dungeon[M - 1][c];
            if (tmp <= 0) tmp = 1;
            dp[c] = tmp;
        }


        // dy computing
        for (int r = M - 2; r >= 0; r--) {
            int[] tmpDP = new int[N];
            for (int c = N - 1; c >= 0; c--) {
                if (c == N - 1) {                       // if it is at the right border, can only move downward
                    int tmp = dp[c] - dungeon[r][c];
                    if (tmp <= 0) tmp = 1;
                    tmpDP[c] = tmp;
                }
                else {                                  // else, choose the better one
                    int tmp = Math.min(tmpDP[c + 1], dp[c]) - dungeon[r][c];
                    if (tmp <= 0) tmp = 1;
                    tmpDP[c] = tmp;
                }
            }
            dp = tmpDP;                                 // update the dp array
        }
//        for (int[] row : dp) {
//            for (int ele : row) System.out.print(ele + " ");
//            System.out.println();
//        }
        return dp[0];
    }


    // [H] 72       Edit Distance
    public int minDistance(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] dp = new int[M + 1][N + 1];

        for (int r = 0; r < M + 1; r++) dp[r][0] = r;
        for (int c = 0; c < N + 1; c++) dp[0][c] = c;

        for (int r = 1; r < M + 1; r++) {
            char sourceChar = word1.charAt(r - 1);
            for (int c = 1; c < N + 1; c++) {
                char destChar = word2.charAt(c - 1);
                if (sourceChar == destChar) dp[r][c] = dp[r - 1][c - 1];
                else dp[r][c] = Math.min(dp[r - 1][c], Math.min(dp[r][c - 1], dp[r - 1][c - 1])) + 1;
            }
        }
        return dp[M][N];
    }


    // [M] 50       Pow(x, n)
    public double myPow(double x, int n) {
        return Math.pow(x, (double)n);
    }


    public static void main(String[] args) {
        Leetcode1011 lc = new Leetcode1011();
        int[][] a = {{-2,-3,3},{-5,-10,1},{10,30,-5}};
        System.out.println(lc.calculateMinimumHP(a));
    }
}
