import java.util.*;

public class Leetcode0922 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [M] 11       Container With Most Water
    public int maxArea(int[] height) {
        int max = 0;
        int stIdx = 0, edIdx = height.length - 1;

        while (stIdx < edIdx) {
            int tmpArea = Math.min(height[stIdx], height[edIdx]) * (edIdx - stIdx);
            if (tmpArea > max) max = tmpArea;
            if (height[stIdx] <= height[edIdx]) stIdx++;
            else edIdx--;
        }
        return max;
    }


    // [M] 309      Best Time to Buy and Sell Stock with Cooldown
    public int maxProfit(int[] prices) {
        // [1] at every ith day, we have three states:
        // B, buy/bought on/before this day
        // S. the accumulated profit of 0~i-1 day (do not sell on ith day)
        // C. the accumulated profit that sell on this day, then cool

        // B. b[i] = MAX{b[i - 1], s[i - 1] - p[i - 1]} , dont count cool because we cannot buy after selling
        // S. s[i] = MAX{s[i - 1], c[i - 1]}
        // C. c[i] = b[i-1] + p[i]      (must sell on this day)

        if (prices.length == 1) return 0;
        int b = -prices[0], s = 0, c = 0;

        for (int i = 1; i < prices.length; i++) {
            int tmpB = Math.max(b, s - prices[i]);
            int tmpS = Math.max(s, c);
            int tmpC = b + prices[i];
            b = tmpB;
            s = tmpS;
            c = tmpC;
        }

        return Math.max(s, c);
    }


    // [M] 714      Best Time to Buy and Sell Stock with Transaction Fee
    public int maxProfit(int[] prices, int fee) {
        int b = -prices[0], s = 0;

        for (int i = 1; i < prices.length; i++) {
            int tmpB = Math.max(b, s - prices[i]);
            int tmpS = Math.max(b + prices[i] - fee, s);
            b = tmpB;
            s = tmpS;
        }

        return s;
    }
    public int maxProfit1(int[] prices, int fee) {
        if (prices.length == 1) return 0;
        int profit = 0;
        int minP = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minP) minP = prices[i];
            if (prices[i] > minP + fee) {
                profit += prices[i] - minP- fee;
                minP = prices[i] - fee;
            }
        }
        return profit;
    }


    // [M] 79       Word Search
    public boolean exist(char[][] board, String word) {
        int M = board.length;
        int N = board[0].length;
        boolean[][] visited = new boolean[M][N];
        Set<Integer>[] sets = new Set[M * N];

        char head = word.charAt(0);
        List<int[]> indices = new LinkedList<>();
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == head) indices.add(new int[]{r, c});
            }
        }
        if (indices.size() == 0) return false;
        else {
            for (int[] index : indices)
                if (backtracking(visited, board, word, 0, index[0], index[1], sets)) return true;
        }
        return false;
    }
    private boolean backtracking(boolean[][] visited, char[][] board, String word, int idx, int r, int c, Set<Integer>[] sets) {
        if (idx == word.length() - 1) return true;

        int key = r * board[0].length + c;
        if (sets[key] == null) sets[key] = new HashSet<>(idx);
        else {
            Set<Integer> set = sets[key];
            if (set.contains(idx)) return false;
            else set.add(idx);
        }
        visited[r][c] = true;
        char nextC = word.charAt(idx + 1);
        List<int[]> nextIdx = getNext(visited, board, nextC, r, c);
        if (nextIdx.size() > 0) {
            for (int[] indice : nextIdx)
                if (backtracking(visited, board, word, idx + 1, indice[0], indice[1], sets)) return true;
        }
        visited[r][c] = false;
        return false;
    }
    private List<int[]> getNext(boolean[][] visited, char[][] board, char target, int r, int c) {
        List<int[]> indices = new LinkedList<>();
        int M = board.length;
        int N = board[0].length;
        if (r > 0 && !visited[r - 1][c] && board[r - 1][c] == target) indices.add(new int[]{r - 1, c});
        if (r < M - 1 && !visited[r + 1][c] && board[r + 1][c] == target) indices.add(new int[]{r + 1, c});
        if (c > 0 && !visited[r][c - 1] && board[r][c - 1] == target) indices.add(new int[]{r, c - 1});
        if (c < N - 1 && !visited[r][c + 1] && board[r][c + 1] == target) indices.add(new int[]{r, c + 1});
        return indices;
    }


    // [H] 212      Word Search II
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new LinkedList<>();

        List<int[]>[] indices = new List[26];
        int M = board.length, N = board[0].length;
        for (int i = 0; i < 26; i++) indices[i] = new LinkedList<>();

        for (int r = 0; r < M; r++)
            for (int c = 0; c < N; c++) indices[board[r][c] - 'a'].add(new int[]{r, c});

        for (String word : words) {
            Set<Integer>[] sets = new Set[M * N];
            boolean[][] visited = new boolean[M][N];
            char head = word.charAt(0);
            List<int[]> idxs = indices[head - 'a'];
            for (int[] coordinate : idxs) {
                if (backtracking212(visited, board, word, 0, coordinate[0], coordinate[1], sets)) {
                    res.add(word);
                    break;
                }
            }
        }
        return res;
    }
    private boolean backtracking212(boolean[][] visited, char[][] board, String word, int idx, int r, int c, Set<Integer>[] sets) {
        if (idx == word.length() - 1) return true;

        int key = r * board[0].length + c;
        if (sets[key] == null) sets[key] = new HashSet<>(idx);
        else {
            Set<Integer> set = sets[key];
            if (set.contains(idx)) return false;
            else set.add(idx);
        }
        visited[r][c] = true;
        char nextC = word.charAt(idx + 1);
        List<int[]> nextIdx = getNext212(visited, board, nextC, r, c);
        if (nextIdx.size() > 0) {
            for (int[] indice : nextIdx)
                if (backtracking212(visited, board, word, idx + 1, indice[0], indice[1], sets)) return true;
        }
        visited[r][c] = false;
        return false;
    }
    private List<int[]> getNext212(boolean[][] visited, char[][] board, char target, int r, int c) {
        List<int[]> indices = new LinkedList<>();
        int M = board.length;
        int N = board[0].length;
        if (r > 0 && !visited[r - 1][c] && board[r - 1][c] == target) indices.add(new int[]{r - 1, c});
        if (r < M - 1 && !visited[r + 1][c] && board[r + 1][c] == target) indices.add(new int[]{r + 1, c});
        if (c > 0 && !visited[r][c - 1] && board[r][c - 1] == target) indices.add(new int[]{r, c - 1});
        if (c < N - 1 && !visited[r][c + 1] && board[r][c + 1] == target) indices.add(new int[]{r, c + 1});
        return indices;
    }


    // [H] 23       Merge k Sorted Lists
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        ListNode virtualHead = new ListNode(-1000);
        ListNode curr = virtualHead;
        MinPQ23 pq = new MinPQ23(lists.length);

        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) pq.insert(lists[i]);
        }

        while (!pq.isEmpty()) {
            ListNode smallest = pq.delMin();
            if (smallest.next != null) pq.insert(smallest.next);

            curr.next = smallest;
            smallest.next = null;
            curr = smallest;
        }
        return virtualHead.next;
    }
    static class MinPQ23 {
        private ListNode[] data;
        private int N;
        public MinPQ23(int size) {
            this.data = new ListNode[size + 1];
            this.N = 0;
        }

        public boolean isEmpty() { return N == 0; }
        public ListNode min() { return data[1]; }

        public ListNode delMin() {
            ListNode res = data[1];
            exch(1, N--);
            sink(1);
            data[N + 1] = null;
            return res;
        }

        public void insert(ListNode x) {
            data[++N] = x;
            swim(N);
        }

        private boolean greater(int i, int j) {
            return data[i].val > data[j].val;
        }

        private void exch(int i, int j) {
            ListNode swap = data[i];
            data[i] = data[j];
            data[j] = swap;
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && greater(j, j + 1)) j++;
                if (!greater(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }
    }


    // [E] 1365     How Many Numbers Are Smaller Than the Current Number
    public int[] smallerNumbersThanCurrent(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) res[i] = map.get(nums[i]);
            else {
                int n = 0;
                for (int num : nums) if (num < nums[i]) n++;
                res[i] = n;
                map.put(nums[i], n);
            }
        }
        return res;
    }


    // [M] 300      Longest Increasing Subsequence
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int max = 1;
        Arrays.fill(dp, 1);

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    // [E] 674      Longest Continuous Increasing Subsequence
    public int findLengthOfLCIS(int[] nums) {
        int max = 1;
        int curr = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                curr++;
                max = Math.max(max, curr);
            }
            else curr = 1;
        }
        return max;
    }


    // [M] 673      Number of Longest Increasing Subsequence
    public int findNumberOfLIS(int[] nums) {
        int maxLen = 1;
        int cnt = 1;
        int[][] dp = new int [2][nums.length];
        for (int[] sub : dp) Arrays.fill(sub, 1);
        for (int i = 1; i < nums.length; i++) {
            int subMax = 1;
            int subCnt = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[0][j] + 1 > subMax) {
                        dp[0][i] = subMax = dp[0][j] + 1;
                        subCnt = dp[1][j];
                    }
                    else if (dp[0][j] + 1 == subMax) subCnt += dp[1][j];
                }
            }
            dp[1][i] = subCnt;
            if (subMax > maxLen) {
                maxLen = subMax;
                cnt = subCnt;
            }
            else if (subMax == maxLen) cnt += subCnt;
        }
        return cnt;
    }


    public static void main(String[] args) {
        Leetcode0922 lc = new Leetcode0922();
        int[] a = {1,1,1,2,2,2,3,3,3};
        int[] a1 = {1,2};
        System.out.println(lc.findNumberOfLIS(a));
    }
}
