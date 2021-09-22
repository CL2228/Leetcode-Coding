import java.util.*;

public class Leetcode0922 {

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




    public static void main(String[] args) {
        Leetcode0922 lc = new Leetcode0922();
        int[] a = {1,3,7,5,10,3};
        List<int[]>[] indices = (List<int[]>[]) new List[26];

//        System.out.println(lc.findWords(null, null));
    }
}
