import java.util.*;

public class Leetcode0919 {


    // [E] 9        Palindrome Number
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        Deque<Integer> deque = new LinkedList<>();

        while (x > 0) {
            deque.offerLast(x % 10);
            x /= 10;
        }
        while (deque.size() > 1) {
            if (deque.peekFirst() != deque.peekLast())  return false;
            deque.pollFirst();
            deque.pollLast();
        }
        return true;
    }


    // [E] 1323     Maximum 69 Number
    public int maximum69Number (int num) {
        char[] data = String.valueOf(num).toCharArray();

        int sixIdx = 0;
        for (sixIdx = 0; sixIdx < data.length; sixIdx++) {
            if (data[sixIdx] == '6') break;
        }
        if (sixIdx == data.length) return num;
        data[sixIdx] = '9';
        return Integer.parseInt(String.valueOf(data));
    }


    // [M] 983      Minimum Cost For Tickets
    public int mincostTickets(int[] days, int[] costs) {
        int N = days[days.length - 1];
        int[] dp = new int[N + 1];
        boolean[] shouldGo = new boolean[N + 1];
        for (int day : days) shouldGo[day] = true;
        for (int j = 1; j < N + 1; j++) {
            if (shouldGo[j]) {
                dp[j] = costs[0] + dp[j - 1];
                int leftIdx = Math.max(0, j - 7);
                int newCost = dp[leftIdx] + costs[1];
                if (newCost < dp[j]) dp[j] = newCost;
                leftIdx = Math.max(0, j - 30);
                newCost = dp[leftIdx] + costs[2];
                if (newCost < dp[j])  dp[j] = newCost;
            }
            else dp[j] = dp[j - 1];
        }
        return dp[N];
    }


    // [E] 1128     NUmber of Equivalent Domino Pairs
    public int numEquivDominoPairs(int[][] dominoes) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] domi : dominoes) {
            if (domi[0] > domi[1]) {
                int swap = domi[0];
                domi[0] = domi[1];
                domi[1] = swap;
            }

            int key = domi[0] * 10 + domi[1];
            if (map.containsKey(key)) map.replace(key, map.get(key) + 1);
            else map.put(key, 1);
        }

        for (int key : map.keySet()) {
            int val = map.get(key);
            if (val > 1) count += val * (val - 1) / 2;
        }


        return count;
    }


    // [M] 139      Word Break
    public boolean wordBreak1(String s, List<String> wordDict) {
        int N = s.length();
        int[] dp = new int[N + 1];
        dp[0] = 1;

        for (int j = 1; j < N + 1; j++) {
            for (String word : wordDict) {
                if (j >= word.length()) {
                    if (s.substring(j - word.length() ,j).equals(word)) {
                        if (dp[j - word.length()] == 1) {
                            dp[j] = 1;
                            break;
                        }
                    }
                }
            }
        }
        return dp[N] == 1;
    }
    public boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        return backtracking(s, 0, set);
    }
    private boolean backtracking(String s, int stIdx, Set<String> set) {
        int edIdx = stIdx + 1;
        while (edIdx <= s.length()) {
            while (edIdx <= s.length() && !set.contains(s.substring(stIdx, edIdx))) edIdx++;
            if (edIdx > s.length()) break;
//            System.out.println(s.substring(stIdx, edIdx));
            if (edIdx == s.length()) return true;
            if (backtracking(s, edIdx, set)) return true;
            edIdx++;
        }
        return false;
    }


    // [H] 140      Word Break II
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        int N = s.length();
        int[] dp = new int[N + 1];
        List<Node>[] data =  new List[N + 1];
        dp[0] = 1;
        for (int i = 0; i < data.length; i++) data[i] = new LinkedList<>();
        for (int j = 1; j < N + 1; j++) {
            for (String wrd : wordDict) {
                if (j >= wrd.length() && s.substring(j - wrd.length(), j).equals(wrd)) {
                    if (dp[j - wrd.length()] == 1) {
                        dp[j] = 1;
                        data[j].add(new Node(j - wrd.length(), wrd));
                    }
                }
            }
        }
        if (dp[N] == 0) return res;
        Deque<String> deque = new LinkedList<>();
        for (Node curr : data[N]) backtrackDFS(data, curr, deque, res);
        return res;
    }
    private void backtrackDFS(List<Node>[] data, Node curr, Deque<String> deque, List<String> res) {
        deque.offerFirst(curr.word);
        if (curr.preIdx == 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : deque) sb.append(str).append(" ");
            res.add(sb.toString().trim());
        }
        else for (Node next : data[curr.preIdx]) backtrackDFS(data, next, deque, res);
        deque.pollFirst();
    }
    static class Node {
        private int preIdx;
        private String word;
        public Node(int preIdx, String word) {
            this.preIdx = preIdx;
            this.word = word;
        }
    }


    // [E] 883      Projection Area of 3D Shapes
    public int projectionArea(int[][] grid) {
        int count = 0;
        int xy = 0;
        int yz = 0;
        for (int r = 0; r < grid.length; r++) {
            int max = 0;
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 0) xy++;
                if (grid[r][c] > max) max = grid[r][c];
            }
            yz += max;
        }
        count += xy;
        count += yz;
        int xz = 0;
        for (int c = 0; c < grid[0].length; c++) {
            int max = 0;
            for (int r = 0; r < grid.length; r++) if (grid[r][c] > max) max = grid[r][c];
            xz += max;

        }
        count += xz;
        return count;
    }


    // [E] 14       Longest Common Prefix
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) return strs[0];
        StringBuilder sb = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++) {
            if (sb.length() > strs[i].length()) sb.delete(strs[i].length(), sb.length());
            for (int j = 0; j < sb.length(); j++) {
                if (sb.charAt(j) != strs[i].charAt(j)) {
                    sb.delete(j, sb.length());
                    break;
                }
            }
        }

        return sb.toString();
    }






    public static void main(String[] args) {
        Leetcode0919 lc = new Leetcode0919();
        String s = "catsanddog";
        String[] word = {"cat", "cats", "and", "sand", "dog"};
        System.out.println(lc.wordBreak(s, new ArrayList<>(Arrays.asList("cat", "cats", "and", "sand", "dog"))));
//        String a = " er 34 rt r  ";
        StringBuilder sb = new StringBuilder();
    }

}
