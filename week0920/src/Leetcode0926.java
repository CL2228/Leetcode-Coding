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


    // [M] 261      Graph Valid Tree
    public boolean validTree(int n, int[][] edges) {
        // this one uses union find (quick union, log time to union!)
        // for a graph which is also a tree, the number of edges must be V - 1
        if (edges.length != n - 1) return false;

        // if the above condition satisfied, then if all the nodes are connected, it will be a tree
        // using union find
        int[] uf = new int[n];
        for (int i = 0; i < n; i++) uf[i] = i;
        for (int[] e : edges) union261(uf, e[0], e[1]);
        for (int i = 1; i < n; i++)
            if (getRoot261(uf, i) != getRoot261(uf,i - 1)) return false;
        return true;
    }
    private int getRoot261(int[] uf, int i) {
        while (i != uf[i]) i = uf[i];
        return i;
    }
    private void union261(int[] uf, int p, int q) {
        int i = getRoot261(uf, p);
        int j = getRoot261(uf, q);
        uf[i] = j;
    }
    public boolean validTree1(int n, int[][] edges) {
        // this one uses DFS with hash sets
        Set<Integer> visited = new HashSet<>();
        Set<Integer>[] data = new Set[n];

        for (int i = 0; i < n; i++) data[i] = new HashSet<>();
        for (int[] e : edges) {
            data[e[0]].add(e[1]);
            data[e[1]].add(e[0]);
        }

        if (!dfs261(data, -100, 0, visited)) return false;
        if (visited.size() != n) return false;
        return true;
    }
    private boolean dfs261(Set<Integer>[] data, int lastIdx, int idx, Set<Integer> visited) {
        if (visited.contains(idx)) return false;
        visited.add(idx);
        Set<Integer> nextSteps = data[idx];
        for (int step : nextSteps)
            if (step != lastIdx && !dfs261(data, idx, step, visited)) return false;
        return true;
    }


    // [M] 323      Number of Connected Components in an Undirected Graph
    public int countComponents(int n, int[][] edges) {
        // using DFS for every root entry

        Set<Integer>[] graph = new Set[n];
        for (int i = 0; i < n; i++) graph[i] = new HashSet<>();
        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        boolean[] visited = new boolean[n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs323(graph, i, visited);
                cnt++;
            }
        }
        return cnt;
    }
    private void dfs323(Set<Integer>[] graph, int idx, boolean[] visited) {
//        System.out.println(idx);
        visited[idx] = true;
        Set<Integer> nextNodes = graph[idx];
        for (int n : nextNodes)
            if (!visited[n]) dfs323(graph, n, visited);
    }
    public int countComponents1(int n, int[][] edges) {
        // using union find, with quick union
        int[] uf = new int[n];
        for (int i = 0; i < n; i++) uf[i] = i;
        for (int[] e : edges) union323(uf, e[0], e[1]);

        Set<Integer> cc = new HashSet<>();
        for (int i : uf) cc.add(getRoot323(uf, i));
        return cc.size();
    }
    private int getRoot323(int[] uf, int i){
        while (uf[i] != i) i = uf[i];
        return i;
    }
    private void union323(int[] uf, int p, int q) {
        int i = getRoot323(uf, p);
        int j = getRoot323(uf, q);
        uf[i] = j;
    }


    // [M] 200      Number of Islands
    public int numIslands(char[][] grid) {
        int M = grid.length, N = grid[0].length;
        boolean[][] visited = new boolean[M][N];
        int cnt = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    cnt++;
                    bfs200(grid, visited, i, j);
                }
            }
        }
        return cnt;
    }
    private void bfs200(char[][] grid, boolean[][] visited, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});
        visited[r][c] = true;
        while (!queue.isEmpty()) {
            int[] coordinate = queue.poll();
            int tmpR = coordinate[0], tmpC = coordinate[1];
            if (tmpR < grid.length - 1 && grid[tmpR + 1][tmpC] == '1' && !visited[tmpR+1][tmpC]) {
                queue.offer(new int[]{tmpR + 1, tmpC});
                visited[tmpR + 1][tmpC] = true;
            }
            if (tmpC < grid[0].length - 1 && grid[tmpR][tmpC + 1] == '1' && !visited[tmpR][tmpC + 1]) {
                queue.offer(new int[]{tmpR, tmpC + 1});
                visited[tmpR][tmpC + 1] = true;
            }
            if (tmpR > 0 && grid[tmpR - 1][tmpC] == '1' && !visited[tmpR - 1][tmpC]) {
                queue.offer(new int[]{tmpR - 1, tmpC});
                visited[tmpR - 1][tmpC] = true;
            }
            if (tmpC > 0 && grid[tmpR][tmpC - 1] == '1' && !visited[tmpR][tmpC - 1]) {
                queue.offer(new int[]{tmpR, tmpC - 1});
                visited[tmpR][tmpC - 1] = true;
            }
        }
    }


    // [M] 130      Surrounded Regions
    public void solve(char[][] board) {
        int M = board.length, N = board[0].length;
        boolean[][] visited = new boolean[M][N];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++)
                if (!visited[r][c] && board[r][c] == 'O') bfs130(board, visited, r, c);
        }
    }
    private void bfs130(char[][] board, boolean[][] visited, int r, int c) {
        int M = board.length, N = board[0].length;
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> list = new LinkedList<>();
        int[] tmpArr = new int[]{r, c};
        queue.add(tmpArr);
        list.add(tmpArr);
        visited[r][c] = true;
        boolean toTurn = true;

        while (!queue.isEmpty()) {
            int[] headArr = queue.poll();
            int tmpR = headArr[0], tmpC = headArr[1];

            if (tmpR == 0 || tmpR == M - 1 || tmpC == 0 || tmpC == N - 1) toTurn = false;

            if (tmpR > 0 && !visited[tmpR - 1][tmpC] && board[tmpR - 1][tmpC] == 'O') {
                int[] tmpCo = new int[]{tmpR - 1, tmpC};
                visited[tmpR - 1][tmpC] = true;
                queue.offer(tmpCo);
                list.add(tmpCo);
            }

            if (tmpR < M - 1 && !visited[tmpR + 1][tmpC] && board[tmpR + 1][tmpC] == 'O') {
                int[] tmpCo = new int[]{tmpR + 1, tmpC};
                visited[tmpR + 1][tmpC] = true;

                queue.offer(tmpCo);
                list.add(tmpCo);
            }

            if (tmpC > 0 && !visited[tmpR][tmpC - 1] && board[tmpR][tmpC - 1] == 'O') {
                int[] tmpCo = new int[]{tmpR, tmpC - 1};
                visited[tmpR][tmpC + 1] = true;
                queue.offer(tmpCo);
                list.add(tmpCo);
            }

            if (tmpC < N - 1 && !visited[tmpR][tmpC + 1] && board[tmpR][tmpC + 1] == 'O') {
                int[] tmpCo = new int[]{tmpR, tmpC + 1};
                visited[tmpR][tmpC + 1] = true;
                queue.offer(tmpCo);
                list.add(tmpCo);
            }
        }
        if (toTurn)
            for (int[] coor : list) board[coor[0]][coor[1]] = 'X';
    }


    // [M] 286      Wall and Gates
    public void wallsAndGates(int[][] rooms) {
        int M = rooms.length, N = rooms[0].length;
        Queue<int[]> coordinates = new LinkedList<>();
        boolean[][] visited = new boolean[M][N];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++)
                if (rooms[r][c] == 0) coordinates.add(new int[]{r, c});
        }
        int distance = 0;
        while (!coordinates.isEmpty()) {
            int levelSize = coordinates.size();
            for (int i = 0; i < levelSize; i++) {
                int[] tmpCoor = coordinates.poll();
                int tmpR = tmpCoor[0], tmpC = tmpCoor[1];
                if (distance < rooms[tmpR][tmpC]) rooms[tmpR][tmpC] = distance;

                if (tmpR > 0 && !visited[tmpR - 1][tmpC] && rooms[tmpR - 1][tmpC] > 0) {
                    visited[tmpR - 1][tmpC] = true;
                    coordinates.offer(new int[]{tmpR - 1, tmpC});
                }
                if (tmpR < M - 1 && !visited[tmpR + 1][tmpC] && rooms[tmpR + 1][tmpC] > 0) {
                    visited[tmpR + 1][tmpC] = true;
                    coordinates.offer(new int[]{tmpR + 1, tmpC});
                }
                if (tmpC > 0 && !visited[tmpR][tmpC - 1] && rooms[tmpR][tmpC - 1] > 0) {
                    visited[tmpR][tmpC - 1] = true;
                    coordinates.offer(new int[]{tmpR, tmpC - 1});
                }
                if (tmpC < N - 1 && !visited[tmpR][tmpC + 1] && rooms[tmpR][tmpC + 1] > 0) {
                    visited[tmpR][tmpC + 1] = true;
                    coordinates.offer(new int[]{tmpR, tmpC + 1});
                }
            }
            distance++;
        }
    }
    private void bfs286(int[][] rooms, int r, int c) {
        // this version uses single source BFS, so it sucks

        int M = rooms.length, N = rooms[0].length;
        boolean[][] visited = new boolean[M][N];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        visited[r][c] = true;
        int dist = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int[] tmpCoor = queue.poll();
                int tmpR = tmpCoor[0], tmpC = tmpCoor[1];
                if (rooms[tmpR][tmpC] > dist) rooms[tmpR][tmpC] = dist;

                if (tmpR > 0 && !visited[tmpR - 1][tmpC] && rooms[tmpR - 1][tmpC] > 0) {
                    visited[tmpR - 1][tmpC] = true;
                    queue.offer(new int[]{tmpR - 1, tmpC});
                }
                if (tmpR < M - 1 && !visited[tmpR + 1][tmpC] && rooms[tmpR + 1][tmpC] > 0) {
                    visited[tmpR + 1][tmpC] = true;
                    queue.offer(new int[]{tmpR + 1, tmpC});
                }
                if (tmpC > 0 && !visited[tmpR][tmpC - 1] && rooms[tmpR][tmpC - 1] > 0) {
                    visited[tmpR][tmpC - 1] = true;
                    queue.offer(new int[]{tmpR, tmpC - 1});
                }
                if (tmpC < N - 1 && !visited[tmpR][tmpC + 1] && rooms[tmpR][tmpC + 1] > 0) {
                    visited[tmpR][tmpC + 1] = true;
                    queue.offer(new int[]{tmpR, tmpC + 1});
                }
            }
            dist++;
        }
    }


    public static void main(String[] args) {
        Leetcode0926 lc = new Leetcode0926();
        String a = "bbbab";
        System.out.println(lc.longestPalindromeSubseq(a));
    }
}
