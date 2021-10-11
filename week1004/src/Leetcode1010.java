import java.util.*;

public class Leetcode1010 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // [M] 863      All Nodes Distance K in Binary Tree
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        /**
         * this solution transfers the tree to the un-directed graph,
         * if the target is not in the graph, return an empty list
         *
         * else run BFS at the graph, to calculate the distance.
         */
        Set<Integer>[] graph = new Set[501];            // Use sets for the graph
        for (int i = 0; i < graph.length; i++) graph[i] = new HashSet<>();
        List<Integer> res = new LinkedList<>();


        dfs863(root, graph);                            // uses dfs to construct the graph
        if (graph[target.val].isEmpty()) return res;    // if the target node doesn't exist, return

        int dist = 0;                                   // uses bfs to get the target ndoes
        boolean[] visited = new boolean[501];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(target.val);
        visited[target.val] = true;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                int curr = queue.poll();
                if (dist == k) res.add(curr);
                for (int nextKey : graph[curr]) {
                    if (!visited[nextKey]) {
                        visited[nextKey] = true;
                        queue.offer(nextKey);
                    }
                }

            }
            if (dist == k) return res;
            dist++;
        }

        return res;
    }
    private void dfs863(TreeNode curr, Set<Integer>[] graph) {
        if (curr.left == null && curr.right == null) return;
        if (curr.left != null) {
            graph[curr.val].add(curr.left.val);
            graph[curr.left.val].add(curr.val);
            dfs863(curr.left, graph);
        }
        if (curr.right != null) {
            graph[curr.val].add(curr.right.val);
            graph[curr.right.val].add(curr.val);
            dfs863(curr.right, graph);
        }
    }


    // [M] 1305     All Elements in Two Binary Search Trees
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> res = new LinkedList<>();

        Deque<Integer> tree1 = new LinkedList<>();
        Deque<Integer> tree2 = new LinkedList<>();
        inorderTraversal(root1, tree1);
        inorderTraversal(root2, tree2);

        while(!tree1.isEmpty() || !tree2.isEmpty()) {
            if (tree1.isEmpty()) res.add(tree2.pollFirst());
            else if (tree2.isEmpty()) res.add(tree1.pollFirst());
            else {
                if (tree1.peekFirst() <= tree2.peekFirst()) res.add(tree1.pollFirst());
                else res.add(tree2.pollFirst());
            }
        }
        return res;
    }
    private void inorderTraversal(TreeNode curr, Deque<Integer> list) {
        if (curr == null) return;
        inorderTraversal(curr.left, list);
        list.offerLast(curr.val);
        inorderTraversal(curr.right, list);
    }


    // [M] 1010     Pairs of Songs With Total Durations Divisible by 60
    public int numPairsDivisibleBy60(int[] time) {
        int[] freq = new int[60];
        for (int t : time) freq[t % 60]++;

//        for (int i = 0; i < 60; i++) {
//            if (freq[i] != 0) {
//                System.out.println(i + " " + freq[i]);
//            }
//        }

        int sum = 0;
        for (int i = 1; i < 60; i++) {
            if (i == 30) continue;
            sum += freq[i] * freq[60 - i];
        }
        sum += freq[0] * (freq[0] - 1);
        sum += freq[30] * (freq[30] - 1);

        return sum / 2;
    }
    public int numPairsDivisibleBy601(int[] time) {
        /**
         * this solution uses a hash map to keep the record of occurred times
         *
         * after the map is constructed, loop through each key to see
         * whether there is a target that adds up the current key to be divisible by 60
         */
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();        // construct a map
        for (int t : time) {
            maxLen = Math.max(maxLen, t);
            if (!map.containsKey(t)) map.put(t, 1);
            else map.replace(t, map.get(t) + 1);
        }

        int sum = 0;                                        // the result sum

        for (int key : map.keySet()) {                      // each key in the map
            int currCnt = map.get(key);                     // the frequency of the current key
            int base = key / 60 + 1;                        // the base of the next divisible sum
            int target = base * 60 - key;
            while (target <= maxLen) {                      // the target cannot exceed the maximum value
                if (map.containsKey(target)) {              // if this target exist
                    if (target == key) sum += currCnt * (currCnt - 1);  // if the target is the same as the current
                    else sum += currCnt * map.get(target);              // otherwise, add it up
                }
                target += 60;                               // update the target
            }
        }
        return sum / 2;                                     // noted that we calculate twice for every pair, divide it by 2
    }


    // [H] 460      LFU Cache
    static class LFUCache {
        // this solution uses an index-Min-PQ
        private int capacity;
        private IndexMinPQ pq;
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.pq = new IndexMinPQ(capacity);
        }

        public int get(int key) {
            if (capacity == 0) return -1;
            else return this.pq.getValue(key);
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            this.pq.put(key, value);
        }

        private class Node460 {
            private int key;
            private int value;
            private int timeStamp;
            private int frequency;
            public Node460(int key, int value, int timeStamp) {
                this.key = key;
                this.value = value;
                this.timeStamp = timeStamp;
                this.frequency = 1;
            }
        }
        private class IndexMinPQ {
            private int currT;                      // the time stamp
            private Map<Integer, Integer> indexMap; // the map to store the indices
            private int N;                          // the current number of items in the PQ
            private Node460[] data;                 // PQ's data
            private int capacity;

            public IndexMinPQ(int capacity) {           // construct a Index-MinPQ
                this.capacity = capacity;
                this.data = new Node460[capacity + 1];
                indexMap = new HashMap<>();
                this.N = 0;                             // the current number and time stamp
                this.currT = 0;
            }

            public int getValue(int key) {          // get value method will affect the time stamp
                if (indexMap.containsKey(key)) {
                    int idx = indexMap.get(key);    // this item's index in the PQ
                    int value = data[idx].value;    // the value need to be returned
                    data[idx].frequency++;          // the frequency of this node increases
                    data[idx].timeStamp = currT++;  // the time stamp updates
                    sink(idx);                      // sink this node, as it may now bigger than the son
                    return value;                   // return value
                }
                else return -1;
            }

            public void put(int key, int value) {   // put a value in the PQ
                if (indexMap.containsKey(key)) {    // this PQ has this key, update it
                    int idx = indexMap.get(key);    // get the index of this node
                    data[idx].value = value;        // update its value
                    data[idx].frequency++;          // update its frequency
                    data[idx].timeStamp = currT++;  // update its time stamp
                    sink(idx);                      // sink if possible
                }
                else {
                    if (N < capacity)               // can directly insert to the PQ
                        add(key, value);
                    else {                          // reached the capacity, delete the minimum and insert the new one
                        delMin();
                        add(key, value);
                    }
                }
            }

            private int delMin() {              // delete the minimum element, delete an element wont affect the time stamp
                Node460 target = data[1];       // the element needed to delete
                exch(1, N--);                // exchange with the last item, noted that the index is also changed
                indexMap.remove(target.key);    // remove the index from the map
                data[N + 1] = null;             // let the last item to be null
                sink(1);                     // sink the first item
                return target.value;            // return the value, although it might be unnecessary
            }

            private void add(int key, int value) {              // add a new item to the PQ, noted that the new item will swim to the peek
                Node460 curr = new Node460(key, value, currT);  // create a new node
                data[++N] = curr;                               // put it at the end
                indexMap.put(key, N);                           // put the index to the map
                swim(N);                                        // swim this item to the peek
                currT++;                                        // update the time stamp
            }

            // helper function for minPQ
            private void swim(int k) {
                while (k / 2 >= 1 && greater(k / 2, k)) {
                    exch(k/2, k);
                    k = k / 2;
                }
            }

            private void sink(int k) {
                while (2*k <= N) {
                    int j = 2 * k;
                    if (j < N && greater(j, j + 1)) j++;
                    if (greater(k, j)) {
                        exch(k, j);
                        k = j;
                    }
                    else break;
                }
            }

            private void exch(int i, int j) {
                // first, exchange the index in the map
                int keyI = data[i].key, keyJ = data[j].key;
                int swapIdx = indexMap.get(keyI);
                indexMap.put(keyI, indexMap.get(keyJ));
                indexMap.put(keyJ, swapIdx);

                // second, exchange the position
                Node460 swap = data[i];
                data[i] = data[j];
                data[j] = swap;
            }

            private boolean greater(int i, int j) {
                if (data[i].frequency > data[j].frequency) return true;
                else if (data[i].frequency < data[j].frequency) return false;
                else {
                    if (data[i].timeStamp > data[j].timeStamp) return true;
                    else return false;
                }
            }

            public void visualization() {
                System.out.println("--------------------------------");
                System.out.println(indexMap);
                for (int i = 1; i <= N; i++) {
                    System.out.println("key:" + data[i].key + " value:" + data[i].value +
                            " timestamp:" + data[i].timeStamp + "  freq:" + data[i].frequency);
                }
                System.out.println("--------------------------------");
            }

        }
    }


    // [M] 318      Maximum Product of Word Lengths
    public int maxProduct(String[] words) {
        int maxPro = 0;
        quickSort318(words, 0, words.length - 1);
//        for (String str : words) System.out.print(str.length() + "  ");
//        System.out.println();

        int[] binaryRepre = new int[words.length];
        for (int i = 0; i < words.length; i++) binaryRepre[i] = getBinaryPre(words[i]);

        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].length() * words[i].length() <= maxPro) break;
            for (int j = i + 1; j < words.length; j++) {
                if ((binaryRepre[i] & binaryRepre[j]) == 0)
                    maxPro = Math.max(maxPro, words[i].length() * words[j].length());
            }
        }
        return maxPro;
    }
    private int getBinaryPre(String word) {
        int[] freq = new int[26];
        for (int i = 0; i < word.length(); i++) {
            char curr = word.charAt(i);
            if (freq[curr - 'a'] == 0) freq[curr - 'a'] = 1;
        }
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) count += (int)(Math.pow(2, i));
        }
        return count;
    }
    private void quickSort318(String[] words, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition318(words, lo, hi);
        exch318(words, lo, j);
        quickSort318(words, lo, j - 1);
        quickSort318(words, j + 1, hi);
    }
    private void exch318(String[] a, int i, int j) {
        String swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition318(String[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].length() >= a[lo].length())
                if (i >= hi) break;
            while (a[--j].length() <= a[lo].length())
                if (j <= lo) break;
            if (i >= j) break;
            exch318(a, i, j);
        }
        return j;
    }


    // [H] 174      Dungeon Game
    public int calculateMinimumHP(int[][] dungeon) {
        int M = dungeon.length, N = dungeon[0].length;
        List<Integer>[][] dp = new List[M][N];
        dp[0][0] = new ArrayList<>(Arrays.asList(dungeon[0][0], dungeon[0][0], dungeon[0][0], dungeon[0][0]));
        for (int c = 1; c < N; c++) {
            int currHP = dp[0][c - 1].get(0) + dungeon[0][c];
            int maxHPNeed = Math.min(currHP, dp[0][c - 1].get(1));
            dp[0][c] = new ArrayList<>(Arrays.asList(currHP, maxHPNeed, currHP, maxHPNeed));
        }
        for (int r = 1; r < M; r++) {
            int currHP = dp[r - 1][0].get(0) + dungeon[r][0];
            int maxHPNeed = Math.min(currHP, dp[r - 1][0].get(1));
            dp[r][0] = new ArrayList<>(Arrays.asList(currHP, maxHPNeed, currHP, maxHPNeed));
        }

        for (int r = 1; r < M; r++) {
            for (int c = 1; c < N; c++) {
                dp[r][c] = getDPList(dp, r, c, dungeon);
            }
        }





        for (List<Integer>[] row : dp) {
            for (List<Integer> ele : row) System.out.print(ele + " ");
            System.out.println();
        }



        int res = Math.max(dp[M - 1][N - 1].get(1), dp[M - 1][N - 1].get(3));
        if (res > 0) return 1;
        return - res + 1;
    }
    private List<Integer> getDPList(List<Integer>[][] dp, int r, int c, int[][] dungeon) {

        // [MPCurrHP, MPHPNeed]
        int profitUp = dp[r - 1][c].get(0) + dp[r - 1][c].get(1);
        int profitLeft = dp[r][c - 1].get(0) + dp[r][c - 1].get(1);
        int MPCurrHP, MPHPNeed;
        if (profitUp > profitLeft) {
            MPCurrHP = dp[r - 1][c].get(0) + dungeon[r][c];
            MPHPNeed = Math.min(dp[r - 1][c].get(1), MPCurrHP);
        }
        else if (profitUp < profitLeft) {
            MPCurrHP = dp[r][c - 1].get(0) + dungeon[r][c];
            MPHPNeed = Math.min(dp[r][c - 1].get(1), MPCurrHP);
        }
        else {
            if (dp[r - 1][c].get(1) >= dp[r][c - 1].get(1)) {
                MPCurrHP = dp[r - 1][c].get(0) + dungeon[r][c];
                MPHPNeed = Math.min(dp[r - 1][c].get(1), MPCurrHP);
            }
            else {
                MPCurrHP = dp[r][c - 1].get(0) + dungeon[r][c];
                MPHPNeed = Math.min(dp[r][c - 1].get(1), MPCurrHP);
            }
        }

        // [LCCurrHP, LCHPNeed]
        int LCCurrHP, LCHPNeed;
        if (dp[r - 1][c].get(3) > dp[r][c - 1].get(3)) {
            LCCurrHP = dp[r - 1][c].get(2) + dungeon[r][c];
            LCHPNeed = Math.min(dp[r - 1][c].get(3), LCCurrHP);
        }
        else if (dp[r - 1][c].get(3) < dp[r][c - 1].get(3)) {
            LCCurrHP = dp[r][c - 1].get(2) + dungeon[r][c];
            LCHPNeed = Math.min(dp[r][c - 1].get(3), LCCurrHP);
        }
        else {
            if (dp[r - 1][c].get(2) >= dp[r][c - 1].get(2)) {
                LCCurrHP = dp[r - 1][c].get(2) + dungeon[r][c];
                LCHPNeed = Math.min(dp[r - 1][c].get(3), LCCurrHP);
            }
            else {
                LCCurrHP = dp[r][c - 1].get(2) + dungeon[r][c];
                LCHPNeed = Math.min(dp[r][c - 1].get(3), LCCurrHP);
            }
        }

        return new ArrayList<>(Arrays.asList(MPCurrHP, MPHPNeed, LCCurrHP, LCHPNeed));
    }

    public static void main(String[] args) {
        Leetcode1010 lc = new Leetcode1010();
        int[][] a = {{1,-3,3},{0, -2, 0},{-3, -3, -3}};
        int[][] a1 = {{0}};
        System.out.println(lc.calculateMinimumHP(a));



    }
}
