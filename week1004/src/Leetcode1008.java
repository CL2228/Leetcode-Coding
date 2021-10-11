import java.util.PriorityQueue;

public class Leetcode1008 {


    // [M] 64       Minimum Path Sum
    public int minPathSum(int[][] grid) {
        // this solution uses dynamic programming,
        // for each position, there are at most two ways to reach it, the left tile of the top tile
        // the minimum distance from the source to this tile is the minimum distance between the left and top tile plus the value of this one
        int M = grid.length, N = grid[0].length;
        int[] dp = new int[N];
        dp[0] = grid[0][0];
        for (int i = 1; i < N; i++)             // initialize the first row of dp array
            dp[i] = dp[i -1] + grid[0][i];

        for (int i = 1; i < M; i++) {
            int[] tmpDP = new int[N];
            for (int j = 0; j < N; j++) {
                if (j == 0) tmpDP[j] = grid[i][j] + dp[j];                      // the first column situation
                else tmpDP[j] = Math.min(tmpDP[j - 1], dp[j]) + grid[i][j];     // normal situation
            }
            dp = tmpDP;                                                         // update the dp array
        }
        return dp[N - 1];
    }


    // [M] 1937     Maximum Number of Points with Cost
    public long maxPoints(int[][] points) {
        int M = points.length, N = points[0].length;
        long[] dp = new long[N];


        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) dp[j] += points[i][j];
            relaxation(dp);
        }
        long maxPoints = 0;
        for (long ele : dp) maxPoints = Math.max(maxPoints, ele);
        return maxPoints;
    }
    private void relaxation(long[] dp) {
        for (int i = 1; i < dp.length; i++) dp[i] = Math.max(dp[i], dp[i - 1] - 1);
        for (int i = dp.length - 2; i >= 0; i--) dp[i] = Math.max(dp[i], dp[i + 1] - 1);
    }



    // [H] 295
    class MedianFinder {
        private PriorityQueue<Integer> maxPQ;       // for the first half of the data
        private PriorityQueue<Integer> minPQ;       // for the second half of the data
        public MedianFinder() {
            minPQ = new PriorityQueue<>();
            maxPQ = new PriorityQueue<>((a,b) -> -Integer.compare(a,b));
        }

        public void addNum(int num) {
            if (maxPQ.isEmpty() && minPQ.isEmpty()) maxPQ.add(num);
            else if (minPQ.isEmpty()) {             // the initialization cases
                if (num < maxPQ.peek()) {
                    minPQ.add(maxPQ.remove());
                    maxPQ.add(num);
                }
                else minPQ.add(num);
            }
            else {                  // normal cases
                if (num < minPQ.peek()) {               // this new number should be added to the left half
                    if (maxPQ.size() > minPQ.size())    // the maximum element of the left half should be popped to the right half
                        minPQ.add(maxPQ.remove());
                    maxPQ.add(num);
                }
                else {                                  // this new number should be added to the right half
                    if (minPQ.size() > maxPQ.size())    // the minimum element of the right half should be popped to the left half
                        maxPQ.add(minPQ.remove());
                    minPQ.add(num);
                }
            }
        }

        public double findMedian() {
            if (maxPQ.size() > minPQ.size()) return (double)maxPQ.peek();
            else if (maxPQ.size() < minPQ.size()) return (double)minPQ.peek();
            else return ((double)maxPQ.peek() + (double)minPQ.peek() ) / 2;
        }
    }


    public static void main(String[] args) {

    }
}
