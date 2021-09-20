public class Leetcode0915 {

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


    // [E] 746      Min Cost Climbing Stairs
    public int minCostClimbingStairs(int[] cost) {
        int[] minCost = new int[cost.length];
        minCost[0] = 0;
        minCost[1] = 0;

        for (int i = 2; i < cost.length; i++)
            minCost[i] = Math.min(minCost[i - 2] + cost[i - 2], minCost[i - 1] + cost[i - 1]);
        return Math.min(minCost[cost.length - 2] + cost[cost.length - 2], minCost[cost.length - 1] + cost[cost.length - 1]);
    }


    // [M] 62       Unique Paths
    public int uniquePaths(int m, int n) {
        int[][] count = new int[m][n];
        count[m - 1][n - 1] = 1;
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                count[r][c] = getCount(count, r, c);
            }
        }
        return count[0][0];
    }
    private int getCount(int[][] count, int r, int c) {
        int right = 0;
        int down = 0;
        if (c < count[0].length - 1) right += count[r][c + 1];
        if (r < count.length - 1) down += count[r + 1][c];
        return count[r][c] + right + down;
    }
    private int findPath(int r, int c, int M, int N) {
        if (r > M || c > N) return 0;
        if (r == M && c == N) return 1;
        return findPath(r + 1, c, M, N) + findPath(r, c + 1, M, N);
    }


    // [M] 63       Unique Paths II
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int M = obstacleGrid.length;
        int N = obstacleGrid[0].length;
        if (obstacleGrid[M - 1][N - 1] == 1) return 0;
        int[][] count = new int[M][N];
        count[M - 1][N - 1] = 1;
        for (int r = M - 1; r >= 0; r--) {
            for (int c = N - 1; c >= 0; c--) {
                if (obstacleGrid[r][c] != 1) count[r][c] = getCount63(count, r, c, obstacleGrid);
            }
        }
        return count[0][0];
    }
    private int getCount63(int[][] count, int r, int c, int[][] obstacle) {
        int right = 0;
        int down = 0;
        if (c < count[0].length - 1) right += count[r][c + 1];
        if (r < count.length - 1) down += count[r + 1][c];
        return count[r][c] + down + right;
    }


    // [M] 343      Integer Break
    public int integerBreak(int n) {
        if (n == 2) return 1;
        int[] maxProduct = new int[n + 1];
        maxProduct[1] = 1;
        maxProduct[2] = 2;


        for (int curr = 3; curr < n + 1; curr++) {
            int max = 0;
            for (int j = 1; j < curr; j++) {
                int product = Math.max(j * maxProduct[curr - j], j * (curr - j));
                if (product > max) max = product;
            }
            maxProduct[curr] = max;
        }
        return maxProduct[n];
    }


    // [M] 96       Unique Binary Search Trees
    public int numTrees(int n) {
        if (n == 1) return 1;
        int[] count = new int[n + 1];
        count[1] = 1;
        count[2] = 2;

        for (int curr = 3; curr < n + 1; curr++) {
            int subCount = 0;
            for (int i = 1; i <= curr; i++) {
                if (i == 1) subCount += count[curr - i];
                else if (i == curr) subCount += count[curr - 1];
                else subCount += count[i - 1] * count[curr - i];
            }
            count[curr] = subCount;
        }
        return count[n];
    }



    public static void main(String[] args) {
        Leetcode0915 lc = new Leetcode0915();
        System.out.println(lc.numTrees(4));
    }
}
