import java.util.*;

public class Leetcode1007 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // [M] 300      Longest Increasing Subsequence
    public int lengthOfLIS(int[] nums) {
        // this approach takes N square time complexity
        // uses dynamic programming
        // for each i, let j [0, i - 1], if nums[i] > nums[j], a longer subsequence might exist
        int maxLen = 1;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }


    // [M] 673      Number of Longest Increasing Subsequence
    public int findNumberOfLIS(int[] nums) {
        // this program uses dynamic programming
        // 1. It uses a two-dimensional dp array, dp[0][i] is the maximum of i,
        //      dp[1][i] is the frequency of that sub maximum length.
        // 2. For every i, it visits j belongs to [0, i - 1],
        //   IF nums[i] is larger than nums[j], compare the current maximum length and the maximum length if j + 1
        //      a. if the length related to j is equal to the current record, the dp[1][i] += dp[1][j], as the frequency number increases
        //      b. If the length related to j is larger than the current record, replace the current record to the longer length, as well as the frequency
        //      c. If the length is shorter, ignore it.

        int maxLen = 1;
        int maxTime = 1;
        int[][] dp = new int[2][nums.length];
        Arrays.fill(dp[0], 1);          // initialize the dp array to ALL 1s
        Arrays.fill(dp[1], 1);

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {          // only response when the num[i] is greater than num[j]
                    int tmpLen = dp[0][j] + 1;      // get the longest length associated to j
                    if (tmpLen > dp[0][i]) {        // if this length is greater
                        dp[0][i] = tmpLen;          // update the dp array
                        dp[1][i] = dp[1][j];
                    }
                    else if (tmpLen == dp[0][i])    // if the length equals to the record
                        dp[1][i] += dp[1][j];       // only increase the frequency
                }
            }
            if (dp[0][i] > maxLen) {                // if the longest length if i is greater than the maxLen, update it
                maxLen = dp[0][i];
                maxTime = dp[1][i];
            }                                       // if the longest length equals to the maxLen, update the frequency
            else if (dp[0][i] == maxLen) maxTime += dp[1][i];
        }
        return maxTime;
    }


    // [E] 463      Island Perimeter
    public int islandPerimeter(int[][] grid) {
        // this key of this problem is that given an island tile,
        // the number of borders it contributes to the whole island = 4 - the number of neighbour-island-tiles
        int perimeter = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) perimeter += calBorder(grid, r, c);
        }
        return perimeter;
    }
    private int calBorder(int[][] data, int r, int c) {
        if (data[r][c] == 0) return 0;
        int neighbour = 0;
        if (r > 0 && data[r - 1][c] == 1) neighbour++;
        if (r < data.length - 1 && data[r + 1][c] == 1) neighbour++;
        if (c > 0 && data[r][c - 1] == 1) neighbour++;
        if (c < data[0].length - 1 && data[r][c + 1] == 1) neighbour++;
        return 4 - neighbour;
    }


    // [M] 695      Max Area of Island
    public int maxAreaOfIsland(int[][] grid) {
        // this approach uses breadth-first search.
        // first, create a visited array to keep track of which index has been visited.
        int M = grid.length, N = grid[0].length;
        boolean[][] visited = new boolean[M][N];
        int maxArea = 0;
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 1 && !visited[r][c])      // if this is an island tile and it has not been visited, bfs it
                    maxArea = Math.max(maxArea, bfs695(grid, visited, r, c));
            }
        }
        return maxArea;
    }
    private int bfs695(int[][] grid, boolean[][] visited, int r, int c) {
        // traditional BFS, using a queue.
        int area = 0, M = grid.length, N = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            area++;
            int currR = curr[0], currC = curr[1];
            if (currR > 0  && grid[currR - 1][currC] == 1 && !visited[currR - 1][currC]) {
                visited[currR - 1][currC] = true;
                queue.offer(new int[]{currR - 1, currC});
            }
            if (currR < M - 1 && grid[currR + 1][currC] == 1 && !visited[currR + 1][currC]) {
                visited[currR + 1][currC] = true;
                queue.offer(new int[]{currR + 1, currC});
            }
            if (currC > 0 && grid[currR][currC - 1] == 1 && !visited[currR][currC - 1]) {
                visited[currR][currC - 1] = true;
                queue.offer(new int[]{currR, currC - 1});
            }
            if (currC < N - 1 && grid[currR][currC + 1] == 1 && !visited[currR][currC + 1]) {
                visited[currR][currC + 1] = true;
                queue.offer(new int[]{currR, currC + 1});
            }
        }
        return area;
    }


    // [M] 841      Keys and Rooms
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // this approach uses Breadth-first search
        // a traditional BFS using a queue, nothing complicate
        int N = rooms.size();
        int remaining = N - 1;
        boolean[] visited = new boolean[N];
        visited[0] = true;
        Queue<Integer> queue = new LinkedList<>();
        for (int key : rooms.get(0)) {
            if (!visited[key]) {
                remaining--;
                visited[key] = true;
                queue.add(key);
            }
        }

        while (!queue.isEmpty()) {
            int currIdx = queue.poll();
            for (int key : rooms.get(currIdx)) {
                if (!visited[key]) {
                    remaining--;
                    visited[key] = true;
                    queue.add(key);
                }
            }
        }

        return remaining == 0;
