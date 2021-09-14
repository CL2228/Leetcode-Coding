import java.util.*;

public class Leetcode0911 {


    // [M] 332      Reconstruct Itinerary
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        boolean[] visited = new boolean[tickets.size()];
        backtracking332(tickets, res, visited, new Node332(-1, "JFK"));
        return res;
    }
    private boolean backtracking332(List<List<String>> tickets, List<String> res, boolean[] visited, Node332 thisNode) {
        String from = thisNode.dest;
        int thisIdx = thisNode.idx;

        if (res.size() >= tickets.size() + 1) return true;
        res.add(from);
        if (thisIdx >= 0) visited[thisIdx] = true;
        if (res.size() == tickets.size() + 1) return true;

        // find the next steps
        int idx = 0;
        PriorityQueue<Node332> PQ = new PriorityQueue<>();
        for (List<String> tick : tickets) {
            if (!visited[idx] && tick.get(0).equals(from)) PQ.offer(new Node332(idx, tick.get(1)));
            idx++;
        }

        if (!PQ.isEmpty()) {
            while (!PQ.isEmpty()) {
                if (backtracking332(tickets, res, visited, PQ.remove())) return true;
            }
        }
        if (thisIdx >= 0) visited[thisIdx] = false;
        res.remove(res.size() - 1);
        return false;
    }
    static class Node332 implements Comparable<Node332>{
        private final String dest;
        private final int idx;
        public Node332(int idx, String dest) {
            this.idx = idx;
            this.dest = dest;
        }
        public int compareTo(Node332 that) {
            if (this.dest.charAt(0) < that.dest.charAt(0)) return -1;
            else if (this.dest.charAt(0) > that.dest.charAt(0)) return 1;
            else {
                if (this.dest.charAt(1) < that.dest.charAt(1)) return -1;
                else if (this.dest.charAt(1) > that.dest.charAt(1)) return 1;
                else {
                    if (this.dest.charAt(2) < that.dest.charAt(2)) return -1;
                    else if (this.dest.charAt(2) > that.dest.charAt(2)) return 1;
                    else return 0;
                }
            }
        }
    }
    private void quickSort322(Node332[] arr, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition322(arr, lo, hi);
        exch322(arr, lo, j);
        quickSort322(arr, lo, j - 1);
        quickSort322(arr, j + 1, hi);
    }
    private void exch322(Node332[] arr, int i, int j) {
        Node332 swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }
    private int partition322(Node332[] arr, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (arr[++i].compareTo(arr[lo]) <= 0)
                if (i >= hi) break;
            while (arr[--j].compareTo(arr[lo]) >= 0)
                if (j <= lo) break;
            if (i >= j) break;
            exch322(arr, i, j);
        }
        return j;
    }


    // [H] 51       N-Queens
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        boolean[][] board = new boolean[n][n];
        boolean[] yVisited = new boolean[n];

        List<List<Integer>> path = new ArrayList<>();

        for (int c = 0; c < n; c++) {
            backtracking51(board, res, path, 0, c, yVisited);
        }
        return res;
    }
    private void backtracking51(boolean[][] board, List<List<String>> res, List<List<Integer>> path,
                                int currR, int currC, boolean[] yVisited) {

        path.add(new ArrayList<>(Arrays.asList(currR, currC)));
        board[currR][currC] = true;
        boolean[] currYVisited = new boolean[yVisited.length];
        System.arraycopy(yVisited, 0, currYVisited, 0, yVisited.length);
        currYVisited[currC] = true;

        if (path.size() == board.length) {
            List<String> subRes = new ArrayList<>();
            boolean shouldAdd = true;

            for (int i = 0; i < board.length; i++) {
                StringBuilder sb = new StringBuilder();
                List<Integer> row = path.get(0);
                for (List<Integer> r : path) {
                    if (r.get(0) == i) {
                        row = r;
                        break;
                    }
                }
                for (int c = 0; c < board.length; c++) {
                    if (c == row.get(1)) sb.append("Q");
                    else sb.append(".");
                }
                subRes.add(sb.toString());
            }

            for (List<String> re : res) {
                boolean subDup = true;
                for (int i = 0; i < re.size(); i++) {
                    subDup = subDup && (re.get(i).equals(subRes.get(i)));
                    if (!subDup) break;
                }
                if (subDup) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) res.add(subRes);
        }
        else {
            List<Integer> nextCandidates = getNextStep(board, currR + 1, currYVisited);
            if (!nextCandidates.isEmpty()) {
                for (int nextC : nextCandidates) {
                    backtracking51(board, res, path, currR + 1, nextC, currYVisited);
                }
            }
        }

        board[currR][currC] = false;
        path.remove(path.size() - 1);
    }
    private List<Integer> getNextStep(boolean[][] board, int currRow, boolean[] yVisited) {
        List<Integer> available = new ArrayList<>();
        int N = board.length;
        for (int c = 0; c < N; c++) {
            if (isAvailable(board, currRow, c, yVisited)) available.add(c);
        }
        return available;
    }
    private boolean isAvailable(boolean[][] board, int x, int y, boolean[] yVisited) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length) return false;
        if (yVisited[y]) return false;
        int x1 = x, y1 = y;
        while (x1 >= 0 && y1 >= 0) {
            if (board[x1--][y1--]) return false;
        }
        x1 = x; y1 = y;
        while (x1 >=0 && y1 < board.length) {
            if (board[x1--][y1++]) return false;
        }
        return true;
    }


    // [E] 455      Assign Cookies
    public int findContentChildren(int[] g, int[] s) {
        int count = 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int gIdx = 0, sIdx = 0;
        while (gIdx < g.length && sIdx < s.length) {
            if (s[sIdx] >= g[gIdx]) {
                gIdx++;
                count++;
            }
            sIdx++;
        }
        return count;
    }


    // [E] 1470     Shuffle the Array
    public int[] shuffle(int[] nums, int n) {
        int[] tmp = new int[n];
        System.arraycopy(nums, n, tmp, 0, n);
        for (int i = n - 1; i > 0; i--) {
            nums[2 * i] = nums[i];
        }
        for (int i = 0; i < n; i++) {
            nums[2 * i + 1] = tmp[i];
        }
        return nums;
    }


    // [M] 376      Wiggle Subsequence
    public int wiggleMaxLength(int[] nums) {
        int count = 1;
        int lastState = -1;     // -1:head;    0:down;  1:up
        int currIdx = 1;

        while (currIdx < nums.length) {
            if (lastState == -1) {
                while (currIdx < nums.length && nums[currIdx] == nums[0]) currIdx++;
                if (currIdx == nums.length) break;
                if (nums[currIdx] > nums[0]) lastState = 1;
                else lastState = 0;
            }
            if (currIdx == nums.length - 1) count++;

            if (lastState == 1) {   // this is up-warding
//                if (currIdx == nums.length - 1) count++;
                if (nums[currIdx] < nums[currIdx - 1]) {
                    count++;
                    lastState = 0;
                }
            }
            else {      // down-warding
//                if (currIdx == nums.length - 1) count++;
                if (nums[currIdx] > nums[currIdx - 1]) {
                    count++;
                    lastState = 1;
                }
            }
            currIdx++;
        }
        return count;
    }











    public static void main(String[] args) {
        Leetcode0911 lc = new Leetcode0911();
        int[] a= {3,3,4,3,3};
        System.out.println(lc.wiggleMaxLength(a));


    }
}
