import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Leetcode1002 {

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

    // [H] 52       N-Queens II
    int count52 = 0;
    public int totalNQueens(int n) {
        boolean[][] placed = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            backtracking52(placed, 0, i);
        }
        return count52;
    }
    private void backtracking52(boolean[][] placed, int row, int col) {
        if (!workable(placed, row, col)) return;
        placed[row][col] = true;

        if (row == placed.length - 1) {
            count52++;
        }
        else {
            for (int c = 0; c < placed[0].length; c++) {
                backtracking52(placed, row + 1, c);
            }
        }

        placed[row][col] = false;
    }
    private boolean workable(boolean[][] placed, int row, int col) {
        for (int r = 0; r < row; r++) if (placed[r][col]) return false;

        int r = row - 1, c = col - 1;
        while (r >=0 && c >= 0) {
            if (placed[r--][c--]) return false;
        }
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < placed[0].length) {
            if (placed[r--][c++]) return false;
        }
        return true;
    }


    // [H] 214      ShortestPalindrome
    public String shortestPalindrome(String s) {
        // this approach uses the KMP algorithm
        // First it computes the Deterministic Finite State Automation
        // use the DFA to run the original string, from tail to head
        // the final state is the length of the palindrome, ending at the head of the string
        // the length needed to add is s.length() - state
        // add (s.length() - state) characters from the tail

        if (s.length() == 0) return s;
        int[][] dfa = getDFA(s);
        int state = 0;
        for (int j = s.length() - 1; j >= 0; j--) {
            state = dfa[s.charAt(j) - 'a'][state];
        }
        int needAdded = s.length() - state;
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i > s.length() - 1 - needAdded; i--) sb.append(s.charAt(i));
        sb.append(s);
        return sb.toString();
    }
    private int[][] getDFA(String s) {
        int N = s.length();
        int[][] dfa = new int[26][N];
        dfa[s.charAt(0) - 'a'][0] = 1;
        int X = 0;
        for (int c = 1; c < N; c++) {
            for (int r = 0; r < dfa.length; r++)
                dfa[r][c] = dfa[r][X];
            dfa[s.charAt(c) - 'a'][c] = c + 1;
            X = dfa[s.charAt(c) - 'a'][X];
        }
        return dfa;
    }


    // [E] 953      Verifying an Alien Dictionary
    public boolean isAlienSorted(String[] words, String order) {
        int[] priority = new int[26];
        for (int i = 0; i < 26; i++) {
            priority[order.charAt(i) - 'a'] = i;
        }
        Comparator<String> myCmp = new myComparator(priority);
        for (int i = 1; i < words.length; i++) {
            if (myCmp.compare(words[i], words[i - 1]) < 0) return false;
        }
        return true;
    }
    class myComparator implements Comparator<String> {
        private int[] order;
        public myComparator(int[] order) {
            this.order = order;
        }
        public int compare(String a, String b) {
            int N = Math.min(a.length(), b.length());
            int idx = 0;
            for (idx = 0; idx < N; idx++) {
                if (order[a.charAt(idx) - 'a'] < order[b.charAt(idx) - 'a']) return -1;
                else if (order[a.charAt(idx) - 'a'] > order[b.charAt(idx) - 'a']) return 1;
            }
            return Integer.compare(a.length(), b.length());
        }
    }


    // [M] 528      Random Pick with Weight
    static class Solution {
        private int[] data;
        private int maxNum;

        public Solution(int[] w) {
            this.data = new int[w.length];
            data[0] = w[0];
            for (int i = 1; i < w.length; i++) data[i] = data[i - 1] + w[i];
            maxNum = data[data.length - 1];

//            for (int i : data) System.out.print(i + " ");
//            System.out.println();
        }

        public int pickIndex() {
            Random rand = new Random();
            int value = rand.nextInt(maxNum) + 1;

            int lo = 0, hi = data.length - 1, mid = (lo + hi) / 2;
//            System.out.println(lo + " " + hi + " " + mid + " " + value);
            while (lo <= hi) {
                if (data[mid] == value) return mid;
                else if (data[mid] < value) {
                    if (data[mid + 1] >= value) return mid + 1;
                    else lo = mid + 1;
                }
                else if (data[mid] >value) {
                    if (mid == 0) return mid;
                    else if (data[mid - 1] < value) return mid;
                    else hi = hi - 1;
                }
                mid = (lo + hi) / 2;
            }
            return mid;
        }
    }







    public static void main(String[] args) {
        Leetcode1002 lc = new Leetcode1002();
        Solution a = new Solution(new int[]{3,14,1,7});
        System.out.println(a.pickIndex());
    }
}
