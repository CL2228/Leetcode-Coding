import java.util.*;

public class Leetcode0926 {

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

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


    // [M] 207      Course Schedule
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // use backtracking and dfs with recording.
        boolean[] clear = new boolean[numCourses];
        boolean[] visited = new boolean[numCourses];

        Set<Integer>[] graph = new Set[numCourses];
        for (int i = 0; i < numCourses; i++) graph[i] = new HashSet<>();
        for (int[] preq : prerequisites) graph[preq[0]].add(preq[1]);

        for (int i = 0; i < numCourses; i++)
            if (!dfs207(graph, i, clear, visited)) return false;
        return true;
    }
    private boolean dfs207(Set<Integer>[] graph, int idx, boolean[] clear, boolean[] visited) {
        if (visited[idx]) return false;
        if (clear[idx]) return true;

        visited[idx] = true;
        Set<Integer> nextCourse = graph[idx];

        for (int next : nextCourse) {
            if (!dfs207(graph, next, clear, visited)) return false;
        }
        visited[idx] = false;
        clear[idx] = true;
        return true;
    }


    // [M] 210      Courser Schedule II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];

        Set<Integer>[] graph = new Set[numCourses];
        boolean[] clear = new boolean[numCourses];
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) graph[i] = new HashSet<>();
        for (int[] preq : prerequisites) graph[preq[0]].add(preq[1]);

        Queue<Integer> postOrder = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!dfs210(graph, i, clear, visited, postOrder)) return new int[0];
        }
        int idx = 0;
        while (!postOrder.isEmpty()) res[idx++] = postOrder.poll();

        return res;
    }
    private boolean dfs210(Set<Integer>[] graph, int idx, boolean[] clear, boolean[] visited, Queue<Integer> stack) {
        if (visited[idx]) return false;
        if (clear[idx]) return true;

        visited[idx] = true;
        Set<Integer> nextSteps = graph[idx];

        for (int step : nextSteps) {
            if (!dfs210(graph, step, clear, visited, stack)) return false;
        }
        visited[idx] = false;
        clear[idx] = true;
        stack.add(idx);
        return true;
    }


    // [M] 133      Clone Graph
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        boolean[] clear = new boolean[101];
        Node[] data = new Node[101];

        Deque<Node> reference = new LinkedList<>();
        reference.add(node);
        clear[node.val] = true;

        while (!reference.isEmpty()) {
            Node currRef = reference.pollFirst();

            Node curr;
            if (data[currRef.val] == null) data[currRef.val] = new Node(currRef.val);
            curr = data[currRef.val];

            for (Node nei : currRef.neighbors) {
                Node curNei;
                if (data[nei.val] == null) data[nei.val] = new Node(nei.val);
                curNei = data[nei.val];

                curr.neighbors.add(curNei);
                if (!clear[nei.val]) {
                    reference.offerLast(nei);
                    clear[nei.val] = true;
                }
            }
        }
        return data[1];
    }



    public static void main(String[] args) {
        Leetcode0926 lc = new Leetcode0926();
        String a = "bbbab";
        System.out.println(lc.longestPalindromeSubseq(a));
    }
}
