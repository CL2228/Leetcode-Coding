import java.awt.*;
import java.util.*;
import java.util.List;

public class Leetcode1015 {


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


    // [M] 994      Rotting Oranges
    public int orangesRotting(int[][] grid) {
        int M = grid.length, N = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int dist = 0;
        int remaining = 0;
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 2) queue.offer(new int[]{r, c});
                if (grid[r][c] == 1) remaining++;
            }
        }

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int[] curr = queue.poll();
                int currR = curr[0], currC = curr[1];

                if (currR > 0 && grid[currR - 1][currC] == 1) {
                    grid[currR - 1][currC] = 2;
                    queue.offer(new int[]{currR - 1, currC});
                    remaining--;
                }
                if (currR < M - 1 && grid[currR + 1][currC] == 1) {
                    grid[currR + 1][currC] = 2;
                    queue.offer(new int[]{currR + 1, currC});
                    remaining--;
                }
                if (currC > 0 && grid[currR][currC - 1] == 1) {
                    grid[currR][currC - 1] = 2;
                    queue.offer(new int[]{currR, currC - 1});
                    remaining--;
                }
                if (currC < N - 1 && grid[currR][currC + 1] == 1) {
                    grid[currR][currC + 1] = 2;
                    queue.offer(new int[]{currR, currC + 1});
                    remaining--;
                }
            }
            if (!queue.isEmpty()) dist++;
        }
        if (remaining > 0) return -1;
        else return dist;
    }


    // [M] 926      Flip String to Monotone Increasing
    public int minFlipsMonoIncr(String s) {
        /**
         * this solution uses dynamic programming,
         * the dp array is two-dimensional, dp[N][2]
         *
         * 1. for each i,
         *      dp[i][0] means the minimum changes needed to make the array corrected and ended with 0
         *      dp[i][1] means the minimum changes needed to make the array corrected and ended with 1
         * 2. for each i,
         *   if s[i] == '0', dp[i][0] = dp[i - 1][0]; dp[i][1] = min{dp[i - 1][0], dp[i - 1][1]} + 1.
         *   if s[i] == '1', dp[i][0] = dp[i - 1][0] + 1;  dp[i][1] = min{dp[i - 1][0], dp[i - 1][1]}.
         *
         * takes O(n) time and constant memory
         */
        int N = s.length();
        int ed0 = 0, ed1 = 0;
        if (s.charAt(0) == '0') ed1 = 1;
        else ed0 = 1;

        for (int i = 1; i < N; i++) {
            int curr0 = 0, curr1 = 0;
            if (s.charAt(i) == '0') {
                curr0 = ed0;
                curr1 = Math.min(ed0, ed1) + 1;
            }
            else {
                curr0 = ed0 + 1;
                curr1 = Math.min(ed0, ed1);
            }
            ed0 = curr0;
            ed1 = curr1;
        }
        return Math.min(ed0, ed1);
    }


    // [M] 935      Knight Dialer
    public long knightDialer(int n) {
        Map<Integer, Long>[] cache = new Map[10];
        for (int i = 0; i < 10; i++) cache[i] = new HashMap<>();

        long res = 0;
        for (int i = 0; i <= 9; i++) {
            long a = backtrack(i, n, cache);
//            System.out.println(a);
            res += a;
//            res += backtrack(i, n, cache);
        }
//        return res;
        return (int)(res % ((long)Math.pow(10, 9) + 7));
    }
    private long backtrack(int i, int remainLevel, Map<Integer, Long>[] cache) {
        if (remainLevel == 1) return 1;
        if (cache[i].containsKey(remainLevel)) return cache[i].get(remainLevel);
        long currN = 0;
        for (int nextIdx : getNextStep(i))
            currN += backtrack(nextIdx, remainLevel - 1, cache);

        long currMode = (long)(currN % ((long)Math.pow(10, 9) + 7));
        cache[i].put(remainLevel, currMode);
        return currMode;

    }
    private List<Integer> getNextStep(int i) {
        switch (i) {
            case 0: return new ArrayList<>(Arrays.asList(4, 6));
            case 1: return new ArrayList<>(Arrays.asList(6, 8));
            case 2: return new ArrayList<>(Arrays.asList(7, 9));
            case 3: return new ArrayList<>(Arrays.asList(4, 8));
            case 4: return new ArrayList<>(Arrays.asList(3, 9, 0));
            case 6: return new ArrayList<>(Arrays.asList(1, 7, 0));
            case 7: return new ArrayList<>(Arrays.asList(2, 6));
            case 8: return new ArrayList<>(Arrays.asList(1, 3));
            case 9: return new ArrayList<>(Arrays.asList(2, 4));
            default: return new ArrayList<>(Arrays.asList());
        }
    }


    // [M] 1120     Maximum Average Subtree
    public double maximumAverageSubtree(TreeNode root) {
        /**
         * this solution uses dynamic programming
         */
        return postOrderTraversal(root)[0];
    }
    private double[] postOrderTraversal(TreeNode curr) {
        /**
         * return [historical maximum average, subtree sum, subtree node number]
         */
        if (curr == null) return new double[]{0, 0, 0};

        double[] leftRes = postOrderTraversal(curr.left);
        double[] rightRes = postOrderTraversal(curr.right);

        double currSum = curr.val + leftRes[1] + rightRes[1];
        double currN = 1 + leftRes[2] + rightRes[2];
        double currAve = currSum / currN;
        double historicalMax = Math.max(currAve, Math.max(leftRes[0], rightRes[0]));

        return new double[]{historicalMax, currSum, currN};
    }


    // [M] 1973     Count Nodes Equal to Sum of Descendants
    int count1973 = 0;
    public int equalToDescendants(TreeNode root) {
        postOrder1973(root);
        return count1973;
    }
    private int postOrder1973(TreeNode curr) {
        if (curr == null) return 0;
        int subtreeSum = postOrder1973(curr.left) + postOrder1973(curr.right);
        if (curr.val == subtreeSum) count1973++;
        return subtreeSum + curr.val;
    }


    // [M] 1135     Connecting Cities With Minimum Cost
    public int minimumCost2(int n, int[][] connections) {
        /**
         * this solution uses Minimum Spanning Tree, Prim's algorithms, lazy implementation,
         * O(ElogE) time
         */
        Set<Edge>[] graph = new Set[n + 1];
        for (int i = 0; i < n + 1; i++) graph[i] = new HashSet<>();

        for (int[] con : connections) {
            Edge e = new Edge(con[0], con[1], con[2]);
            graph[con[0]].add(e);
            graph[con[1]].add(e);
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(connections.length, (a, b) -> Integer.compare(a.weight, b.weight));
        for (Edge e : graph[1]) pq.add(e);
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        int weightSum = 0;
        int remaining = n - 1;
        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            if (visited[curr.from] && visited[curr.to]) continue;

            int nonVisited;                 // determine the unvisited vertex
            if (!visited[curr.from]) nonVisited = curr.from;
            else nonVisited = curr.to;

            remaining--;                    // WL--
            weightSum += curr.weight;       // weight increases
            visited[nonVisited] = true;     // visit this vertex
            for (Edge e : graph[nonVisited]) {      // put the available edges to the PQ
                if (!visited[e.that(nonVisited)]) pq.add(e);
            }
        }
        if (remaining > 0) return -1;
        else return weightSum;
    }
    class Edge {
        private int from;
        private int to;
        private int weight;
        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public int that(int i) {
            if (from == i) return to;
            else return from;
        }
    }


    // [M] 1135     Connecting Cities With Minimum Cost
    public int minimumCost1(int n, int[][] connections) {
        /**
         * DONT implement sorting yourself, it sucks
         *
         * this one uses union find,    E: edge, V: vertex
         * the total runtime complexity:
         *  1. union: [for check connection]2 * E * H (H = the height of UF tree) + [union]2 * E * H
         *  2. check all connected: V * 2 * H
         *  [total runtime]: (4E+2V)*H, H <= logV
         */
        int[] UF = new int[n + 1];
        for (int i = 0 ; i < n + 1; i++) UF[i] = i;

        int weightSum = 0;

        Arrays.sort(connections, (a, b)->Integer.compare(a[2], b[2]));
//        quickSort1135(connections, 0, connections.length - 1);
        for (int[] con : connections) {
            int st = con[0], ed = con[1], weight = con[2];
            if (!connected(UF, st, ed)) {
                union(UF, st, ed);
                weightSum += weight;
            }
        }

        for (int i = 2; i < n + 1; i++)
            if (!connected(UF, i ,i - 1)) return -1;
        return weightSum;
    }
    private int getAncestor(int[] UF, int i) {
        // UF get ancestor, use logN time
        int curr = i;
        while (UF[curr] != curr)
            curr = UF[curr];
        return curr;
    }
    private boolean connected(int[] UF, int i, int j) {     // check if connected, 2logN time
        return getAncestor(UF, i) == getAncestor(UF, j);
    }
    private void union(int[] UF, int i, int j) {            // union, 2logN time
        int ancestorI = getAncestor(UF, i);
        int ancestorJ = getAncestor(UF, j);
        UF[ancestorJ] = ancestorI;
    }
    private void quickSort1135(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition1135(a, lo, hi);
        exch1135(a, lo, j);
        quickSort1135(a, lo, j - 1);
        quickSort1135(a, j + 1, hi);
    }
    private void exch1135(int[][] a, int i, int j) {
        int[] swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition1135(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][2] <= a[lo][2])
                if (i >= hi) break;
            while (a[--j][2] >= a[lo][2])
                if (j <= lo) break;
            if (i >= j) break;
            exch1135(a, i, j);
        }
        return j;
    }


    // [M] 1135     Connecting Cities With Minimum Cost
    public int minimumCost(int n, int[][] connections) {
        /**
         * this solution uses Union Find with weighted union to see whether it can improve the speed
         *
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>(connections.length, (a, b) -> Integer.compare(a[2], b[2]));
        for (int[] con : connections) pq.add(con);
        UF myUF = new UF(n + 1);
        int weightSum = 0;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            if (myUF.connected(curr[0], curr[1])) continue;
            myUF.union(curr[0], curr[1]);
            weightSum += curr[2];
        }

        for (int i = 2; i <= n; i++)
            if (!myUF.connected(i - 1, i)) return -1;
        return weightSum;
    }
    class UF {
        private int N;
        int[] data;
        int[] weight;
        public UF(int N) {
            this.N = N;
            this.data = new int[N];
            this.weight = new int[N];
            for (int i = 0; i < N; i++) {
                this.data[i] = i;
                this.weight[i] = 1;
            }
        }
        public int getRoot(int i) {
            int curr = i;
            while (data[curr] != curr)
                curr = data[curr];
            return curr;
        }
        public boolean connected(int i, int j) {
            return this.getRoot(i) == this.getRoot(j);
        }
        public void union(int a, int b) {           // weighted union
            int rootA = getRoot(a), rootB = getRoot(b);
            if (weight[rootA] >= weight[rootB]) {
                data[rootB] = rootA;
                weight[rootA] += weight[rootB];
            }
            else {
                data[rootA] = rootB;
                weight[rootB] += weight[rootA];
            }
        }
    }


    // [M] 1167     Minimum Cost to Connect Sticks
    public int connectSticks(int[] sticks) {
        PQ pq = new PQ(sticks.length);
        for (int s : sticks) pq.add(s);
        int sum = 0;

        while (pq.size() >= 2) {
            int a = pq.delMin();
            int b = pq.delMin();
            int subTotal = a + b;
            sum += subTotal;
            pq.add(subTotal);
        }

        return sum;
    }
    static class PQ {
        private int[] data;
        private int N;
        public PQ(int capacity) {
            this.data = new int[capacity + 1];
            this.N = 0;
        }


        public int peek() {
            return data[1];
        }

        public boolean isEmpty() {
            return N == 0;
        }

        public int size() {
            return N;
        }

        public int delMin() {
            int res = data[1];
            exch(1, N--);
            sink(1);
            data[N + 1] = 0;
            return res;
        }

        public void add(int val) {
            data[++N] = val;
            swim(N);
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && greater(j, j + 1)) j++;
                if (greater(k, j)) {
                    exch(k, j);
                    k = j;
                }
                else break;
            }
        }

        private void swim(int k) {
            while (k / 2 >= 1 && greater(k/2, k)) {
                exch(k / 2, k);
                k = k / 2;
            }
        }

        private boolean greater(int i, int j) {
            return data[i] > data[j];
        }

        private void exch(int i, int j) {
            int swap = data[i];
            data[i] = data[j];
            data[j] = swap;
        }

    }



    // [H] 1000     Minimum Cost to Merge Stones
    int sum1000 = 0;
    public int mergeStones(int[] stones, int k) {
        if (stones.length == 1) return 0;
        if (k > stones.length) return -1;
        if ((stones.length - 1) % (k - 1) != 0) return -1;

        while (stones.length >= k) {
            stones = eachStep(stones, k);
//            for (int i : stones) System.out.print(i + " ");
//            System.out.println("\n-------------");
        }
        return sum1000;
    }
    private int[] eachStep(int[] stones, int k) {
        int stIdx = 0, edIdx = k - 1;

        int currSum = 0;
        for (int i = 0; i < k; i++) currSum += stones[i];
        int minSum = currSum;


        for (int i = k; i < stones.length; i++) {
            currSum -= stones[i - k];
            currSum += stones[i];
//            System.out.println("currSum" + currSum + "  minSum:" + minSum);
            if (currSum < minSum) {
                minSum = currSum;
                edIdx = i;
                stIdx = i - k + 1;
            }
        }

        sum1000 += minSum;
        int[] res = new int[stones.length - k + 1];

        int resIdx = 0;
        for (int i = 0; i < stones.length; i++) {
            if (i == stIdx) {
                res[resIdx++] = minSum;
                i += k - 1;
            }
            else res[resIdx++] = stones[i];
        }
        return res;
    }


    // [H] 123      Best Time to Buy and Sell Stock III
    public int maxProfit(int[] prices) {
        /**
         * this solution uses dynamic programing,
         * for each i, it has [stay still, buy1, sell1, buy2, sell2]
         *
         * 1. buy1 computes from the previous buy1, and the current staystill - price[i],
         *      means that if the current price is lower that the previous price,
         *      we should buy this one rather than the previous one
         *    [curr_buy1] = Max{pre_buy1, -price[i]}
         * 2. sell1 computes from the previous sell1, and the previous buy1 + prince[i],
         *      means that if sell the stock at this time for the first transaction can have more profit that sell the stock last time for the first transaction,
         *      we shouldn't sell it at last time stamp, and sell it at the current time stamp
         *    [curr_sell1] = Max{pre_sell1, pre_buy1 + price[i]}
         * 3. buy2 is similar to buy1, but it is computed from previous sell1, as this is the second transaction
         *    [curr_buy1] = Max{pre_buy2, pre_sell1 - price[i]}
         * 4. sell2 is similar to sell1, but it is computed form the previous buy2, as it is the second transaction
         *    [curr_sell2] = Max{pre_sell2, pre_buy2 + price[i]}
         */
        int buy1 = -prices[0], sell1 = 0, buy2 = -prices[0], sell2 = 0;

        for (int i = 1; i < prices.length; i++) {
            int tmpB1 = Math.max(buy1, - prices[i]);
            int tmpS1 = Math.max(sell1, buy1 + prices[i]);
            int tmpB2 = Math.max(buy2, sell1 - prices[i]);
            int tmpS2 = Math.max(sell2, buy2 + prices[i]);
            buy1 = tmpB1;
            sell1 = tmpS1;
            buy2 = tmpB2;
            sell2 = tmpS2;
        }
        return Math.max(sell1, sell2);
    }


    // [H] 41       First Missing Positive
    public int firstMissingPositive(int[] nums) {
        int N = nums.length;
        boolean found1 = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                found1 = true;
                break;
            }
        }
        if (!found1) return 1;
        for (int i = 0; i < N; i++) {
            if (nums[i] <= 0 || nums[i] > N) nums[i] = 1;
        }

        for (int i = 0; i < N; i++) {
            int idx = nums[i] - 1;
            nums[idx] = - Math.abs(nums[idx]);
        }
        int idx = 0;
        while (idx < N) {
            if (nums[idx] > 0) return idx + 1;
            idx++;
        }
        return idx;
    }






    public static void main(String[] args) {
        Leetcode1015 lc = new Leetcode1015();

        int[] a = {3,5,1,2,6};

        System.out.println(lc.mergeStones(a, 3));
    }
}
