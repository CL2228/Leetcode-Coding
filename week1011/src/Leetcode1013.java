import java.util.*;

public class Leetcode1013 {

    public interface NestedInteger {
        // Constructor initializes an empty nested list.
        // Constructor initializes a single integer.

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value);

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni);

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    };


    // [E] 408      Valid Word Abbreviation
    public boolean validWordAbbreviation(String word, String abbr) {
        int wdIdx = 0, abIdx = 0;
        while (wdIdx < word.length() && abIdx < abbr.length()) {
            if (isNum(abbr.charAt(abIdx))) {
                if (abbr.charAt(abIdx) == '0') return false;
                int edIdx = abIdx + 1;
                while (edIdx < abbr.length() && isNum(abbr.charAt(edIdx))) edIdx++;
                int forward = Integer.parseInt(abbr.substring(abIdx, edIdx));
                wdIdx += forward;
                abIdx = edIdx;
            }
            else {
                if (word.charAt(wdIdx) != abbr.charAt(abIdx)) return false;
                wdIdx++;
                abIdx++;
            }
        }
//        System.out.println(wdIdx + "  " + abIdx);
        return abIdx == abbr.length() && wdIdx == word.length();
    }
    private boolean isNum(char c) {
        return (c >= '0' && c <= '9');
    }


    // [M] 339      Nested List Weight Sum
    int sum339 = 0;
    public int depthSum(List<NestedInteger> nestedList) {
        /**
         * recursion and dfs
         */
        for (NestedInteger nl : nestedList) dfs339(nl, 1);
        return sum339;
    }
    private void dfs339(NestedInteger curr, int currLevel) {
        if (curr.isInteger())
            sum339+= currLevel * curr.getInteger();
        else {
            for (NestedInteger next : curr.getList())
                dfs339(next, currLevel + 1);
        }
    }


    // [M] 364      Nested List Weight Sum II
    int sum364 = 0;
    int maxDepth364 = 0;
    public int depthSumInverse(List<NestedInteger> nestedList) {
        /**
         * DFS twice, using 2N time
         */
        for (NestedInteger curr : nestedList) findMaxDFS(curr, 1);
        for (NestedInteger curr : nestedList) calWeightDFS(curr, 1);
        return sum364;
    }
    private void findMaxDFS(NestedInteger curr, int currLevel) {
        if (curr.isInteger()) maxDepth364 = Math.max(maxDepth364, currLevel);
        else {
            for (NestedInteger next : curr.getList())
                findMaxDFS(next, currLevel + 1);
        }
    }
    private void calWeightDFS(NestedInteger curr, int currLevel) {
        if (curr.isInteger())
            sum364 += (maxDepth364 - currLevel + 1) * curr.getInteger();
        else {
            for (NestedInteger next : curr.getList())
                calWeightDFS(next, currLevel + 1);
        }
    }


    // [M] 565      Array Nesting
    public int arrayNesting(int[] nums) {
        /**
         * this solution uses a boolean array to keep track of the visited indices
         * it takes O(N) time since all elements are only visited once
         */
        boolean[] visited = new boolean[nums.length];
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++)
            if (!visited[i])
                maxLen = Math.max(maxLen, calNestingLength(nums, i, visited));
        return maxLen;
    }
    private int calNestingLength(int[] nums, int idx, boolean[] visited) {
        // visit a cycle (the length of the cycle might be 1)
        visited[idx] = true;
        int subLen = 1;
        int nextIdx = nums[idx];
        while (!visited[nextIdx]) {
            visited[nextIdx] = true;
            nextIdx = nums[nextIdx];
            subLen++;
        }
        return subLen;
    }


    // [M] 341      Flatten Nested List Iterator
    public class NestedIterator implements Iterator<Integer> {

        Deque<Integer> deque = new LinkedList<>();
        public NestedIterator(List<NestedInteger> nestedList) {
            for (NestedInteger curr : nestedList) dfs(curr);
        }
        private void dfs(NestedInteger curr) {
            if (curr.isInteger()) deque.offerLast(curr.getInteger());
            else
                for (NestedInteger next : curr.getList()) dfs(next);
        }

        @Override
        public Integer next() {
            if (hasNext()) return deque.pollFirst();
            else return null;
        }

        @Override
        public boolean hasNext() {
            return !deque.isEmpty();
        }
    }


    // [E] 766      Toeplitz Matrix
    public boolean isToeplitzMatrix(int[][] matrix) {
        if (matrix.length == 1 || matrix[0].length == 1) return true;
        int M = matrix.length, N = matrix[0].length;
        for (int col = 0; col < N - 1; col++) {
            int c = col + 1, r = 1;
            while (r < M && c < N) {
//                System.out.println("r:" + r + " c:" + c + "  " + matrix[r][c]);
                if (matrix[r][c] != matrix[r - 1][c - 1]) return false;
                r++;
                c++;
            }
        }

        for (int row = 1; row < M - 1; row++) {
            int c = 1, r = row + 1;
            while (r < M && c < N) {
                if (matrix[r][c] != matrix[r - 1][c - 1]) return false;
                r++;
                c++;
            }
        }
        return true;
    }


    // [M] 370      Range Addition
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int stIdx = update[0], edIdx = update[1], val = update[2];
            res[stIdx] += val;
            if (edIdx < length - 1) res[edIdx + 1] -= val;
        }

        for (int i = 1; i < length; i++) res[i] += res[i - 1];
        return res;
    }


    // [M] 547      Number of Provinces
    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        Set<Integer>[] graph = new Set[N];
        for (int i = 0; i < graph.length; i++) graph[i] = new HashSet<>();

        for (int r = 0; r < N; r++) {
            for (int c = r + 1; c < N; c++) {
                if (isConnected[r][c] == 1) {
                    graph[r].add(c);
                    graph[c].add(r);
                }
            }
        }

        boolean[] visited = new boolean[N];
        int connectedComponent = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                dfs547(i, graph, visited);
                connectedComponent++;
            }
        }
        return connectedComponent;
    }
    private void dfs547(int idx, Set<Integer>[] graph, boolean[] visited) {
        visited[idx] = true;
        for (int next : graph[idx]) {
            if (!visited[next]) dfs547(next, graph, visited);
        }
    }


    // [M] 737      Sentence Similarity II
    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        /**
         * this solution uses union find (based on hash map) to connect similar words and find if two words are connected
         */
        if (sentence1.length != sentence2.length) return false;
        Map<String, String> UF = new HashMap<>();
        for (List<String> pair : similarPairs)                  // Union process
            union(UF, pair.get(0), pair.get(1));

        for (int i = 0; i < sentence1.length; i++) {            // Find process
            if (sentence1[i].equals(sentence2[i])) continue;
            String anceA = find(UF, sentence1[i]), anceB = find(UF, sentence2[i]);
            if (anceA == null || anceB == null || !anceA.equals(anceB)) return false;
        }

        return true;
    }
    private String find(Map<String, String> UF, String key) {   // find, helper function to find the ancestor of a key
        if (!UF.containsKey(key)) return null;
        String currKey = key;
        while (!UF.get(currKey).equals(currKey)) {
            currKey = UF.get(currKey);
        }
        return currKey;
    }
    private void union(Map<String, String> UF, String a, String b) {        // union, connect two words
        boolean containA = UF.containsKey(a), containB = UF.containsKey(b); // whether the UF contain them
        if (!containA && !containB) {       // neither of them exist
            UF.put(a, a);
            UF.put(b, a);
        }
        else if (!containB) {               // has A, but not B
            String ancestorA = find(UF, a);
            UF.put(b, ancestorA);
        }
        else if (!containA){                // only B exists
            String ancestorB = find(UF, b);
            UF.put(a, ancestorB);
        }
        else {                              // both keys exist
            String ancestorA = find(UF, a);
            String ancestorB = find(UF, b);
            UF.put(ancestorB, ancestorA);
        }
    }


    // [E] 734      Sentence Similarity
    public boolean areSentencesSimilar(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if (sentence1.length != sentence2.length) return false;
        Map<String, String> UF = new HashMap<>();
        for (List<String> pair : similarPairs)
             union734(UF, pair.get(0), pair.get(1));

        for (int i = 0; i < sentence1.length; i++) {
            if (sentence1[i].equals(sentence2[i])) continue;
            String anceA = find734(UF, sentence1[i]), anceB = find734(UF, sentence2[i]);
            if (anceA == null || anceB == null || !anceA.equals(anceB)) return false;
        }

        return true;
    }
    private String find734(Map<String, String> UF, String key) {
        if (!UF.containsKey(key)) return null;
        String currKey = key;
        while (!UF.get(currKey).equals(currKey)) {
            currKey = UF.get(currKey);
        }
        return currKey;
    }
    private void union734(Map<String, String> UF, String a, String b) {
        boolean hasA = UF.containsKey(a), hasB = UF.containsKey(b);
        if (!hasA && !hasB) {
            UF.put(a, a);
            UF.put(b, a);
        }
        else if (!hasB) {
            String ancesA = find734(UF, a);
            UF.put(b, ancesA);
        }
        else if (!hasA) {
            String anceB = find734(UF, b);
            UF.put(a, anceB);
        }
        else {
            String anceA = find734(UF, a);
            String anceB = find734(UF, b);
            UF.put(anceB, anceA);
        }
    }


    // [M] 1739     Shortest Path to Get Food
    public int getFood(char[][] grid) {
        /**
         * Breadth First Search!!
         */
        int M = grid.length, N = grid[0].length;
        int[] stIdx = {-1, -1};
        for (int r = 0; r < M; r++) {           // find the source
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == '*') {
                    stIdx = new int[]{r, c};
                    break;
                }
            }
            if (stIdx[0] != -1) break;
        }

        int distance = 0;
        boolean[][] visited = new boolean[M][N];
        visited[stIdx[0]][stIdx[1]] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(stIdx);