//        for (int i = 0; i < N; i++) if (!visited[i]) return false;
//        return true;
    }


    // [M] 1020     Number of Enclaves
    public int numEnclaves(int[][] grid) {
        int area = 0;
        int M = grid.length, N = grid[0].length;
        boolean[][] visited = new boolean[M][N];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 1 && !visited[r][c])
                    area += bfs1020(grid, visited, r, c);
            }
        }
        return area;
    }
    private int bfs1020(int[][] grid, boolean[][] visited, int r, int c) {
        int M = grid.length, N = grid[0].length;
        int area = 0;
        boolean valid = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currR = curr[0], currC = curr[1];
            if (currR == 0 || currC == 0 || currR == M - 1 || currC == N - 1) valid = false;
            area++;


            if (currR > 0 && grid[currR - 1][currC] == 1 && !visited[currR - 1][currC]) {
                visited[currR - 1][currC] = true;
                queue.add(new int[]{currR - 1, currC});
            }
            if (currR < M - 1 && grid[currR + 1][currC] == 1 && !visited[currR + 1][currC]) {
                visited[currR + 1][currC] = true;
                queue.add(new int[]{currR + 1, currC});
            }
            if (currC > 0 && grid[currR][currC - 1] == 1 && !visited[currR][currC - 1]) {
                visited[currR][currC - 1] = true;
                queue.add(new int[]{currR, currC - 1});
            }
            if (currC < N - 1 && grid[currR][currC + 1] == 1 && !visited[currR][currC + 1]) {
                visited[currR][currC + 1] = true;
                queue.add(new int[]{currR, currC + 1});
            }
        }
        if (valid) return area;
        else return 0;
    }


    // [M] 398      Random Pick Index
    class Solution1 {
        // this solution uses a hash map, whose keys are the value of each element,
        // and whose values are lists contain the indices of that element
        private Map<Integer, List<Integer>> data;
        private Random rand;
        public Solution1(int[] nums) {
            this.data = new HashMap<>();
            this.rand = new Random();
            for (int i = 0; i < nums.length; i++) {         // construct the map
                if (!data.containsKey(nums[i]))
                    data.put(nums[i], new ArrayList<>(Arrays.asList(i)));
                else data.get(nums[i]).add(i);
            }
        }
        public int pick(int target) {
            List<Integer> list = data.get(target);
            if (list.size() == 1) return list.get(0);
            else return list.get(rand.nextInt(list.size()));
        }
    }


    // [M] 382      Linked List Random Node
    class Solution {
        List<Integer> data;
        Random rand;
        int N;
        public Solution(ListNode head) {
            this.data = new ArrayList<>();
            this.rand = new Random();
            ListNode curr = head;
            while (curr != null) {
                data.add(curr.val);
                curr = curr.next;
            }
            this.N = data.size();
        }

        public int getRandom() {
            return data.get(rand.nextInt(N));
        }
    }


    // [H] 295      Find Median from Data Stream
    static class MedianFinder {
        // 1. this solution uses two priority queues,
        //      a MaxPQ for the smaller half of the data, a MinPQ for the larger half of the data
        // 2. When inquired the median, return the peek of the longer PQ, if two PQ have the same size, return the average
        // 3. When inserting,
        //      a. If one PQ is empty, compare the peek and insert to the right one
        //      b. If the number is less than the peek of the MaxPQ, this number must be added to the MaxPQ,
        //          if the size of MaxPQ is bigger than the MinPQ, pop the largest item to the MinPQ from MaxPQ
        //      c. If the number is greater than the peek of the MinPQ, this number must be added to the MinPQ, same as b
        //      d. if this number is between the peeks of MaxPQ and MinPQ, just add to the PQ with a smaller size.

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
                if (num < maxPQ.peek()) {               // this new number should be added to the left half
                    if (maxPQ.size() > minPQ.size())    // the maximum element of the left half should be popped to the right half
                        minPQ.add(maxPQ.remove());
                    maxPQ.add(num);
                }
                else if (num > minPQ.peek()){                                  // this new number should be added to the right half
                    if (minPQ.size() > maxPQ.size())    // the minimum element of the right half should be popped to the left half
                        maxPQ.add(minPQ.remove());
                    minPQ.add(num);
                }
                else {                                  // between the two peeks, just put in the PQ with less items
                    if (maxPQ.size() <= minPQ.size()) maxPQ.add(num);
                    else minPQ.add(num);
                }
            }
        }
        public double findMedian() {                    // return the median based on the size of two PQs
            if (maxPQ.size() > minPQ.size()) return (double)maxPQ.peek();
            else if (maxPQ.size() < minPQ.size()) return (double)minPQ.peek();
            else return ((double)maxPQ.peek() + (double)minPQ.peek() ) / 2;
        }
    }


    // [H] 127      Word Ladder
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        /**
         * this solution uses breadth first search to calculate the shortest path
         * 1. construct a graph with
         */
        int N = wordList.size();
        Set<Integer>[] graph = new Set[N];
        int targetIdx = -1;
        int beginIdx = -1;
        for (int i = 0; i < N; i++) {
            graph[i] = new HashSet<>();
            String wordI = wordList.get(i);
            if (beginWord.equals(wordI)) beginIdx = i;
            if (endWord.equals(wordI)) targetIdx = i;
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                String wordJ = wordList.get(j);
                if (oneDistReach(wordI, wordJ)) graph[i].add(j);
            }
        }
        if (targetIdx < 0) return 0;

        int dist = 1;
        boolean[] visited = new boolean[N];
        if (beginIdx > 0) visited[beginIdx] = true;

            Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            if (oneDistReach(beginWord, wordList.get(i))) {
                visited[i] = true;
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                int curr = queue.poll();
                if (curr == targetIdx) return dist + 1;
                for (int nextIdx : graph[curr]) {
                    if (!visited[nextIdx]) {
                        visited[nextIdx] = true;
                        queue.add(nextIdx);
                    }
                }
            }
            dist++;
        }
        return 0;
    }
    private boolean oneDistReach(String a, String b) {
        if (a.length() != b.length()) return false;
        int difference = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) difference++;
            if (difference > 1) return false;
        }
        return true;
    }









    public static void main(String[] args) {
        Leetcode1007 lc = new Leetcode1007();
        MedianFinder mf = new MedianFinder();
        List<String> list = new ArrayList<>();
        list.add("hot");
        list.add("dot");
        list.add("dog");
        list.add("lot");
        list.add("log");
        list.add("cog");
        System.out.println(lc.ladderLength("hit", "cog", list));
    }
}
