public class Leetcode0916 {

    // TODO: [M] 95 Unique Binary Search Trees II


    // [M] 416      Partition Equal Subset Sum
    public boolean canPartition(int[] nums) {
        int average = 0;
        for (int i : nums) average += i;
        if (average % 2 > 0) return false;
        average /= 2;
        int M = average + 1;
        int[] dp = new int[M];
        for (int i = 1; i < nums.length; i++) {
            for (int w = M - 1; w >= 0; w--) if (w >= nums[i]) dp[w] = Math.max(dp[w], dp[w - nums[i]] + nums[i]);
        }

        return dp[average] == average;

//        int[][] dp = new int[nums.length][M];
//        for (int c = 0; c < M; c++) if (c >= nums[0]) dp[0][c] = nums[0];
//
//        for (int r = 1; r < N; r++) {
//            for (int c = 1; c < M; c++) {
//                if (nums[r] > c) dp[r][c] = dp[r - 1][c];
//                else dp[r][c] = Math.max(dp[r - 1][c], dp[r - 1][c - nums[r]] + nums[r]);
//            }
//        }
//
//        for (int[] subDp : dp) {
//            for (int i : subDp) System.out.print(i + " ");
//            System.out.println();
//        }
//
//        return dp[nums.length - 1][average] == average;
    }


    // [M] 1049     Last Stone Weight II
    public int lastStoneWeightII(int[] stones) {
        if (stones.length == 1) return stones[0];
        MaxPQ pq = new MaxPQ(stones.length);
        for (int s : stones) pq.insert(s);
        while (pq.size() > 1) {
            int a = pq.delMax();
            int b = pq.delMax();
            if (a - b > 0) pq.insert(a - b);
        }
        if (pq.size() == 0) return 0;
        else return pq.delMax();
    }
    static class MaxPQ {
        private int[] pq;
        private int n;
        public MaxPQ(int capacity) {
            this.pq = new int[capacity + 1];
            n = 0;
        }

        public boolean isEmpty() { return n == 0; }
        public int size() { return n; }
        public void insert(int val) {
            pq[++n] = val;
            swim(n);
        }
        public int delMax() {
            int max = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = 0;
            return max;
        }


        private void swim(int k) {
            while (k > 1 && less(k / 2, k)){
                exch(k, k / 2);
                k = k / 2;
            }
        }
        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && less(j, j + 1)) j ++;
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        private void exch(int i, int j) {
            int swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
        }
        private boolean less(int i, int j) {
            return pq[i] < pq[j];
        }
    }


    // [M] 698      Partition to K Equal Sum Subsets
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int average = 0;
        for (int i : nums) average += i;
        if (average % k != 0) return false;
        average = average / k;

        return false;
    }


    public static void main(String[] args) {
        Leetcode0916 lc = new Leetcode0916();
        int[]a = {1,5,11,5};
        System.out.println(lc.canPartition(a));

    }


}