//        System.out.println(stIdx[0] + " " + stIdx[1]);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
//            System.out.println("new level");
            for (int i = 0; i < levelSize; i++) {
                int[] curr = queue.poll();
                int currR = curr[0], currC = curr[1];
//                System.out.println(currR + " " + currC);

                if (grid[currR][currC] == '#') return distance;


                if (currR > 0 && !visited[currR - 1][currC] && grid[currR - 1][currC] != 'X') {
                    if (grid[currR - 1][currC] == '#') return distance + 1;
                    visited[currR - 1][currC] = true;
                    queue.offer(new int[]{currR - 1, currC});
                }
                if (currR < M - 1 && !visited[currR + 1][currC] && grid[currR + 1][currC] != 'X') {
                    if (grid[currR + 1][currC] == '#') return distance + 1;
                    visited[currR + 1][currC] = true;
                    queue.offer(new int[]{currR + 1, currC});
                }
                if (currC > 0 && !visited[currR][currC - 1] && grid[currR][currC - 1] != 'X') {
                    if (grid[currR][currC - 1] == '#') return distance + 1;
                    visited[currR][currC - 1] = true;
                    queue.offer(new int[]{currR, currC - 1});
                }
                if (currC < N - 1 && !visited[currR][currC + 1] && grid[currR][currC + 1] != 'X') {
                    if (grid[currR][currC + 1] == '#') return distance + 1;
                    visited[currR][currC + 1] = true;
                    queue.offer(new int[]{currR, currC + 1});
                }
            }
            distance++;
        }
        return -1;
    }


    // [M] 541      01 Matrix
    public int[][] updateMatrix(int[][] mat) {
        int M = mat.length, N = mat[0].length;
        boolean[][] visited = new boolean[M][N];
        Queue<int[]> queue = new LinkedList<>();
        int dist = 0;
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (mat[r][c] == 0) {
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int[] curr = queue.poll();
                int currR = curr[0], currC = curr[1];
                mat[currR][currC] = dist;

                if (currR > 0 && !visited[currR - 1][currC] && mat[currR - 1][currC] > 0) {
                    visited[currR - 1][currC] = true;
                    queue.offer(new int[]{currR - 1, currC});
                }
                if (currR < M - 1 && !visited[currR + 1][currC] && mat[currR + 1][currC] > 0) {
                    visited[currR + 1][currC] = true;
                    queue.offer(new int[]{currR + 1, currC});
                }
                if (currC > 0 && !visited[currR][currC - 1] && mat[currR][currC - 1] > 0) {
                    visited[currR][currC - 1] = true;
                    queue.offer(new int[]{currR, currC - 1});
                }
                if (currC < N - 1 && !visited[currR][currC + 1] && mat[currR][currC + 1] > 0) {
                    visited[currR][currC + 1] = true;
                    queue.offer(new int[]{currR, currC + 1});
                }
            }
            dist++;
        }
        return mat;
    }


    public static void main(String[] args) {
        Leetcode1013 lc = new Leetcode1013();
        int[][] a = {{11,74,0,93},{40,11,74,7}};
        System.out.println(lc.isToeplitzMatrix(a));
    }
}
